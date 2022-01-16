package com.imooc.service.impl;

import com.imooc.common.JHConst;
import com.imooc.mapper.JHAddressBookCategoryItemRelationMapper;
import com.imooc.mapper.JHAddressBookCategoryMapper;
import com.imooc.mapper.JHAddressBookItemMapper;
import com.imooc.pojo.JHAddressBookCategory;
import com.imooc.pojo.JHAddressBookCategoryItemRelation;
import com.imooc.pojo.JHAddressBookItem;
import com.imooc.response.JHResponse;
import com.imooc.response.JHResponseCode;
import com.imooc.service.IAddressBookService;
import com.imooc.vo.JHAddressBookCategoryVO;
import com.imooc.vo.JHAddressBookItemVO;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Array;
import java.util.*;

@Log
@Service("iAddressBookService")
@CacheConfig(cacheNames = {"AddressBookListCache"})
public class IAddressBookServiceImpl implements IAddressBookService {

	@Autowired
	JHAddressBookCategoryMapper addressBookCategoryMapper;

	@Autowired
	JHAddressBookItemMapper addressBookItemMapper;

	@Autowired
	JHAddressBookCategoryItemRelationMapper addressBookCategoryItemRelationMapper;

	@Override
	public JHResponse fetchClosedCategorys() {
		return this.fetchCategorys(Arrays.asList(JHConst.Status.Closed));
	}

	@Override
	public JHResponse fetchShownCategorys() {
		return this.fetchCategorys(Arrays.asList(JHConst.Status.OnSale));
	}

	@Override
	public JHResponse fetchHiddenCategorys() {
		return this.fetchCategorys(Arrays.asList(JHConst.Status.OffSale));
	}

	@Override
	public JHResponse fetchCategorys() {
		return this.fetchCategorys(null);
	}

	private JHResponse fetchCategorys(List<Byte> statusList) {
		Example example = new Example(JHAddressBookCategory.class);
		example.orderBy("orderNum").asc();
		Example.Criteria criteria = example.createCriteria();
		if (statusList != null) {
			for (Byte status : statusList) {
				criteria.orEqualTo("status", status);
			}
		}
		List<JHAddressBookCategory> categoryList =
				addressBookCategoryMapper.selectByExample(example);
		return JHResponse.createBySuccess(JHResponseCode.Success, categoryList);
	}

	@Override
	public List fetchShownItems(Short catId) {
		List<Integer> idList = new ArrayList();
		List<JHAddressBookCategoryItemRelation> relations = this.fetchCategoryItemRelations(catId);
		for (JHAddressBookCategoryItemRelation item : relations) {
			idList.add(item.getItemId());
		}
		if (CollectionUtils.isEmpty(idList)) {
			return new ArrayList();
		}
		Example example = new Example(JHAddressBookItem.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andIn("id", idList);
		criteria.andEqualTo("status", JHConst.Status.OnSale);
		List<JHAddressBookItem> itemList =
				addressBookItemMapper.selectByExample(example);
		//生成临时map根据orderNum做重排
		Map<Integer, JHAddressBookItem> itemMap = new HashMap<>();
		for (JHAddressBookItem item : itemList) {
			itemMap.put(item.getId(), item);
		}

		List orderedList = new ArrayList();
		for (Integer id : idList) {
			JHAddressBookItem item = itemMap.get(id);
			if (item != null) {
				orderedList.add(item);
			}
		}
		return orderedList;
	}

	@Override
	public List fetchCategoryItemRelations(Short catId) {
		JHAddressBookCategoryItemRelation item = new JHAddressBookCategoryItemRelation();
		item.setCategoryId(catId);
		Example example = new Example(JHAddressBookCategoryItemRelation.class);
		example.orderBy("orderNum").asc();
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("categoryId", catId);
		return addressBookCategoryItemRelationMapper.selectByExample(example);
	}

	@Override
	@CacheEvict(value="userCache", allEntries=true) //方法调用后清空所有缓存
	public List addItem() {
		return null;
	}

	@Override
	@Cacheable("AddressBookList")
	public JHResponse fetchCategorysAndItems() {
		JHResponse<List> categoryResponse = fetchShownCategorys();
		if (!categoryResponse.isSuccess()) {
			return categoryResponse;
		}
		List categoryAndItemsList = new ArrayList();
		List<JHAddressBookCategory> categoryList = categoryResponse.getData();
		for (JHAddressBookCategory category : categoryList) {
			// 得到categoryVO
			JHAddressBookCategoryVO categoryVO = new JHAddressBookCategoryVO();
			BeanUtils.copyProperties(category, categoryVO);

			// 得到categoryVO中subItems
			List itemVOList = new ArrayList();
			List<JHAddressBookItem> itemList = this.fetchShownItems(category.getId());
			for (JHAddressBookItem item : itemList) {
				JHAddressBookItemVO itemVO = new JHAddressBookItemVO();
				BeanUtils.copyProperties(item, itemVO);
				itemVO.setCategoryId(category.getId());
				itemVOList.add(itemVO);
			}
			if (!CollectionUtils.isEmpty(itemVOList)) {
				categoryVO.setSubItems(itemVOList);
				categoryAndItemsList.add(categoryVO);
			}
		}
		return JHResponse.createBySuccess(JHResponseCode.Success,
				categoryAndItemsList);
	}
}

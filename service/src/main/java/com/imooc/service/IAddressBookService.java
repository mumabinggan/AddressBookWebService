package com.imooc.service;

import com.imooc.pojo.JHAddressBookCategory;
import com.imooc.response.JHResponse;

import java.util.List;

public interface IAddressBookService {

	/**
	 * 已经关闭的分类
	 * @return
	 */
	JHResponse fetchClosedCategorys();

	/**
	 * 获取展示的分类
	 * @return
	 */
	JHResponse fetchShownCategorys();

	/**
	 * 获取隐藏的分类
	 * @return
	 */
	JHResponse fetchHiddenCategorys();

	/**
	 * 获取展示的分类
	 * @return
	 */
	JHResponse fetchCategorys();

	/**
	 * 根据分类得到显示的电话
	 * @param catId
	 * @return
	 */
	List fetchShownItems(Short catId);

	/**
	 * 根据分类id得到分类和item关系表
	 * @param catId
	 * @return
	 */
	List fetchCategoryItemRelations(Short catId);

	/**
	 * 增加item
	 * @return
	 */
	List addItem();

	/**
	 * 获取分类和对应项所有数据
	 * @return
	 */
	JHResponse fetchCategorysAndItems();
}

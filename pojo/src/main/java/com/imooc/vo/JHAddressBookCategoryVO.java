package com.imooc.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JHAddressBookCategoryVO {

	Short id;

	String name;

	List<JHAddressBookItemVO> subItems;
}

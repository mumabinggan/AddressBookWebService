package com.imooc.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class JHAddressBookItemVO {

	private Short id;

	private Short categoryId;

	private String name;

	private String phone;
}

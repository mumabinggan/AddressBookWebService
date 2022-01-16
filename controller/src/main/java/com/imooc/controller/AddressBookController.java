package com.imooc.controller;

import com.imooc.mapper.StuMapper;
import com.imooc.response.JHResponse;
import com.imooc.service.IAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("addressbook")
public class AddressBookController {

	@Autowired
	IAddressBookService iAddressBookService;

	@GetMapping("/hello")
	public Object hello() {
		return "Hello!";
	}

	@GetMapping("/fetch")
	public Object helloWorld(int id) {
		return null;
//		return stuMapper.selectByPrimaryKey(id);
	}

	@GetMapping("/fetchAddressBookList.do")
	public JHResponse fetchAddressBookList(@RequestParam String communityId) {
		return iAddressBookService.fetchCategorysAndItems();
	}
}

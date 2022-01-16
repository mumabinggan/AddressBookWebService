package com.imooc.response;

import lombok.Getter;
import lombok.Setter;

public enum JHResponseCode {

	Success(0, "返回成功"),
	ReLogin(1000, "重新登录"),
	InvalidArgument(1001, "参数错误"),
	EmptyArgument(1002, "参数为空"),
	Error(1, "返回失败"),

	CheckUsernameErrorByEmpty(10000000, "用户名为空"),
	CheckUsernameErrorByIsExist(10000001, "用户名已存在"),

	RegisterUserErrorByUsernameEmpty(10000100, "用户名为空"),
	RegisterUserErrorByPasswordEmpty(10000101, "密码为空"),
	RegisterUserErrorByPasswordDiff(10000102, "密码和确认密码不一致"),
	RegisterUserErrorByInsertDBFail(10000103, "写入数据库失败"),

	LoginUserErrorByUsernameEmpty(10000200, "用户名为空"),
	LoginUserErrorByPasswordEmpty(10000201, "密码为空"),
	LoginUserErrorBySelectFromDBFail(10000202, "登录失败"),

	OrderPayErrorByPayMethodFail(10000300, "支付方式不正确"),

	Error_XXX(2000001, "用户名不存在");

	private final int code;
	private final String msg;

	public static int successMaxCode = 999;

	JHResponseCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	JHResponseCode(JHResponse response) {
		this.code = response.getCode();
		this.msg = response.getMsg();
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}

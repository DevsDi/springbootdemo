package com.dev.exception;

public class MyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int code;
	private String msg;

	public MyException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

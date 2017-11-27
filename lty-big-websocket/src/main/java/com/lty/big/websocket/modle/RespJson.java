package com.lty.big.websocket.modle;

/**
 * 返回前端的json对象
 * 
 * @描述:
 * @作者: hgy
 * @创建时间: 2017年8月18日
 * @版本: 1.0
 */
public class RespJson extends BasicModle {

	private static final long serialVersionUID = 1L;

	private int type;

	private Object data;

	public int getType() {
		return type;
	}

	public RespJson setType(int type) {
		this.type = type;
		return this;
	}

	public Object getData() {
		return data;
	}

	public RespJson setData(Object data) {
		this.data = data;
		return this;
	}

}

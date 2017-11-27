package com.lty.big.websocket.modle;

/**
 * 返回给前端的业务类型枚举
 * 
 * @描述:
 * @作者: hgy
 * @创建时间: 2017年8月22日
 * @版本: 1.0
 */
public enum ResType {
	PUSH_LINE_PATH("推送线路轨迹信息", 101), //
	PUSH_LINE_STATISTICS("推送线路统计信息", 102), //
	PUSH_STATION_STATISTICS("推送站点统计信息", 203), //
	PUSH_BUS_STATISTICS("推送车辆统计信息", 301);//

	private String name;
	private int code;

	private ResType(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}

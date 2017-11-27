package com.lty.big.websocket.modle;

/**
 * 站点客流量
 * 
 * @描述:
 * @作者: hgy
 * @创建时间: 2017年8月22日
 * @版本: 1.0
 */
public class Station extends BasicModle {
	private static final long serialVersionUID = 1L;
	private String stationName;
	private double latitude;
	private double longitude;
	private int psg;

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getPsg() {
		return psg;
	}

	public void setPsg(int psg) {
		this.psg = psg;
	}

}

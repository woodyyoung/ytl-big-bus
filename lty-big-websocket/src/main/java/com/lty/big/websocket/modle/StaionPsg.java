package com.lty.big.websocket.modle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.lty.big.websocket.util.FileHelper;

public class StaionPsg extends BasicModle {
	private static final long serialVersionUID = 1L;
	private int type;

	private List<Spsg> data;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Spsg> getData() {
		return data;
	}

	public void setData(List<Spsg> data) {
		this.data = data;
	}

	public static class Spsg extends BasicModle {

		private static final long serialVersionUID = 1L;
		private String stationName;
		private long psg;
		private BigDecimal latitude;
		private BigDecimal longitude;

		public String getStationName() {
			return stationName;
		}

		public void setStationName(String stationName) {
			this.stationName = stationName;
		}

		public long getPsg() {
			return psg;
		}

		public void setPsg(long psg) {
			this.psg = psg;
		}

		public BigDecimal getLatitude() {
			return latitude;
		}

		public void setLatitude(BigDecimal latitude) {
			this.latitude = latitude;
		}

		public BigDecimal getLongitude() {
			return longitude;
		}

		public void setLongitude(BigDecimal longitude) {
			this.longitude = longitude;
		}

	}

	public void ranPsg() {
		Random r = new Random();
		for (Spsg psg : this.getData()) {
			psg.setPsg(r.nextInt(30));
		}
	}

	public void ranTop10Psg() {
		Random r = new Random();
		for (Spsg psg : this.getData()) {
			long a = psg.getPsg() + r.nextInt(5);
			psg.setPsg(a);
		}
	}

	public static void main(String[] args) {
		String msg = FileHelper.readJson("201.txt");
		StaionPsg linePsg = JSON.parseObject(msg, StaionPsg.class);
		linePsg.ranPsg();
		System.out.println(JSON.toJSONString(linePsg));
	}
}

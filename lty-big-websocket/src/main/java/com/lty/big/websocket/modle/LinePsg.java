package com.lty.big.websocket.modle;

import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.lty.big.websocket.util.FileHelper;

public class LinePsg extends BasicModle {
	private static final long serialVersionUID = 1L;

	private int type;

	private List<Psg> data;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Psg> getData() {
		return data;
	}

	public void setData(List<Psg> data) {
		this.data = data;
	}

	public static class Psg extends BasicModle {
		private static final long serialVersionUID = 1L;
		private String line_id;
		private String line_name;
		private long total_psg;

		public String getLine_id() {
			return line_id;
		}

		public void setLine_id(String line_id) {
			this.line_id = line_id;
		}

		public String getLine_name() {
			return line_name;
		}

		public void setLine_name(String line_name) {
			this.line_name = line_name;
		}

		public long getTotal_psg() {
			return total_psg;
		}

		public void setTotal_psg(long total_psg) {
			this.total_psg = total_psg;
		}

	}

	public void ranPsg() {
		Random r = new Random();
		for (Psg psg : this.getData()) {
			long a = psg.getTotal_psg() + r.nextInt(20);
			psg.setTotal_psg(a);
		}
	}

	public static void main(String[] args) {
		String msg = FileHelper.readJson("103.txt");
		LinePsg linePsg = JSON.parseObject(msg, LinePsg.class);
		for (Psg psg : linePsg.getData()) {
			psg.getTotal_psg();
		}
		System.out.println(linePsg.getType());
	}
}

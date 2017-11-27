package com.lty.big.websocket.modle;

import java.util.Random;

public class LineTotalPsg extends BasicModle {
	private static final long serialVersionUID = 1L;

	private int type;

	private TotalPsg data;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public TotalPsg getData() {
		return data;
	}

	public void setData(TotalPsg data) {
		this.data = data;
	}

	public static class TotalPsg extends BasicModle {
		private static final long serialVersionUID = 1L;
		private long total_psg;

		public long getTotal_psg() {
			return total_psg;
		}

		public void setTotal_psg(long total_psg) {
			this.total_psg = total_psg;
		}

	}

	public void ranPsg() {
		Random r = new Random();
		long a = this.getData().getTotal_psg() + r.nextInt(100);
		this.getData().setTotal_psg(a);
	}

}

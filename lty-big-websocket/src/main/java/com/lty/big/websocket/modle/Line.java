package com.lty.big.websocket.modle;

import com.alibaba.fastjson.JSON;

/**
 * 线路信息
 * 
 * @描述:
 * @作者: hgy
 * @创建时间: 2017年8月11日
 * @版本: 1.0
 */
public class Line extends BasicModle {
	private static final long serialVersionUID = 1600572344816974884L;
	/**
	 * 线路名称
	 */
	private String line_name;
	/**
	 * 线路编号
	 */
	private String line_id;
	/**
	 * 线路轨迹
	 */
	private double[][] line_path;
	/**
	 * 线路样式
	 */
	private LineStyle lineStyle;

	public String getLine_name() {
		return line_name;
	}

	public Line setLine_name(String line_name) {
		this.line_name = line_name;
		return this;
	}

	public String getLine_id() {
		return line_id;
	}

	public Line setLine_id(String line_id) {
		this.line_id = line_id;
		return this;
	}

	public double[][] getLine_path() {
		return line_path;
	}

	public Line setLine_path(double[][] line_path) {
		this.line_path = line_path;
		return this;
	}

	public LineStyle getLineStyle() {
		return lineStyle;
	}

	public Line setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
		return this;
	}

	public static class LineStyle extends BasicModle {
		private static final long serialVersionUID = 1L;
		private Style normal;

		public Style getNormal() {
			return normal;
		}

		public LineStyle setNormal(Style normal) {
			this.normal = normal;
			return this;
		}

	}

	public static class Style extends BasicModle {
		private static final long serialVersionUID = 1L;
		private String color;

		public String getColor() {
			return color;
		}

		public Style setColor(String color) {
			this.color = color;
			return this;
		}

	}

	public Line setMystyle(String color) {
		Style style = new Style().setColor(color);
		LineStyle lineStyle = new LineStyle().setNormal(style);
		setLineStyle(lineStyle);
		return this;
	}

	public static void main(String[] args) {
		double[][] a = { { 23.5, 108.7 }, { 23.6, 108.7 } };

		Line line = new Line().setLine_id("101").setLine_name("11路")
				.setMystyle("rgba(90,221,223,1)").setLine_path(a);

		String s = JSON.toJSONString(line);
		System.out.println(s);

		String bb = "{\"lineStyle\":{\"normal\":{\"color\":\"rgba(90,221,223,1)\"}},\"line_id\":\"101\",\"line_name\":\"11路\"}";
		Line parseObject = JSON.parseObject(bb, Line.class);
		System.out.println(parseObject.getLine_name());
	}

}

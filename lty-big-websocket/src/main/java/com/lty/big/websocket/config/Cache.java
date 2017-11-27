package com.lty.big.websocket.config;

import com.lty.big.websocket.modle.RespJson;

/**
 * 离线数据缓存
 * 
 * @描述:
 * @作者: hgy
 * @创建时间: 2017年8月22日
 * @版本: 1.0
 */
public class Cache {
	// 线路轨迹信息缓存
	public static final RespJson linePathCahe = new RespJson();
	// 线路统计信息缓存
	public static final RespJson line_statistics_cahe = new RespJson();
	// 站点构成信息缓存
	public static final RespJson station_statistics_cahe = new RespJson();
	// 车辆构成信息缓存
	public static final RespJson bus_statistics_cahe = new RespJson();
}

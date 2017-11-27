package com.lty.big.websocket.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StationMapper {
	/*
	 * List<Map<String, Object>> queryStationStatistics(
	 * 
	 * @Param("cityCode") String cityCode);
	 */

	List<Map<String, Object>> queryAllStaion();

	List<Map<String, Object>> queryGpsData();
}

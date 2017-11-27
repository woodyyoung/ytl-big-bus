package com.lty.big.websocket.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LineMapper {

	List<Map<String, Object>> queryLinePath(@Param("cityCode") String cityCode);

	/*
	 * List<Map<String, Object>> queryLineStatisticsInfo(
	 * 
	 * @Param("cityCode") String cityCode);
	 */
}

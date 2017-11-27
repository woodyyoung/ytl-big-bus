package com.lty.big.websocket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lty.big.websocket.mapper.BusMapper;
import com.lty.big.websocket.mapper.LineMapper;
import com.lty.big.websocket.mapper.StationMapper;
import com.lty.big.websocket.modle.Line;
import com.lty.big.websocket.util.CoordinateConversion;
import com.lty.big.websocket.util.Point;

@Service
public class OfflineService {
	private static final String CITY_CODE = "001";
	@Autowired
	private LineMapper lineMapper;
	@Autowired
	private BusMapper busMapper;
	@Autowired
	private StationMapper stationMapper;

	public List<Line> getLinePath() {
		List<Map<String, Object>> linePaths = lineMapper
				.queryLinePath(CITY_CODE);
		List<Line> lineList = new ArrayList<Line>();
		for (Map<String, Object> linePath : linePaths) {
			Line line = new Line().setLine_id((String) linePath.get("line_id"))
					.setLine_name((String) linePath.get("line_name"))
					.setMystyle("rgba(90,221,223,1)");
			String[] pathArray = ((String) linePath.get("line_path"))
					.split("\\|");

			double[][] line_path = new double[pathArray.length][2];
			for (int i = 0; i < pathArray.length; i++) {
				String path = pathArray[i];
				if (StringUtils.isBlank(path)) {
					continue;
				}
				String[] lalng = path.split(",");

				// google经纬度转换成百度经纬度
				Point point = CoordinateConversion.google_bd_encrypt(
						Double.parseDouble(lalng[1]),
						Double.parseDouble(lalng[0]));

				line_path[i][0] = point.getLng();
				line_path[i][1] = point.getLat();
			}

			line.setLine_path(line_path);
			lineList.add(line);
		}
		return lineList;
	}

	public List<Map<String, Object>> queryBusStatistics() {
		/*
		 * List<Map<String, Object>> busStatistics = busMapper
		 * .queryBusStatistics(CITY_CODE); return busStatistics;
		 */
		return null;
	}

	public List<Map<String, Object>> queryLineStatisticsInfo() {
		/*
		 * List<Map<String, Object>> lineStatistics = lineMapper
		 * .queryLineStatisticsInfo(CITY_CODE); return lineStatistics;
		 */
		return null;
	}

	public List<Map<String, Object>> queryStationStatistics() {
		/*
		 * List<Map<String, Object>> staionStatistics = stationMapper
		 * .queryStationStatistics(CITY_CODE); return staionStatistics;
		 */
		return null;
	}

}

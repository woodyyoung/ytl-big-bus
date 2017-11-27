package com.lty.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.lty.big.websocket.WebsocketApplication;
import com.lty.big.websocket.mapper.LineMapper;
import com.lty.big.websocket.mapper.StationMapper;
import com.lty.big.websocket.modle.Line;
import com.lty.big.websocket.modle.RespJson;
import com.lty.big.websocket.util.Point;
import com.lty.big.websocket.util.Transform;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebsocketApplication.class)
public class MapperTest {
	@Autowired
	private LineMapper linemapper;
	@Autowired
	private StationMapper stationMapper;

	@Test
	public void queryLinePath() {
		List<Map<String, Object>> queryLinePath = linemapper
				.queryLinePath("001");
		
		String[] clorarr ={
		"#FF0000",
		"#FF0000",
		"#FF00FF",
		"#FF00FF",
		"#FF00FF",
		"#99FF33",
		"#66FFFF",
		"#FFCCFF",
		"#FFFF99",
		"#33FFCC",
		"#FFFF00"
		};

		Random r =new Random();
		for (Map<String, Object> line : queryLinePath) {
			String linePath = (String) line.get("line_path");
			List<double[]> path = new ArrayList<double[]>();
			String[] pathArray = linePath.split("\\|");
			for (String l : pathArray) {
				String[] latlong = l.split(",");

				Point p = Transform.wgs84togcj02(
						Double.parseDouble(latlong[1]),
						Double.parseDouble(latlong[0]));
				Point p2 = Transform.gcj02tobd09(p.getLng(), p.getLat());

				/*
				 * Point point = CoordinateConversion.wgs_gcj_encrypts(
				 * Double.parseDouble(latlong[1]),
				 * Double.parseDouble(latlong[0]));
				 */

				double[] s = { p2.getLng(), p2.getLat() };
				path.add(s);
			}
			line.remove("line_path");
			line.put("line_path", path);

			
			Line l = new Line().setMystyle(clorarr[r.nextInt(clorarr.length)]);
			line.put("lineStyle", l.getLineStyle());
		}
		RespJson json = new RespJson();
		json.setType(101);
		json.setData(queryLinePath);
		System.out.println(JSON.toJSONString(json));
	}

	@Test
	public void queryStations() {
		List<Map<String, Object>> stations = stationMapper.queryAllStaion();
		for (Map<String, Object> station : stations) {
			Point p = Transform.wgs84togcj02(
					((BigDecimal) station.get("longitude")).doubleValue(),
					((BigDecimal) station.get("latitude")).doubleValue());
			Point p2 = Transform.gcj02tobd09(p.getLng(), p.getLat());
			/*
			 * Point point = CoordinateConversion.wgs_gcj_encrypts(
			 * ((BigDecimal) station.get("latitude")).doubleValue(),
			 * ((BigDecimal) station.get("longitude")).doubleValue());
			 */

			station.put("latitude", p2.getLat());
			station.put("longitude", p2.getLng());
		}
		RespJson json = new RespJson();
		json.setType(201);
		json.setData(stations);
		System.out.println(JSON.toJSONString(json));
	}

	@Test
	public void queryGPSdata() {
		List<Map<String, Object>> stations = stationMapper.queryGpsData();
		Map<String, Object> resultData = new HashMap<String, Object>();
		resultData.put("type", 302);
		resultData.put("data", stations);
		System.out.println(JSON.toJSONString(resultData));
	}

}

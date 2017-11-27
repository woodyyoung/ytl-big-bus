package com.lty.big.websocket;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.lty.big.websocket.config.Cache;
import com.lty.big.websocket.modle.Line;
import com.lty.big.websocket.modle.ResType;
import com.lty.big.websocket.service.OfflineService;

@Component
public class ApplicationStartup implements
		ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (arg0.getApplicationContext().getParent() == null) {

			System.out.println("容器初始化成功!----");
			System.out.println("缓存加载开始!----");
			// 查询线路统计信息，放入缓存
			OfflineService bean = arg0.getApplicationContext().getBean(
					OfflineService.class);
			List<Line> linePath = bean.getLinePath();
			Cache.linePathCahe.setData(linePath);
			Cache.linePathCahe.setType(ResType.PUSH_LINE_PATH.getCode());

			List<Map<String, Object>> lineStatisticsInfo = bean
					.queryLineStatisticsInfo();
			Cache.line_statistics_cahe.setData(lineStatisticsInfo);
			Cache.line_statistics_cahe.setType(ResType.PUSH_LINE_STATISTICS
					.getCode());

			// 查询站点构成信息，放入缓存
			List<Map<String, Object>> stationStatistics = bean
					.queryStationStatistics();
			Cache.station_statistics_cahe.setData(stationStatistics);
			Cache.station_statistics_cahe
					.setType(ResType.PUSH_STATION_STATISTICS.getCode());

			// 查询车辆信息，放入缓存
			List<Map<String, Object>> busStatistics = bean.queryBusStatistics();
			Cache.bus_statistics_cahe.setData(busStatistics);
			Cache.bus_statistics_cahe.setType(ResType.PUSH_BUS_STATISTICS
					.getCode());

			System.out.println("缓存加载完毕!----");

		}

	}

}

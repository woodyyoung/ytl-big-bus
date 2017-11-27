package com.lty.big.websocket.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSON;
import com.lty.big.websocket.kafak.Sender;
import com.lty.big.websocket.mapper.StationMapper;
import com.lty.big.websocket.modle.LinePsg;
import com.lty.big.websocket.modle.LineTotalPsg;
import com.lty.big.websocket.modle.StaionPsg;
import com.lty.big.websocket.socket.BusWsHandler;
import com.lty.big.websocket.socket.LineWsHandler;
import com.lty.big.websocket.socket.StationWsHandler;
import com.lty.big.websocket.util.FileHelper;
import com.lty.big.websocket.util.Point;
import com.lty.big.websocket.util.Transform;

@EnableScheduling
@Component
public class PushTask {
	protected static final Logger logger = LoggerFactory
			.getLogger(PushTask.class);

	private static LinePsg linepsg = null;
	private static LineTotalPsg lineTotalPsg = null;
	private static StaionPsg staionPsg = null;
	private static StaionPsg staionTop10Psg = null;

	@Autowired
	private Sender sender;
	@Autowired
	private StationMapper stationMapper;

	/**
	 * 
	 * @功能：推送线路客流实时数据
	 * 
	 * @param context
	 *
	 * @返回：void
	 *//*
	@Scheduled(cron = "0/5 * * * * ?")
	public void pushLineMsg() {
		// 间隔2分钟,执行工单上传任务
		Thread current = Thread.currentThread();
		System.out.println("定时任务1:" + current.getId());
		logger.info("PushTask.pushLineMsg 定时任务1:" + current.getId() + ",name:"
				+ current.getName());

		// 线路客流top10
		if (linepsg == null || linepsg.getData().get(0).getTotal_psg() > 20000) {
			String msg = FileHelper.readJson("103.txt");
			linepsg = JSON.parseObject(msg, LinePsg.class);
		}
		linepsg.ranPsg();
		TextMessage txt = new TextMessage(JSON.toJSONString(linepsg));
		LineWsHandler.pushMsgToAllUser(LineWsHandler.users, txt);

		// 线路总客流量
		if (lineTotalPsg == null
				|| lineTotalPsg.getData().getTotal_psg() > 300000) {
			String msg = FileHelper.readJson("104.txt");
			lineTotalPsg = JSON.parseObject(msg, LineTotalPsg.class);
		}
		lineTotalPsg.ranPsg();
		txt = new TextMessage(JSON.toJSONString(lineTotalPsg));
		LineWsHandler.pushMsgToAllUser(LineWsHandler.users, txt);

	}

	*//**
	 * 
	 * @功能：推送站点客流实时数据
	 * 
	 * @param context
	 *
	 * @返回：void
	 *//*
	@Scheduled(cron = "0/5 * * * * ?")
	public void pushStationMsg() {
		// 间隔2分钟,执行工单上传任务
		Thread current = Thread.currentThread();
		System.out.println("定时任务2:" + current.getId());
		logger.info("PushTask.pushStationMsg 定时任务2:" + current.getId()
				+ ",name:" + current.getName());

		if (staionPsg == null) {
			String msg = FileHelper.readJson("201.txt");
			staionPsg = JSON.parseObject(msg, StaionPsg.class);
		}
		staionPsg.ranPsg();

		TextMessage txt = new TextMessage(JSON.toJSONString(staionPsg));
		StationWsHandler.pushMsgToAllUser(StationWsHandler.users, txt);

		// 推送站点客流top10
		if (staionTop10Psg == null
				|| staionTop10Psg.getData().get(0).getPsg() > 3000) {
			String msg = FileHelper.readJson("202.txt");
			staionTop10Psg = JSON.parseObject(msg, StaionPsg.class);
		}
		staionTop10Psg.ranTop10Psg();
		txt = new TextMessage(JSON.toJSONString(staionTop10Psg));
		StationWsHandler.pushMsgToAllUser(StationWsHandler.users, txt);
	}*/

	/**
	 * 
	 * @功能：推送车辆相关实时数据
	 * 
	 * @param context
	 *
	 * @返回：void
	 */
	@Scheduled(cron = "0/30 * * * * ?")
	public void pushBusMsg() {
		// 间隔2分钟,执行工单上传任务
		Thread current = Thread.currentThread();
		System.out.println("定时任务3:" + current.getId());
		logger.info("PushTask.pushBusMsg 定时任务3:" + current.getId() + ",name:"
				+ current.getName());

		// 推送运营时速信息
		String msg = FileHelper.readJson("303.txt");
		TextMessage txt = new TextMessage(msg);
		BusWsHandler.pushMsgToAllUser(BusWsHandler.users, txt);

		// 推送运营速度变化数据
		msg = FileHelper.readJson("304.txt");
		txt = new TextMessage(msg);
		BusWsHandler.pushMsgToAllUser(BusWsHandler.users, txt);
	}

	/**
	 * 
	 * @功能：推送车辆GPS实时数据
	 * 
	 * @param context
	 *
	 * @返回：void
	 */
	@Scheduled(cron = "1/10 * * * * ?")
	public void pushGpsData() {

		List<Map<String, Object>> queryGpsData = stationMapper.queryGpsData();

		for (Map<String, Object> station : queryGpsData) {

			/*
			 * Point point = CoordinateConversion.wgs_gcj_encrypts( (double)
			 * station.get("latitude"), (double) station.get("longitude"));
			 */

			Point p = Transform.wgs84togcj02((double) station.get("longitude"),
					(double) station.get("latitude"));
			Point p2 = Transform.gcj02tobd09(p.getLng(), p.getLat());

			station.put("latitude", p2.getLat());
			station.put("longitude", p2.getLng());
		}

		Map<String, Object> resultData = new HashMap<String, Object>();
		resultData.put("type", 302);
		resultData.put("data", queryGpsData);
		TextMessage txt = new TextMessage(JSON.toJSONString(resultData));
		BusWsHandler.pushMsgToAllUser(BusWsHandler.users, txt);
	}

}

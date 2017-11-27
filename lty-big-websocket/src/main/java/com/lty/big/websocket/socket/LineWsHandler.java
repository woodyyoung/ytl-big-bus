package com.lty.big.websocket.socket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.lty.big.websocket.util.FileHelper;

/**
 * 推送大数据展示线路相关的页面的展示信息
 * 
 * @描述:
 * @作者: hgy
 * @创建时间: 2017年8月10日
 * @版本: 1.0
 */
public class LineWsHandler extends BasicWsHandler {
	protected static final Logger logger = LoggerFactory
			.getLogger(LineWsHandler.class);

	public static final Map<String, WebSocketSession> users;// 最好用Map来存储，key用userid

	static {
		users = new ConcurrentHashMap<String, WebSocketSession>();
	}

	/*
	 * @Value("${catalina.base}") private String env;
	 */

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		TextMessage msg = new TextMessage(message.getPayload());
		logger.info("message received: {}", message.getPayload());
		try {
			session.sendMessage(msg);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		users.put(session.getId(), session);
		logger.info(session.getRemoteAddress() + " come in!---");
		logger.info("LineWsHandler user size:---" + users.size());

		// 推送线路信息给前端
		String msg = FileHelper.readJson("101.txt");
		// String msg = JSON.toJSONString(Cache.linePathCahe);
		TextMessage txt = new TextMessage(msg);
		session.sendMessage(txt);

		// 推送线路统计信息给前端
		msg = FileHelper.readJson("102.txt");
		// msg = JSON.toJSONString(Cache.line_statistics_cahe);
		txt = new TextMessage(msg);
		session.sendMessage(txt);
		
		// 推送线路top10
		msg = FileHelper.readJson("103.txt");
		// String msg = JSON.toJSONString(Cache.linePathCahe);
		txt = new TextMessage(msg);
		session.sendMessage(txt);
		
		// 推送线路top10
		msg = FileHelper.readJson("104.txt");
		// String msg = JSON.toJSONString(Cache.linePathCahe);
		txt = new TextMessage(msg);
		session.sendMessage(txt);

		// 推送线路统计信息给前端
		msg = FileHelper.readJson("105.txt");
		txt = new TextMessage(msg);
		session.sendMessage(txt);
	}

	@Override
	public Map<String, WebSocketSession> userMap() {
		return users;
	}

}

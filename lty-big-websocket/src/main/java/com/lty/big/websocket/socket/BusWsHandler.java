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
 * 推送大数据展示车辆相关的页面的展示信息
 * 
 * @描述:
 * @作者: hgy
 * @创建时间: 2017年8月10日
 * @版本: 1.0
 */
public class BusWsHandler extends BasicWsHandler {
	protected static final Logger logger = LoggerFactory
			.getLogger(BusWsHandler.class);

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
		logger.info("BusWsHandler user size:---" + users.size());
		// 推送车辆统计信息给前端
		String msg = FileHelper.readJson("301.txt");
		TextMessage txt = new TextMessage(msg);
		session.sendMessage(txt);

		msg = FileHelper.readJson("304.txt");
		txt = new TextMessage(msg);
		session.sendMessage(txt);
	}

	@Override
	public Map<String, WebSocketSession> userMap() {
		return users;
	}

}

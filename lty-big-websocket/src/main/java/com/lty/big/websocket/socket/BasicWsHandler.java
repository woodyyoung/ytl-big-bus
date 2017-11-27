package com.lty.big.websocket.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public abstract class BasicWsHandler extends TextWebSocketHandler {

	public abstract Map<String, WebSocketSession> userMap();

	protected static final Logger logger = LoggerFactory
			.getLogger(BasicWsHandler.class);

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		userMap().remove(session.getId());
		logger.info(session.getRemoteAddress() + " leaved!---");
		logger.info("user size:---" + userMap().size());
	}

	public static void pushMsgToAllUser(Map<String, WebSocketSession> users,
			TextMessage message) {
		List<String> sendFailSession = new ArrayList<String>();
		// 推送消息给前端
		for (Entry<String, WebSocketSession> user : users.entrySet()) {
			String sessionID = user.getKey();
			WebSocketSession session = user.getValue();
			try {
				synchronized (session) {
					session.sendMessage(message);
				}
			} catch (Exception e) {
				sendFailSession.add(sessionID);
				logger.error("push msg to user error!", e);
			}
		}

		// 把发送失败的websocket会话关闭
		for (String sessionID : sendFailSession) {
			WebSocketSession session = users.get(sessionID);
			if (session == null) {
				continue;
			}
			try {
				session.close();
			} catch (IOException e) {
				logger.error("push msg to user close session error!", e);
			}
			users.remove(sessionID);
		}
	}

}

package com.lty.big.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.lty.big.websocket.socket.BusWsHandler;
import com.lty.big.websocket.socket.LineWsHandler;
import com.lty.big.websocket.socket.StationWsHandler;
import com.lty.big.websocket.socket.WsHandlerInterceptor;

/**
 * websocket配置
 * 
 * @描述:
 * @作者: hgy
 * @创建时间: 2017年8月10日
 * @版本: 1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 线路相关信息推送的websoket
		registry.addHandler(lineWsHandler(), "/lineWs")
				.addInterceptors(wsInterceptor()).setAllowedOrigins("*");

		// 站点相关信息推送的websoket
		registry.addHandler(stationWsHandler(), "/stationWs")
				.addInterceptors(wsInterceptor()).setAllowedOrigins("*");

		// 车辆相关信息推送的websoket
		registry.addHandler(busWsHandler(), "/busWs")
				.addInterceptors(wsInterceptor()).setAllowedOrigins("*");

	}

	@Bean
	public WebSocketHandler lineWsHandler() {
		return new LineWsHandler();
	}

	@Bean
	public WsHandlerInterceptor wsInterceptor() {
		return new WsHandlerInterceptor();
	}

	@Bean
	public WebSocketHandler stationWsHandler() {
		return new StationWsHandler();
	}

	@Bean
	public WebSocketHandler busWsHandler() {
		return new BusWsHandler();
	}
}

package com.navy.netty;


import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.navy.component.SocketClientComponent;

import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
@Component
public class NettySocketIoServer implements InitializingBean,DisposableBean{
	
	@Autowired
	private SocketIOServer socketIOServer;
	
	@Autowired
	private SocketClientComponent socketClientComponent;
	/**
	 * connetNum
	 */
	public static AtomicInteger onlineCount = new AtomicInteger(0);
	/**
	 * connecting begin
	 * @param client
	 */
	@OnConnect
	public void onConnect(SocketIOClient client) {
		this.socketClientComponent.storeClientId(client);
		onlineCount.addAndGet(1);
		log.info("connect success:{},connectClients：[{}]" + getParamsFromClient(client),onlineCount.get());
	}
	/**
	 * dinconnect begin
	 * @param client
	 */
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		this.socketClientComponent.delClientId(client);
		onlineCount.addAndGet(-1);
		log.info("disconnect success:{},connectClients:[{}]" + getParamsFromClient(client),onlineCount.get());
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		start();
	}
	
	/**
	 *  start  netty socketio
	 */
	private void start() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				socketIOServer.start();
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	/**
	 * close netty socketio
	 */
	@Override
	public void destroy() throws Exception {
		if(socketIOServer != null) {
			socketIOServer.stop();
		}
	}
	
	private String getParamsFromClient(SocketIOClient client) {
		
		HandshakeData data = client.getHandshakeData();
		
		String userId = data.getSingleUrlParam("userId");
		String pageSign = data.getSingleUrlParam("pageSign");
		String token = data.getSingleUrlParam("token");
		
		return "userId:" + userId + ",pageSign:" + pageSign + ",token: " + token;
	}
	
//	@OnEvent(value="loginEvent")
//	public void loginEvent(SocketIOClient client, Map<String, Object> loginData) {
//		
//		String userId = (String) loginData.get("userId");
//		String pageId = (String) loginData.get("pageId");
//		
//		this.socketClientComponent.storeClient(userId, pageId, client);
//	}
}

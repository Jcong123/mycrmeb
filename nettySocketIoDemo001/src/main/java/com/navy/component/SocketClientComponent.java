package com.navy.component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;

/**
 * socketio client component
 * @author haishui211
 */
@Component
public class SocketClientComponent {

	public static Map<String, SocketIOClient> clients = new HashMap<String, SocketIOClient>();

	/**
	 * save socketio client
	 * @param client
	 */
	public void storeClientId(SocketIOClient client) {
		clients.put(getKeyFromClient(client), client);
	}
	
	/**
	 * delete socketio client
	 */
	public void delClientId(SocketIOClient client) {
		clients.remove(getKeyFromClient(client));
	}
	
	/**
	 * send to client by clientId ,eventDate
	 * @param businessName
	 * @param data
	 */
	public void send(String userId, String pageSign, String businessName, Map<String, Object> data) {
		SocketIOClient client = clients.get(getKey(userId, pageSign));
		if(client != null) {
			client.sendEvent(businessName, data);
		}
	}
	
	private String getKeyFromClient(SocketIOClient client) {
		HandshakeData data = client.getHandshakeData();
		String userId = data.getSingleUrlParam("userId");
		String pageSign = data.getSingleUrlParam("pageSign");
		return getKey(userId, pageSign);
	}
	
	private String getKey(String userId, String pageSign) {
		return "userId:" + userId + ":pageSign:" + pageSign;
	} 
}

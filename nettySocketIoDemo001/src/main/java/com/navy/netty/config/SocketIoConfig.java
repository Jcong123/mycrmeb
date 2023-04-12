package com.navy.netty.config;

import com.navy.netty.auth.SocketAuthListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;

@Configuration
public class SocketIoConfig {
	@Value("${netty.socket.io.host}")
	private String host;
	@Value("${netty.socket.io.port}")
	private int port;
	/**
	 * create socketIOServer obj
	 * @return
	 */
	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
		/**
		 * init authentication listener
		 */
		AuthorizationListener authorizationListener = new SocketAuthListener();
		config.setAuthorizationListener(authorizationListener);

        return new SocketIOServer(config);
	}
	
	/**
	 * set socketio client connecting security authentication
	 * @param config
	 */
	private void setAuthorizationListener(com.corundumstudio.socketio.Configuration config) {
		config.setAuthorizationListener(new AuthorizationListener() {
			@Override
			public boolean isAuthorized(HandshakeData data) {
				
				String userId = data.getSingleUrlParam("userId");
				String pageSign = data.getSingleUrlParam("pageSign");
				String token = data.getSingleUrlParam("token");
				System.out.println("userId:" + userId + ",pageSign:" + pageSign + ",token: " + token);
				
				return true;
			}
		});
	}
	
	/**
	 * open netty socketio @ann
	 * @param socketServer
	 * @return
	 */
	@Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}

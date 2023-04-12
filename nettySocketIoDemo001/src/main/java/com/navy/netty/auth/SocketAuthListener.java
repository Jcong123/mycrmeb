package com.navy.netty.auth;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.navy.netty.service.SocketAuthService;
import com.navy.netty.service.impl.DefaultSocketAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/** authentication
 * @Author: JCONG
 * @Description: authentication listener
 * @Date Created in 2023年 04 月 11 日  11:07
 * @Modified By:
 */

public class SocketAuthListener implements AuthorizationListener {
    @Autowired
    private SocketAuthService socketAuthService;
    @Override
    public boolean isAuthorized(HandshakeData handshakeData) {
        return socketAuthService.auth(handshakeData);
    }
    public SocketAuthListener(SocketAuthService socketAuthService){
        this.socketAuthService=socketAuthService;
    }
    public SocketAuthListener(){
        this.socketAuthService=new DefaultSocketAuthServiceImpl();
    }
}

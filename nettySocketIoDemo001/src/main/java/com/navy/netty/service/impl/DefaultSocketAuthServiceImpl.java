package com.navy.netty.service.impl;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.navy.component.SocketClientComponent;
import com.navy.netty.NettySocketIoServer;
import com.navy.netty.service.SocketAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: JCONG
 * @Description: default auth rules
 * @Date Created in 2023年 04 月 11 日  10:16
 * @Modified By:
 */
@Service
@Slf4j
public class DefaultSocketAuthServiceImpl implements SocketAuthService {
    private final static  String AUTHENTICATION_TOKEN="authentication_token";
    static final Map<String, SocketIOClient> socketMap;
    static {
        socketMap= SocketClientComponent.clients;
    }
    @Override
    public boolean auth(HandshakeData handshakeData) {
        // connect auth core
        String token = handshakeData.getSingleUrlParam("token");
        String userId = handshakeData.getSingleUrlParam("userId");
        String username = handshakeData.getSingleUrlParam("username");
        if (! StrUtil.equals(token,"1234")) {
            log.warn("auth token false");
            return false;
        }
        if (! StrUtil.equals(userId,"1")){
            log.warn("auth userId false");
            return false;
        }
        log.info("user:{}ID[{}]========connect okk!!",username,userId);
        return true;
    }
}

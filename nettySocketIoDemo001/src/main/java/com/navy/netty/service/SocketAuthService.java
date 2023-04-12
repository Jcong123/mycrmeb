package com.navy.netty.service;

import com.corundumstudio.socketio.HandshakeData;

/**
 * @Author: JCONG
 * @Description:  auth interface
 * @Date Created in 2023年 04 月 11 日  10:13
 * @Modified By:
 */
public interface SocketAuthService {
    /**
     * auth connected client
     * @param handshakeData
     * @return
     */
    boolean auth(HandshakeData handshakeData);
}

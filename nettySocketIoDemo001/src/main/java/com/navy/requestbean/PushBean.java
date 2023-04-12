package com.navy.requestbean;

import lombok.Data;

/**
 * @Author: JCONG
 * @Description:
 * @Date Created in 2023年 04 月 11 日  9:24
 * @Modified By:
 */
@Data
public class PushBean {
    private  String eventName;
    private  String pageSign;
    private  String userId;
}

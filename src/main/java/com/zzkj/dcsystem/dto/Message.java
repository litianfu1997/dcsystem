package com.zzkj.dcsystem.dto;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-26 11:33
 */
@Data
public class Message {
    private String status;
    private String msg;

    public Message(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}

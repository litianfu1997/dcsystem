package com.zzkj.dcsystem.entity;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 10:39
 */
@Data
public class DcUser {
    private String userId;
    private String openId;
    private String sessionKey;
    private String nickName;
    private Integer gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
}

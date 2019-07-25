package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.entity.DcUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author JGZ
 * @Classname DcUserMapper
 * @Date 2019/7/23 11:55
 * @Email 1945282561@qq.com
 */
@Mapper
public interface DcUserMapper {

    /**
     * 插入用户
     * @param dcUser 用户实体
     */
    public void insertUser(DcUser dcUser);

    /**
     * 根据openid查询用户
     * @param openId 微信穿过来的openid
     * @return 含有userId的对象
     */
    public DcUser selectDcUserByOpenId(String openId);

    /**
     * 根据用户id查询所有的用户信息
     * @param userId
     * @return 包含用户的userId
     */
    public DcUser selectDcUserByUserId(String userId);
}

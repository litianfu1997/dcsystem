package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.entity.DcUser;
import org.apache.ibatis.annotations.Mapper;

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
}
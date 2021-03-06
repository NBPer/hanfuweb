package com.jzt.service;

import com.jzt.entity.UserEntity;

import java.util.List;

/**
* 功能描述：
* @Author: sj
* @Date: 2020/10/15 20:32
*/
public interface IUserService {
    
    List<UserEntity> findAll();

    UserEntity fineById(Integer id);

    void save(UserEntity user);

    void update(UserEntity user);

    void delete(Integer id);
}

package com.jzt.dao;

import com.jzt.entity.UserEntity;

import java.util.List;

public interface IUserDao {

    List<UserEntity> findAll();

    UserEntity fineById(Integer id);

    void save(UserEntity test);

    void update(UserEntity test);

    void delete(Integer id);
}

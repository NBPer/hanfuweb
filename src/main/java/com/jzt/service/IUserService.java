package com.jzt.service;

import com.jzt.entity.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> findAll();

    UserEntity fineById(Integer id);

    void save(UserEntity test);

    void update(UserEntity test);

    void delete(Integer id);
}

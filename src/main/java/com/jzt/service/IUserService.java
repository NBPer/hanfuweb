package com.jzt.service;

import com.jzt.entity.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> findAll();

    UserEntity fineById(Integer id);

    void save(UserEntity user);

    void update(UserEntity user);

    void delete(Integer id);
}

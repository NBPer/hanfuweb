package com.jzt.dao.impl;

import com.jzt.dao.IUserDao;
import com.jzt.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImlp implements IUserDao {
    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public UserEntity fineById(Integer id) {
        return null;
    }

    @Override
    public void save(UserEntity test) {

    }

    @Override
    public void update(UserEntity test) {

    }

    @Override
    public void delete(Integer id) {

    }
}

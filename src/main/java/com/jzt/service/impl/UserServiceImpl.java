package com.jzt.service.impl;

import com.jzt.dao.IUserDao;
import com.jzt.entity.UserEntity;
import com.jzt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserEntity fineById(Integer id) {
        return userDao.fineById(id);
    }

    @Override
    public void save(UserEntity user) {
        userDao.save(user);
    }

    @Override
    public void update(UserEntity user) {
        userDao.update(user);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }
}

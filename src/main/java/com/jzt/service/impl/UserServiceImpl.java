package com.jzt.service.impl;

import com.jzt.dao.IUserDao;
import com.jzt.entity.UserEntity;
import com.jzt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao testDao;

    @Override
    public List<UserEntity> findAll() {
        return testDao.findAll();
    }

    @Override
    public UserEntity fineById(Integer id) {
        return testDao.fineById(id);
    }

    @Override
    public void save(UserEntity test) {
        testDao.save(test);
    }

    @Override
    public void update(UserEntity test) {
        testDao.update(test);
    }

    @Override
    public void delete(Integer id) {
        testDao.delete(id);
    }
}

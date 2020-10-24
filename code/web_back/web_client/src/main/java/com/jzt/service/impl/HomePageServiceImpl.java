package com.jzt.service.impl;

import com.github.pagehelper.PageHelper;
import com.jzt.dao.IHomePageDao;
import com.jzt.entity.HomePageEntity;
import com.jzt.service.IHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 功能描述：
* @Author: sj
* @Date: 2020/10/15 20:32
*/
@Service("homePageService")
public class HomePageServiceImpl implements IHomePageService {

    @Autowired
    private IHomePageDao homePageDao;


    @Override
    public HomePageEntity findById(Integer id) {
        return homePageDao.findById(id);
    }
}

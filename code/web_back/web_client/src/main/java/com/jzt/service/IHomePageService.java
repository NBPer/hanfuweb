package com.jzt.service;

import com.jzt.entity.HomePageEntity;

/**
* 功能描述：
* @Author: sj
* @Date: 2020/10/15 20:32
*/

public interface IHomePageService {
    
//    List<PostPageEntity> findAll();

    HomePageEntity findById(Integer id);

}

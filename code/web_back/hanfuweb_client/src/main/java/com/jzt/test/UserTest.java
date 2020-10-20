package com.jzt.test;

import com.jzt.entity.UserEntity;
import com.jzt.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/17 7:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserTest {
    @Autowired
    private IUserService userService;

    @Test
    public void getUser(){
        UserEntity user=new UserEntity();
        user.setName("zhangsan");
        user.setAge(18);
        userService.save(user);
    }

}

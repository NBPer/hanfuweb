package com.jzt.controller;

import com.jzt.entity.UserEntity;
import com.jzt.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private IUserService userService;

    @RequestMapping("/findAll")
    public String findAll(){
        logger.info("表示层：查询所有用户");
        List<UserEntity> userEntityList = userService.findAll();
        logger.info("result : " + userEntityList);
        return "userlist";
    }
}

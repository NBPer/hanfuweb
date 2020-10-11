package com.jzt.controller;

import com.jzt.entity.UserEntity;
import com.jzt.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 视图层（controller）
 * @author sj
 */
@Controller
@RequestMapping("/user")
@Api(value = "/user", tags = "User接口")
public class UserController {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private IUserService userService;

    @RequestMapping("/findAll")
    @ResponseBody
    @ApiOperation(value = "获取所有用户信息", notes = "获取所有用户信息", httpMethod = "GET", response = UserEntity.class)
    public ResponseEntity findAll(){
        logger.info("表示层：查询所有用户");
        List<UserEntity> userEntityList = userService.findAll();
        logger.info("result : " + userEntityList);
        return ResponseEntity.ok(userEntityList);
    }
}

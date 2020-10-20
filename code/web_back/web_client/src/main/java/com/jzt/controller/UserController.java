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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        List<UserEntity> userEntityList = null;
        try{
            userEntityList = userService.findAll();
            logger.info("result : " + userEntityList);
        }catch (Exception e){
            logger.error("[findAll] find all users error:"+e.getMessage(), e);
        }

        return ResponseEntity.ok(userEntityList);
    }

    @RequestMapping("/find/{id}")
    @ResponseBody
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息", httpMethod = "GET", response = UserEntity.class)
    public ResponseEntity find(@PathVariable("id") Integer id){
        UserEntity userEntity = null;
        try{
            userEntity = userService.fineById(id);
            logger.info("result : " + userEntity);
        }catch (Exception e){
            logger.error("[findAll] find user by id error:"+e.getMessage(), e);
        }
        return ResponseEntity.ok(userEntity);
    }

    @RequestMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存用户信息", notes = "保存用户信息", httpMethod = "POST", response = UserEntity.class)
    public ResponseEntity save(UserEntity user){
        try{
            userService.save(user);
        }catch (Exception e){
            logger.error("[save] save user error:"+e.getMessage(), e);
        }
        return ResponseEntity.ok("");
    }
}

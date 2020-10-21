package com.jzt.controller;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jzt.comm.ResultModel;
import com.jzt.dao.IPostPageDao;
import com.jzt.entity.PostPageEntity;
import com.jzt.entity.UserEntity;
import com.jzt.service.IPostPageService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 视图层（controller）
 * @author sj
 */
@Controller
@RequestMapping("/postpage")
@Api(value = "/postpage", tags = "PostPage接口")
public class PostPageController {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private IPostPageService postPageService;

    @RequestMapping("/findAll")
    @ResponseBody
    @ApiOperation(value = "获取所有页面信息", notes = "获取所有页面信息", httpMethod = "GET", response = PostPageEntity.class)
    public ResponseEntity findAll(){
        List<PostPageEntity> postPageEntityList = null;
        try{
            postPageEntityList = postPageService.findAll();
            logger.info("result : " + postPageEntityList);
        }catch (Exception e){
            logger.error("[findAll] find all users error:"+e.getMessage(), e);
        }
        return ResponseEntity.ok(postPageEntityList);
    }

    @RequestMapping("/find/{id}")
    @ResponseBody
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息", httpMethod = "GET", response = PostPageEntity.class)
    public ResultModel find(@PathVariable("id") Integer id){
        ResultModel reuslt = ResultModel.ok();
        try{
            PostPageEntity postPageEntity = postPageService.findById(id);
            reuslt.setData(postPageEntity);
            logger.info("result : " + postPageEntity);
        }catch (Exception e){
            logger.error("[findAll] find user by id error:"+e.getMessage(), e);
        }
        return reuslt;
    }

    @RequestMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存用户信息", notes = "保存用户信息", httpMethod = "POST", response = PostPageEntity.class)
    public ResponseEntity save(PostPageEntity postPage){
        try{
            postPageService.save(postPage);
        }catch (Exception e){
            logger.error("[save] save user error:"+e.getMessage(), e);
        }
        return ResponseEntity.ok("");
    }
}

package com.jzt.controller;

import com.jzt.comm.ResultModel;
import com.jzt.entity.HomePageEntity;
import com.jzt.entity.PostPageEntity;
import com.jzt.service.IESService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/11/5 22:19
 */
@Controller
@RequestMapping("/es")
@Api(value = "/es", tags = "elasticsearch接口")
public class ESController {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private IESService esService;

    @RequestMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存post页面信息到es中", notes = "", httpMethod = "POST")
    public ResultModel save(@RequestBody PostPageEntity postPage){
        ResultModel result = ResultModel.ok();
        try{
            boolean flag = esService.savePostPageEntity(postPage, postPage.getId());
            result.setData(flag);
        }catch (Exception e){
            logger.error("[save] save postPage to es error:"+e.getMessage(), e);
        }
        return result;
    }

    @RequestMapping("/find/{id}")
    @ResponseBody
    @ApiOperation(value = "根据id获取人物主页信息", notes = "根据id获取人物主页信息", httpMethod = "GET", response = PostPageEntity.class)
    public ResultModel find(@PathVariable("id") Integer id){
        ResultModel result = ResultModel.ok();
        try{
            List<PostPageEntity> postPageEntityList = esService.findPostPage(id);
            result.setData(postPageEntityList);
            logger.info("result : " + postPageEntityList);
        }catch (Exception e){
            logger.error("[find] find postPage from es error:"+e.getMessage(), e);
        }
        return result;
    }

}

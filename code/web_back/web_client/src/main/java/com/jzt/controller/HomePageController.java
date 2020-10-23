package com.jzt.controller;

import com.jzt.comm.ResultModel;
import com.jzt.entity.HomePageEntity;
import com.jzt.service.IHomePageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/homeage")
@Api(value = "/homepage", tags = "homepage接口")
public class HomePageController {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private IHomePageService homePageService;

    @RequestMapping("/find/{id}")
    @ResponseBody
    @ApiOperation(value = "根据id获取人物主页信息", notes = "根据id获取人物主页信息", httpMethod = "GET", response = HomePageEntity.class)
    public ResultModel find(@PathVariable("id") Integer id){
        ResultModel reuslt = ResultModel.ok();
        try{
            HomePageEntity homePageEntity = homePageService.findById(id);
            reuslt.setData(homePageEntity);
            logger.info("result : " + homePageEntity);
        }catch (Exception e){
            logger.error("[find] find homepage by id error:"+e.getMessage(), e);
        }
        return reuslt;
    }

}

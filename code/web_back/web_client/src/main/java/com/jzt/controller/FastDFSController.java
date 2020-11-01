package com.jzt.controller;

import com.jzt.comm.ResultModel;
import com.jzt.entity.HomePageEntity;
import com.jzt.service.IFastDFSService;
import com.jzt.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/31 15:59
 */
@Controller
@RequestMapping("/fastdfs")
@Api(value = "/fastdfs", tags = "fastdfs图片上传下载接口")
public class FastDFSController {

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private IFastDFSService fastDFSService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传图片", notes = "上传图片", httpMethod = "POST")
    public ResultModel upload(@RequestParam("file") MultipartFile file){
        ResultModel reuslt = ResultModel.ok();
        try{
            // 文件的存储位置
//            String aa = "C:\\Users\\sj\\Desktop";
//            String fileName = file.getOriginalFilename();
//            String[] rel = fastDFSService.upload(aa+"\\"+fileName);
            String[] rel = fastDFSService.upload(file);
            reuslt.setData(rel);
            logger.info("result : "+reuslt );
        }catch (Exception e){
            logger.error("[upload] upload photo error:"+e.getMessage(), e);
        }
        return reuslt;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "下载图片", notes = "下载图片", httpMethod = "GET")
    public ResultModel download(){
        ResultModel reuslt = ResultModel.ok();
        try{
            logger.info("result : " );
        }catch (Exception e){
            logger.error("[download] download photo error:"+e.getMessage(), e);
        }
        return reuslt;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除图片", notes = "删除图片", httpMethod = "GET")
    public ResultModel delete(){
        ResultModel reuslt = ResultModel.ok();
        try{
            logger.info("result : " );
        }catch (Exception e){
            logger.error("[delete] delete photo error:"+e.getMessage(), e);
        }
        return reuslt;
    }

}

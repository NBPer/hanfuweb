package com.jzt.service;

import com.jzt.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* 功能描述：
* @Author: sj
* @Date: 2020/10/15 20:32
*/
public interface IFastDFSService {

    String[] upload(MultipartFile file);

    void download(String id);

    void delete(Integer id);
}

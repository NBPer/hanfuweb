package com.jzt.service.impl;

import com.jzt.entity.UserEntity;
import com.jzt.service.IFastDFSService;
import com.jzt.util.FastDFSUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/31 16:57
 */
@Service("fastDFSService")
public class IFastDFServiceImpl implements IFastDFSService {

    @Override
    public String[] upload(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf("."));
        try {
            File file = multipartFileToFile(multipartFile);
            FastDFSUtil fastDFSUtil = new FastDFSUtil();
            String[] result = fastDFSUtil.uploadFile(file, extName);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void download(String id) {

    }

    @Override
    public void delete(Integer id) {

    }
}

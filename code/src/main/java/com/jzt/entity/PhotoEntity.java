package com.jzt.entity;


import lombok.Data;

import java.sql.Timestamp;

/**
* 功能描述：图片实体类
* @Author: sj
* @Date: 2020/10/13 22:08
*/
@Data
public class PhotoEntity {


    /**
     * 图片主键id
     */
    private Long id;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 图片描述
     */
    private String discription;

    /**
     * 一级路径，/image
     */
    private String fir_path;

    /**
     * 二级路径，表示年，/2020
     */
    private String sec_path;

    /**
     * 三级路径，表示月份，/08
     */
    private String thr_path;

    /**
     * 四级路径，表示日期，/30
     */
    private String fou_path;

    /**
     * 文件名，30234211222.jpg
     */
    private String filename;

    /**
     * 文件uri：/image/2020/08/30234211222.jpg
     */
    private String uri;

    /**
     * 上传时间
     */
    private Timestamp imput_time;

    /**
     * 投稿人id（用户id）
     */
    private Integer user_id;

}

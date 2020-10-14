package com.jzt.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 功能描述：图片页面
 *
 * @Author: sj
 * @Date: 2020/10/14 20:45
 */
@Data
public class PostPageEntity {

    /**
     * 图片页面id
     */
    private Long id;

    /**
     * 图片页面标题
     */
    private String title;

    /**
     * 图片页面描述
     */
    private String discription;

    /**
     * 封面图片id
     */
    private Long cover_id;

    /**
     * 点击量
     */
    private Integer look_count;

    /**
     * 点击量
     */
    private Integer collect_count;

    /**
     * 页面创建时间
     */
    private Timestamp create_time;

}

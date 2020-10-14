package com.jzt.entity;

import java.sql.Timestamp;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/14 20:52
 */
public class PersonEntity {

    /**
     * 红人id
     */
    private Long id;

    /**
     * 红人昵称
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 微博id
     */
    private String blogID;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 点击量
     */
    private String sign;

    /**
     * 页面创建时间
     */
    private Timestamp regist_time;
}

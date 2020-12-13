package com.jzt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/22 21:10
 */
public class HomePageEntity {

    private Integer id;

    private String name;

    private String sex;

    private String blogID;

    private String city;

    private String sign;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date regist_time;

    private List<PostPageEntity> postPageEntities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBlogID() {
        return blogID;
    }

    public void setBlogID(String blogID) {
        this.blogID = blogID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(Date regist_time) {
        this.regist_time = regist_time;
    }

    public List<PostPageEntity> getPostPageEntities() {
        return postPageEntities;
    }

    public void setPostPageEntities(List<PostPageEntity> postPageEntities) {
        this.postPageEntities = postPageEntities;
    }

    @Override
    public String toString() {
        return "HomePageEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", blogID='" + blogID + '\'' +
                ", city='" + city + '\'' +
                ", sign='" + sign + '\'' +
                ", regist_time=" + regist_time +
                ", postPageEntities=" + postPageEntities +
                '}';
    }
}

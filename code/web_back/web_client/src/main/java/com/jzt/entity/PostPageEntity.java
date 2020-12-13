package com.jzt.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 功能描述：图片页面
 *
 * @Author: sj
 * @Date: 2020/10/14 20:45
 */
public class PostPageEntity{

    /**
     * 图片页面id
     */
    private Integer id;

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
    private PhotoEntity photoEntity;

    /**
     * 点击量
     */
    private Integer look_count;

    /**
     * 收藏量
     */
    private Integer collect_count;

    /**
     * 页面创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    /**
     * 投稿人（用户id）
     */
    private UserEntity userEntity;

    private List<PhotoEntity> photoEntities;

    private List<TagEntity> tagEntities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Integer getLook_count() {
        return look_count;
    }

    public void setLook_count(Integer look_count) {
        this.look_count = look_count;
    }

    public Integer getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(Integer collect_count) {
        this.collect_count = collect_count;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public PhotoEntity getPhotoEntity() {
        return photoEntity;
    }

    public void setPhotoEntity(PhotoEntity photoEntity) {
        this.photoEntity = photoEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<PhotoEntity> getPhotoEntities() {
        return photoEntities;
    }

    public void setPhotoEntities(List<PhotoEntity> photoEntities) {
        this.photoEntities = photoEntities;
    }

    public List<TagEntity> getTagEntities() {
        return tagEntities;
    }

    public void setTagEntities(List<TagEntity> tagEntities) {
        this.tagEntities = tagEntities;
    }

    @Override
    public String toString() {
        return "PostPageEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", discription='" + discription + '\'' +
                ", cover_id=" + photoEntity +
                ", look_count=" + look_count +
                ", collect_count=" + collect_count +
                ", create_time=" + create_time +
                ", user_id=" + userEntity +
                ", photoEntities=" + photoEntities +
                ", tagEntities=" + tagEntities +
                '}';
    }
}

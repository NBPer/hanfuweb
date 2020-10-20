package com.jzt.entity;


import lombok.Data;

import java.sql.Timestamp;

/**
* 功能描述：图片实体类
* @Author: sj
* @Date: 2020/10/13 22:08
*/
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getFir_path() {
        return fir_path;
    }

    public void setFir_path(String fir_path) {
        this.fir_path = fir_path;
    }

    public String getSec_path() {
        return sec_path;
    }

    public void setSec_path(String sec_path) {
        this.sec_path = sec_path;
    }

    public String getThr_path() {
        return thr_path;
    }

    public void setThr_path(String thr_path) {
        this.thr_path = thr_path;
    }

    public String getFou_path() {
        return fou_path;
    }

    public void setFou_path(String fou_path) {
        this.fou_path = fou_path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Timestamp getImput_time() {
        return imput_time;
    }

    public void setImput_time(Timestamp imput_time) {
        this.imput_time = imput_time;
    }

//    @Override
//    public String toString() {
//        return "PhotoEntity{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", discription='" + discription + '\'' +
//                ", fir_path='" + fir_path + '\'' +
//                ", sec_path='" + sec_path + '\'' +
//                ", thr_path='" + thr_path + '\'' +
//                ", fou_path='" + fou_path + '\'' +
//                ", filename='" + filename + '\'' +
//                ", uri='" + uri + '\'' +
//                ", imput_time=" + imput_time +
//                '}';
//    }
}
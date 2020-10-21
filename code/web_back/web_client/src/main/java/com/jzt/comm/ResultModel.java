package com.jzt.comm;

/**
 * 功能描述：统一API响应结果封装
 *
 * @Author: sj
 * @Date: 2020/10/21 21:36
 */
public final class ResultModel<T> {

    private int code;

    private String message;

    private T data;

    public ResultModel() {
    }

    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultModel(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResultModel ok(){
        return new ResultModel(CodeStatus.SUCCESS.getCode(), CodeStatus.SUCCESS.getMsg());
    }
}

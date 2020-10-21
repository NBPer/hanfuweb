package com.jzt.comm;

/**
 * 功能描述：响应状态码和说明枚举类
 *
 * @Author: sj
 * @Date: 2020/10/21 21:40
 */
public enum CodeStatus {

    SUCCESS(0, "成功！"),
    FAIL(1, "失败，未知错误！")
    ;

    /**
     * 响应状态码
     */
    private final int code;

    /**
     * 响应提示
     */
    private final String msg;

    CodeStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

package com.echofeng.common.net;

import com.google.gson.JsonElement;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: ResultData
 * Author: echo
 * Date: 2019/10/25 18:37
 * Description: 外层数据
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class ResultDataNoAes {
    private int code;//    "code": 0,
    private String message;//            "errorMsg": "",
    private JsonElement data;//            "data":

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }
    @Override
    public String toString() {
        return "ResultData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }


}

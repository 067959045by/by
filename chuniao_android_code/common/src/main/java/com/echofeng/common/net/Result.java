package com.echofeng.common.net;


import com.net.core.model.ApiResult;

import java.io.Serializable;

/**
 * <p>描述：如果你的不是约定的标准ApiResult，你可以继承ApiResult后扩展</p>
 * 1.继承时要写全extends ApiResult<T>{} 不能写成extends ApiResult{}<br>
 * 2.code、msg、data哪个字段不一样就覆写谁，不用全部覆写<br>
 */
public class Result<T> extends ApiResult<T> implements Serializable {
    @Override
    public void setCode(int code) {
        super.setCode(code);
    }

    @Override
    public int getCode() {
        return super.getCode();
    }

    @Override
    public void setData(T data) {
        super.setData(data);
    }

    @Override
    public T getData() {
        return super.getData();
    }

    @Override
    public void setMsg(String msg) {
        super.setMsg(msg);
    }

    @Override
    public String getMsg() {
        return super.getMsg();
    }

    @Override
    public boolean isOk() {
        return getCode() == 1;//如果不是1表示成功，请重写isOk()方法。
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

package com.lianzhihui.minitiktok.config;

public class MessageEvent {
    private int code;
    private Object value;
    public MessageEvent(int code, Object value){
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

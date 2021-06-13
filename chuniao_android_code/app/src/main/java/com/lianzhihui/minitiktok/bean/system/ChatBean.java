package com.lianzhihui.minitiktok.bean.system;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ChatBean implements MultiItemEntity {
    public static final int TYPE_ME = 1;//    type	number
    public static final int TYPE_HE = 2;//    type	number
    private int type;//    type	number
    private String content;//    content	string
    private String rid;//    rid	number
    private String avatar;//    avatar	string
    private String time;//    time	string

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int getItemType() {
        if (TextUtils.isEmpty(getRid())){
            return TYPE_ME;
        }else {
            return TYPE_HE;
        }
    }
}

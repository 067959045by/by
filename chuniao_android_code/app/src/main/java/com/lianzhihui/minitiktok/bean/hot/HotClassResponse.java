package com.lianzhihui.minitiktok.bean.hot;

import java.io.Serializable;

public class HotClassResponse implements Serializable {
    private String id;//    id	number 必须
    private String title;//    title	string 标题
    private String cover;//    cover	string 封面

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}

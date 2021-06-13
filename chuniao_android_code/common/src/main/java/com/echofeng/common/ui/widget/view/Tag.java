package com.echofeng.common.ui.widget.view;

public class Tag {
    private String id;
    private String content;
    public Tag(String id,String content){
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

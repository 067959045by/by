package com.lianzhihui.minitiktok.bean.system;

public class ReportBean {
    private boolean isSelected;
    private String content;
    public ReportBean(String content){
        setContent(content);
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

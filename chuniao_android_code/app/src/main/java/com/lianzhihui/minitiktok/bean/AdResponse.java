package com.lianzhihui.minitiktok.bean;

public class AdResponse {
    private int id;//    id	number非必须
    private String position;//    position	string广告位
    private String cover;//    cover	string封面
    private String link;//    link	string链接地址跳转协议，协议内容如果以url:// 开头则跳转外部网页；如果以video://开头，则跳转内部指定视频播放页；如果以user://开头，则跳转内部用户资料页面
    private String type;//    type	number1图片2视频
    private String play;//    play	string播放地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }
}

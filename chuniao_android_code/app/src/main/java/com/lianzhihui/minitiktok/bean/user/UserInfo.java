package com.lianzhihui.minitiktok.bean.user;

import java.io.Serializable;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: UserInfo
 * Author: echo
 * Date: 2019/11/18 17:13
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class UserInfo implements Serializable {
    private String code;//    code	string邀请码
    private String nick;//    nick	string昵称
    private String coins;//    coins	number金币
    private String diamonds;//    diamonds	number钻石
    private String avatar;//    avatar	string头像路径
    private String intro;//    intro	string个人简介
    private int gender = 1;//    gender	number1男,2女,3机器人
    private String city;//    city	string城市
    private String is_vip;//    is_vip	string是否为vip会员:y-是 n-不是
    private String play_num_notice;//    play_num_notice	string会员剩余天数文字，如 无限观看，如剩余天数20 天
    private String bind_mobile;//    bind_mobile	string绑定的手机号
    private int flow;//    flow	number关注数
    private int fans;//    fans	number粉丝数
    private String years;//    years	number年龄
    private String birth;//    years	number年龄
    private int like;//    like	number点赞数
    private String videos;//    videos	number视频数
    private String invite;//    invite	number邀请人数
    private String has_parent;//    has_parent	string是否存在父级推荐人，y-存在 n-不存在
    private String account_qrcode_content;//    account_qrcode_content	string找回账号的凭证
    private int is_follow;//    是否关注（1-已关注 0-没有）

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(String diamonds) {
        this.diamonds = diamonds;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getPlay_num_notice() {
        return play_num_notice;
    }

    public void setPlay_num_notice(String play_num_notice) {
        this.play_num_notice = play_num_notice;
    }

    public String getBind_mobile() {
        return bind_mobile;
    }

    public void setBind_mobile(String bind_mobile) {
        this.bind_mobile = bind_mobile;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public String getHas_parent() {
        return has_parent;
    }

    public void setHas_parent(String has_parent) {
        this.has_parent = has_parent;
    }

    public String getAccount_qrcode_content() {
        return account_qrcode_content;
    }

    public void setAccount_qrcode_content(String account_qrcode_content) {
        this.account_qrcode_content = account_qrcode_content;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}

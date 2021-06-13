package com.lianzhihui.minitiktok.bean.user;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: AddressInfo
 * Author: echo
 * Date: 2019/10/29 16:21
 * Description: 地址信息
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class AddressInfo extends RealmObject implements Serializable {
    private String addressId;
    private String nick;
    private String describe;
    private String address;
    private String coinName;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
}

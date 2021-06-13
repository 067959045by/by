package com.lianzhihui.minitiktok.bean.user;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: WalletInfo
 * Author: echo
 * Date: 2019/10/25 18:34
 * Description: 钱包信息
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class WalletInfo extends RealmObject implements Serializable {

//    private String compressedPubkey;//            "compressedPubkey": "03270b9c6541154567f171b819a25748f207d30fba6fd2e56254130eab0a7923c3",
//    private String compressedPrivkey;//

    private boolean currentUser;
    private boolean syncState;//是否同步
    private String address;//            "address": "1GEyjec8GD6AR4FE46BpiJYsr9APfWzuA8",
    private String userName;//            "userName"
    private String saftyCode;//            "saftyCode"
    private String mnemonicWords;//    "mnemonicWords": "fade crane horse ranch forum ignore curve rose obvious grow combine crumble",
    private String compressedPubkey;//            "compressedPubkey": "03270b9c6541154567f171b819a25748f207d30fba6fd2e56254130eab0a7923c3",
    private String compressedPrivkey;//            "compressedPrivkey": "KxSFoAyy3VkVZisQoR7kZf4V9By9suT1r1qQZCjrmvohqgQsQGn3"
    private String bIP39Seed;//            "bIP39Seed": "6bb49633f305477d5d8c1a665b3ddaac8a0430de7f4dfcb272f4c62ece7e3da13ef83265b859bb233a6a5d4d649d6404f69f3f8a50066194657bc93efbe8eff6",
    private String path;//            "path": "m/0/0",
    private String phone;//            "phone"
    private String ciphertext;//           ciphertext
    private String userUuid;
    private String headPathUrl;
    public String getUserUuid() {
        return userUuid;
    }

    public String getHeadPathUrl() {
        return headPathUrl;
    }

    public void setHeadPathUrl(String headPathUrl) {
        this.headPathUrl = headPathUrl;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getMnemonicWords() {
        return mnemonicWords;
    }

    public void setMnemonicWords(String mnemonicWords) {
        this.mnemonicWords = mnemonicWords;
    }

    public String getbIP39Seed() {
        return bIP39Seed;
    }

    public void setbIP39Seed(String bIP39Seed) {
        this.bIP39Seed = bIP39Seed;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompressedPubkey() {
        return compressedPubkey;
    }

    public void setCompressedPubkey(String compressedPubkey) {
        this.compressedPubkey = compressedPubkey;
    }

    public String getCompressedPrivkey() {
        return compressedPrivkey;
    }

    public void setCompressedPrivkey(String compressedPrivkey) {
        this.compressedPrivkey = compressedPrivkey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSaftyCode() {
        return saftyCode;
    }

    public void setSaftyCode(String saftyCode) {
        this.saftyCode = saftyCode;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public boolean isCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        this.currentUser = currentUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSyncState() {
        return syncState;
    }

    public void setSyncState(boolean syncState) {
        this.syncState = syncState;
    }

    @Override
    public String toString() {
        return "WalletInfo{" +
                "currentUser=" + currentUser +
                ", syncState=" + syncState +
                ", address='" + address + '\'' +
                ", userName='" + userName + '\'' +
                ", saftyCode='" + saftyCode + '\'' +
                ", mnemonicWords='" + mnemonicWords + '\'' +
                ", publicKey='" + compressedPubkey + '\'' +
                ", privateKey='" + compressedPrivkey + '\'' +
                ", bIP39Seed='" + bIP39Seed + '\'' +
                ", path='" + path + '\'' +
                ", phone='" + phone + '\'' +
                ", ciphertext='" + ciphertext + '\'' +
                ", userUuid='" + userUuid + '\'' +
                ", headPathUrl='" + headPathUrl + '\'' +
                '}';
    }
}

package com.lianzhihui.minitiktok.bean.user;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: CountryCode
 * Author: echo
 * Date: 2019/11/20 14:20
 * Description: 国家和地区、
 * History:
 * 作者姓名 修改时间 版本号 描述
 */
public class CountryCode {
    private String countryNameTC;//     "countryNameTC": "中國",
    private String countryNameSC;//             "countryNameSC": "中国",
    private String countryCode;//             "countryCode": "+86",
    private String id;//             "id": 1,
    private String countryNameEN;//             "countryNameEN": "China"

    public String getCountryNameTC() {
        return countryNameTC;
    }

    public void setCountryNameTC(String countryNameTC) {
        this.countryNameTC = countryNameTC;
    }

    public String getCountryNameSC() {
        return countryNameSC;
    }

    public void setCountryNameSC(String countryNameSC) {
        this.countryNameSC = countryNameSC;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryNameEN() {
        return countryNameEN;
    }

    public void setCountryNameEN(String countryNameEN) {
        this.countryNameEN = countryNameEN;
    }
}

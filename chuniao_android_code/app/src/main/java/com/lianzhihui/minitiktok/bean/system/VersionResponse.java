package com.lianzhihui.minitiktok.bean.system;

public class VersionResponse {
    //    id	number非必须
//    title	string更新标题
//    remark	string 更新内容
//    device_type	string设备类型
//    force	number是否强制更新
//    version_code	string版本号
//    package_path	string包地址
//    status	number
    private String id;
    private String title;
    private String remark;
    private String device_type;
    private int force;
    private String version_code;
    private String package_path;
    private String status;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getPackage_path() {
        return package_path;
    }

    public void setPackage_path(String package_path) {
        this.package_path = package_path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

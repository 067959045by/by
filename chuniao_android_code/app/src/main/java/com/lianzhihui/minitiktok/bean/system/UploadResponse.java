package com.lianzhihui.minitiktok.bean.system;

public class UploadResponse {
    private String file_name;//    file_name	string文件名
    private String file_size;//    file_size	string文件大小
    private String file_path;//    file_path	string文件相对路径

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}

package com.lianzhihui.minitiktok.bean.hot;

import java.util.List;

public class AlbumVideoResponse {
    private List<VideoResponse> video_list;
    private AlbumInfo album_info;

    public List<VideoResponse> getVideo_list() {
        return video_list;
    }

    public void setVideo_list(List<VideoResponse> video_list) {
        this.video_list = video_list;
    }

    public AlbumInfo getAlbum_info() {
        return album_info;
    }

    public void setAlbum_info(AlbumInfo album_info) {
        this.album_info = album_info;
    }

    public class AlbumInfo{
        private String album_name;//        album_name	string合集标题
        private String set;//        set

        public String getAlbum_name() {
            return album_name;
        }

        public void setAlbum_name(String album_name) {
            this.album_name = album_name;
        }

        public String getSet() {
            return set;
        }

        public void setSet(String set) {
            this.set = set;
        }
    }
}

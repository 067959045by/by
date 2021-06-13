package com.lianzhihui.minitiktok.bean.hot;

import java.util.List;

public class MostHotResponse {
    private List<VideoResponse> hot_video;
    private List<Perfect> perfect_list;

    public List<VideoResponse> getHot_video() {
        return hot_video;
    }

    public void setHot_video(List<VideoResponse> hot_video) {
        this.hot_video = hot_video;
    }

    public List<Perfect> getPerfect_list() {
        return perfect_list;
    }

    public void setPerfect_list(List<Perfect> perfect_list) {
        this.perfect_list = perfect_list;
    }

    public class Perfect{
        private String id;//        id	number
        private String title;//        title	string精品标题
        private List<VideoResponse> get_perfect_video;

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

        public List<VideoResponse> getGet_perfect_video() {
            return get_perfect_video;
        }

        public void setGet_perfect_video(List<VideoResponse> get_perfect_video) {
            this.get_perfect_video = get_perfect_video;
        }
    }

}

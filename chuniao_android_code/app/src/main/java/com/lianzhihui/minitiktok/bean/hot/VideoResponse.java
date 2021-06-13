package com.lianzhihui.minitiktok.bean.hot;

import java.io.Serializable;
import java.util.List;

public class VideoResponse implements Serializable {
    private boolean canPlay;//    id	视频id
    private String id;//    id	视频id
    private int is_ad;//    is_ad	integer 是否是广告 1=时；0=否
    private Ad ad;
    private User user;
    private AuthError auth_error;
    private List<Category> category;
    private String title;//        title	string 视频标题
    private String cover;//        cover	string 视频封面
    private String smu;//        smu	string m3u8链接
    private String mu;//        mu	string m3u8链接
    private int like;//        like	number 点赞数
    private int is_like;//        is_like	integer 是否点赞1是0否
    private int coins;//        coins	integer 金币数
    private String is_recommend;//        is_recommend	integer 是否推荐1是0否
    private String play_total;//        play_total	number 播放次数
    private String uid;//        uid	string  用户ID
    private String from;//        from	number  0 系统自建
    private String is_fans_watch;//        is_fans_watch	number 是否仅粉丝 0否1是
    private String time;//        time	number  时长
    private String city;//        city	string 城市
    private int comment;//        comment	number 评论数
    private int share;//        share	number 评论数
    private int is_free;//        is_free	number 1为免费
    private String key;//        key	string 加密密匙
    private String iv;//        iv	string 加密向量
    private int is_album;//        is_album	integer  是否属于合集1是0否
    private String album_id;//        album_id	integer 合集ID

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSmu() {
        return smu;
    }

    public void setSmu(String smu) {
        this.smu = smu;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getIs_like() {
        return is_like;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getPlay_total() {
        return play_total;
    }

    public void setPlay_total(String play_total) {
        this.play_total = play_total;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getIs_fans_watch() {
        return is_fans_watch;
    }

    public void setIs_fans_watch(String is_fans_watch) {
        this.is_fans_watch = is_fans_watch;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public int getIs_album() {
        return is_album;
    }

    public void setIs_album(int is_album) {
        this.is_album = is_album;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIs_ad() {
        return is_ad;
    }

    public void setIs_ad(int is_ad) {
        this.is_ad = is_ad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public AuthError getAuth_error() {
        return auth_error;
    }

    public void setAuth_error(AuthError auth_error) {
        this.auth_error = auth_error;
    }

    public String getMu() {
        return mu;
    }

    public void setMu(String mu) {
        this.mu = mu;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public class User implements Serializable{
        private String avatar;//        avatar	string用户头像
        private String code;//        code	string用户code
        private String nick;//        nick	string用户昵称
        private int is_follow;//        is_follow	integer1是0否

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

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

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }
    }
    public class Category implements Serializable{
        private String type_id;//"type_id": 7,
        private String title;//        "title": "\u4f53\u80b2"

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
    public class Ad implements Serializable{
        private int id;//        id	number
        private String position;//        position	string
        private String title;//        title	string
        private String sub_title;//        sub_title	string
        private String cover;//        cover	string封面地址
        private String product_logo;//        product_logo	string封面地址
        private String start_time;//        start_time	string
        private String end_time;//        end_time	string
        private int click;//        click	number
        private int sort;//        sort	number
        private String link;//        link	stringurl://http://www.baidu.com 根据协议不同跳转不同
        private int status;//        status	number
        private int type;//        type    1为图片2为视频 为视频时用play地址播放string非必须 视频播放地址
        private String play;//        type    广告视频播放地址 m3u8视频，type=2时有效
        private String created_at;//        created_at
        private String updated_at;//        updated_at
        private String deleted_at;//        deleted_at

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getClick() {
            return click;
        }

        public void setClick(int click) {
            this.click = click;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getProduct_logo() {
            return product_logo;
        }

        public void setProduct_logo(String product_logo) {
            this.product_logo = product_logo;
        }

        public String getPlay() {
            return play;
        }

        public void setPlay(String play) {
            this.play = play;
        }
    }
    public class AuthError{
        private int key;//        key	number 错误码 1001=次数已用完，开通VIP享不限次观,1002=金币视频查看完整版需支付金币,1003=粉丝视频仅限粉丝观看
        private String info;//        info	string错误提示

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}

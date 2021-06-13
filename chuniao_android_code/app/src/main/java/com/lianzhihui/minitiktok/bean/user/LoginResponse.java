package com.lianzhihui.minitiktok.bean.user;


public class LoginResponse {
    private LoginAuth auth = new LoginAuth();
    private LoginStartup startup = new LoginStartup();
    private LoginConfig config = new LoginConfig();

    public LoginAuth getAuth() {
        return auth;
    }

    public void setAuth(LoginAuth auth) {
        this.auth = auth;
    }

    public LoginStartup getStartup() {
        return startup;
    }

    public void setStartup(LoginStartup startup) {
        this.startup = startup;
    }

    public LoginConfig getConfig() {
        return config;
    }

    public void setConfig(LoginConfig config) {
        this.config = config;
    }

    //    token	string
//    jwt授权token
//    expired_at	string
//    过期日期
    public class LoginAuth{
        private String token;
        private String expired_at;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(String expired_at) {
            this.expired_at = expired_at;
        }
    }
    public class LoginStartup{
        private String id;// "id": 1,
        private int type;//         "type": 1,
        private String play;//         "play": "http://dsafddto.me/test.m3u8",
        private int status;//         "status": 1,
        private String cover;//         "cover": "http://dsafddto.me/test.ceb",
        private String position;//         "position": "startup",
        private String link;//         "link": "url://http://www.baidu.com"

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPlay() {
            return play;
        }

        public void setPlay(String play) {
            this.play = play;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
    public class LoginConfig{
        private Rule rule = new Rule();
        private Sys sys = new Sys();

        public Rule getRule() {
            return rule;
        }

        public void setRule(Rule rule) {
            this.rule = rule;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }
    }
    public class Rule{
        private String withdraw;//   "withdraw _fee": "30",
        private String withdraw_money;//           "withdraw_money": "200",
        private String min_coins;//           "min_coins": "0",
        private String max_coins;//           "max_coins": "50",
        private String vip_intro;//           "vip_intro": "当前微信通道资源较少，微信支付不成功时请选择支付宝进行支付",
        private String coins_intro;//           "coins_intro": "金币可以用来购买金币视频，也可以兑换成余额提现哦！",
        private String feedback_type;//           "feedback_type": "视频加载速度慢/出不来,视频播放失败/卡顿,没有喜欢的内容,视频看过了,视频上传后不清晰,视频搜不到,账号丢失,会员购买失败,付不了款,上传审核慢,上传视频丢失,其他问题"

        public String getWithdraw() {
            return withdraw;
        }

        public void setWithdraw(String withdraw) {
            this.withdraw = withdraw;
        }

        public String getWithdraw_money() {
            return withdraw_money;
        }

        public void setWithdraw_money(String withdraw_money) {
            this.withdraw_money = withdraw_money;
        }

        public String getMin_coins() {
            return min_coins;
        }

        public void setMin_coins(String min_coins) {
            this.min_coins = min_coins;
        }

        public String getMax_coins() {
            return max_coins;
        }

        public void setMax_coins(String max_coins) {
            this.max_coins = max_coins;
        }

        public String getVip_intro() {
            return vip_intro;
        }

        public void setVip_intro(String vip_intro) {
            this.vip_intro = vip_intro;
        }

        public String getCoins_intro() {
            return coins_intro;
        }

        public void setCoins_intro(String coins_intro) {
            this.coins_intro = coins_intro;
        }

        public String getFeedback_type() {
            return feedback_type;
        }

        public void setFeedback_type(String feedback_type) {
            this.feedback_type = feedback_type;
        }
    }
    public class Sys{
        private String app_notice;// "app_notice": "",
        private String notice;//         "notice": "0",
        private String upload_url;//         "upload_url": "http://dsafddto.me",
        private String share_text;//         "share_text": "短视频，万部热门视频各种分类全都有！支持原创，与百万用户分享视频，拥有顶级流量现金红利！下载请点击：{{share_url}}",
        private String share_url;//         "share_url": "http://static.dylite.mx"

        public String getApp_notice() {
            return app_notice;
        }

        public void setApp_notice(String app_notice) {
            this.app_notice = app_notice;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getUpload_url() {
            return upload_url;
        }

        public void setUpload_url(String upload_url) {
            this.upload_url = upload_url;
        }

        public String getShare_text() {
            return share_text;
        }

        public void setShare_text(String share_text) {
            this.share_text = share_text;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
    }
}

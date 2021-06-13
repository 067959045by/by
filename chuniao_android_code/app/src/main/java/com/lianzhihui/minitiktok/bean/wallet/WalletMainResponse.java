package com.lianzhihui.minitiktok.bean.wallet;

import java.util.List;

public class WalletMainResponse {
    private String coins;//    coins	integer 总钻石数
    private String diamonds;//    diamonds	integer 总钻石数
    private String proxy_diamonds;//    proxy_diamonds	integer 代理钻石数
    private String creator_diamonds;//    creator_diamonds	integer 创造者钻石数
    private List<VipItem> vip_list;
    private List<VipItem> coin_list;

    public List<VipItem> getVip_list() {
        return vip_list;
    }

    public void setVip_list(List<VipItem> vip_list) {
        this.vip_list = vip_list;
    }

    public List<VipItem> getCoin_list() {
        return coin_list;
    }

    public void setCoin_list(List<VipItem> coin_list) {
        this.coin_list = coin_list;
    }

    public String getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(String diamonds) {
        this.diamonds = diamonds;
    }

    public String getProxy_diamonds() {
        return proxy_diamonds;
    }

    public void setProxy_diamonds(String proxy_diamonds) {
        this.proxy_diamonds = proxy_diamonds;
    }

    public String getCreator_diamonds() {
        return creator_diamonds;
    }

    public void setCreator_diamonds(String creator_diamonds) {
        this.creator_diamonds = creator_diamonds;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public class VipItem{
        private boolean selected;
        private String id;//    id	number
        private String title;// title	string标题
        private String remark;//    remark	string备注
        private String intro;//    intro	string介绍
        private String coins;//    coins	string价格
        private String price;//    price	string价格
        private String cover;//    cover	stringVIP卡图片，当前版本前端直接进行展示
        private String level;//    level	integer卡等级 0普通卡,1超级会员
        private String type;//    type	integer卡类型 0普通卡,1仅新手,2仅老用户
        private String give_away_num;//    give_away_num	integer赠送数量
        private String give_away_type;//    give_away_type	integer赠送类型；0不赠送,1赠送会员,2赠送金币,3赠送钻石
        private String buy_notice;//    buy_notice	string 购买小提示
        private List<Payment> payment;//    payment	string 购买小提示

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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGive_away_num() {
            return give_away_num;
        }

        public void setGive_away_num(String give_away_num) {
            this.give_away_num = give_away_num;
        }

        public String getGive_away_type() {
            return give_away_type;
        }

        public void setGive_away_type(String give_away_type) {
            this.give_away_type = give_away_type;
        }

        public String getBuy_notice() {
            return buy_notice;
        }

        public void setBuy_notice(String buy_notice) {
            this.buy_notice = buy_notice;
        }

        public List<Payment> getPayment() {
            return payment;
        }

        public void setPayment(List<Payment> payment) {
            this.payment = payment;
        }

        public String getCoins() {
            return coins;
        }

        public void setCoins(String coins) {
            this.coins = coins;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
    public class Payment{
        private String id;//        id	string 必须
        private String name;//        name	string支付类型名称
        private String img;//        img	string支付方式封面
        private boolean selected;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}

package com.gwkj.qixiubaodian.obd.module.login.emty;

import java.io.Serializable;

/**
 * author : Administrator
 * time   : 2018/04/18
 * desc   :
 * version: 1.0
 */

public class User implements Serializable{

    /**
     * status : ok
     * data : {"chengshi":"深圳","jingyan":22,"headimgurl":"http://thirdwx.qlogo.cn/mmopen/8yGzELw0PHl3lDOvFAVY9tFCY46xu0gnWxcbmficia6wxc6ZxMMvtvNicfWH4iaGGN6nW2udTkwicKPFj8uF1TUncaTnILsmrb1oa/132","home_city":"深圳","rz_zhuangtai":"0","jieshao":"举娘舅如日眼到与","nickname":"金融","shouji":"13532116770","suoshugongzhong":"机修","xianyouzhicheng":"高级","xiuchenianxian":"3-5年","dashi_zhuangtai":"1","is_master":"1","shenfen_zhuangtai":"2","zhiye_zhuangtai":"-2","reg_zhuangtai":"1","addr_work_linkman":"","addr_work_tel":"","addr_work":"","addr_gongsi":"深圳","gongsi_tel":"123456","gongsimingcheng":"深圳","tuiguang":"12866","friends":"8","fensi":"0","friend_fensi":"0","experience":22,"star":"0","normal_medal":"0","master_medal":"0","active_medal":"0","master_medal_img":"","normal_medal_img":"","active_medal_img":""}
     * token : {"loginid":"90ae6cd9628e1a66c25b3eb1be8a06b3","openid":"oBPBmw0jqu3YOiK60SaJHq462kcI","uid":"135731"}
     */

    private String status;
    private DataBean data;
    private TokenBean token;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public TokenBean getToken() {
        return token;
    }

    public void setToken(TokenBean token) {
        this.token = token;
    }

    public static class DataBean {
        /**
         * chengshi : 深圳
         * jingyan : 22
         * headimgurl : http://thirdwx.qlogo.cn/mmopen/8yGzELw0PHl3lDOvFAVY9tFCY46xu0gnWxcbmficia6wxc6ZxMMvtvNicfWH4iaGGN6nW2udTkwicKPFj8uF1TUncaTnILsmrb1oa/132
         * home_city : 深圳
         * rz_zhuangtai : 0
         * jieshao : 举娘舅如日眼到与
         * nickname : 金融
         * shouji : 13532116770
         * suoshugongzhong : 机修
         * xianyouzhicheng : 高级
         * xiuchenianxian : 3-5年
         * dashi_zhuangtai : 1
         * is_master : 1
         * shenfen_zhuangtai : 2
         * zhiye_zhuangtai : -2
         * reg_zhuangtai : 1
         * addr_work_linkman :
         * addr_work_tel :
         * addr_work :
         * addr_gongsi : 深圳
         * gongsi_tel : 123456
         * gongsimingcheng : 深圳
         * tuiguang : 12866
         * friends : 8
         * fensi : 0
         * friend_fensi : 0
         * experience : 22
         * star : 0
         * normal_medal : 0
         * master_medal : 0
         * active_medal : 0
         * master_medal_img :
         * normal_medal_img :
         * active_medal_img :
         */

        private String chengshi;
        private int jingyan;
        private int is_vip;
        private String headimgurl;
        private String home_city;
        private String rz_zhuangtai;
        private String jieshao;
        private String nickname;
        private String shouji;
        private String suoshugongzhong;
        private String xianyouzhicheng;
        private String xiuchenianxian;
        private String dashi_zhuangtai;
        private String is_master;
        private String shenfen_zhuangtai;
        private String zhiye_zhuangtai;
        private String reg_zhuangtai;
        private String addr_work_linkman;
        private String addr_work_tel;
        private String addr_work;
        private String addr_gongsi;
        private String gongsi_tel;
        private String gongsimingcheng;
        private String tuiguang;
        private String friends;
        private String fensi;
        private String friend_fensi;
        private String experience;
        private String star;
        private int normal_medal;
        private int master_medal;
        private int active_medal;
        private String master_medal_img;
        private String normal_medal_img;
        private String active_medal_img;
        private String pinpai_id;
        private String pinpai_name;
        private String 	moderator_type;//-1 预备版主，0 非版主，1 正式版主

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public String getPinpai_id() {
            return pinpai_id;
        }

        public void setPinpai_id(String pinpai_id) {
            this.pinpai_id = pinpai_id;
        }

        public String getPinpai_name() {
            return pinpai_name;
        }

        public void setPinpai_name(String pinpai_name) {
            this.pinpai_name = pinpai_name;
        }

        public String getModerator_type() {
            return moderator_type;
        }

        public void setModerator_type(String moderator_type) {
            this.moderator_type = moderator_type;
        }

        public String getChengshi() {
            return chengshi;
        }

        public void setChengshi(String chengshi) {
            this.chengshi = chengshi;
        }

        public int getJingyan() {
            return jingyan;
        }

        public void setJingyan(int jingyan) {
            this.jingyan = jingyan;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getHome_city() {
            return home_city;
        }

        public void setHome_city(String home_city) {
            this.home_city = home_city;
        }

        public String getRz_zhuangtai() {
            return rz_zhuangtai;
        }

        public void setRz_zhuangtai(String rz_zhuangtai) {
            this.rz_zhuangtai = rz_zhuangtai;
        }

        public String getJieshao() {
            return jieshao;
        }

        public void setJieshao(String jieshao) {
            this.jieshao = jieshao;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getShouji() {
            return shouji;
        }

        public void setShouji(String shouji) {
            this.shouji = shouji;
        }

        public String getSuoshugongzhong() {
            return suoshugongzhong;
        }

        public void setSuoshugongzhong(String suoshugongzhong) {
            this.suoshugongzhong = suoshugongzhong;
        }

        public String getXianyouzhicheng() {
            return xianyouzhicheng;
        }

        public void setXianyouzhicheng(String xianyouzhicheng) {
            this.xianyouzhicheng = xianyouzhicheng;
        }

        public String getXiuchenianxian() {
            return xiuchenianxian;
        }

        public void setXiuchenianxian(String xiuchenianxian) {
            this.xiuchenianxian = xiuchenianxian;
        }

        public String getDashi_zhuangtai() {
            return dashi_zhuangtai;
        }

        public void setDashi_zhuangtai(String dashi_zhuangtai) {
            this.dashi_zhuangtai = dashi_zhuangtai;
        }

        public String getIs_master() {
            return is_master;
        }

        public void setIs_master(String is_master) {
            this.is_master = is_master;
        }

        public String getShenfen_zhuangtai() {
            return shenfen_zhuangtai;
        }

        public void setShenfen_zhuangtai(String shenfen_zhuangtai) {
            this.shenfen_zhuangtai = shenfen_zhuangtai;
        }

        public String getZhiye_zhuangtai() {
            return zhiye_zhuangtai;
        }

        public void setZhiye_zhuangtai(String zhiye_zhuangtai) {
            this.zhiye_zhuangtai = zhiye_zhuangtai;
        }

        public String getReg_zhuangtai() {
            return reg_zhuangtai;
        }

        public void setReg_zhuangtai(String reg_zhuangtai) {
            this.reg_zhuangtai = reg_zhuangtai;
        }

        public String getAddr_work_linkman() {
            return addr_work_linkman;
        }

        public void setAddr_work_linkman(String addr_work_linkman) {
            this.addr_work_linkman = addr_work_linkman;
        }

        public String getAddr_work_tel() {
            return addr_work_tel;
        }

        public void setAddr_work_tel(String addr_work_tel) {
            this.addr_work_tel = addr_work_tel;
        }

        public String getAddr_work() {
            return addr_work;
        }

        public void setAddr_work(String addr_work) {
            this.addr_work = addr_work;
        }

        public String getAddr_gongsi() {
            return addr_gongsi;
        }

        public void setAddr_gongsi(String addr_gongsi) {
            this.addr_gongsi = addr_gongsi;
        }

        public String getGongsi_tel() {
            return gongsi_tel;
        }

        public void setGongsi_tel(String gongsi_tel) {
            this.gongsi_tel = gongsi_tel;
        }

        public String getGongsimingcheng() {
            return gongsimingcheng;
        }

        public void setGongsimingcheng(String gongsimingcheng) {
            this.gongsimingcheng = gongsimingcheng;
        }

        public String getTuiguang() {
            return tuiguang;
        }

        public void setTuiguang(String tuiguang) {
            this.tuiguang = tuiguang;
        }

        public String getFriends() {
            return friends;
        }

        public void setFriends(String friends) {
            this.friends = friends;
        }

        public String getFensi() {
            return fensi;
        }

        public void setFensi(String fensi) {
            this.fensi = fensi;
        }

        public String getFriend_fensi() {
            return friend_fensi;
        }

        public void setFriend_fensi(String friend_fensi) {
            this.friend_fensi = friend_fensi;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public int getNormal_medal() {
            return normal_medal;
        }

        public void setNormal_medal(int normal_medal) {
            this.normal_medal = normal_medal;
        }

        public int getMaster_medal() {
            return master_medal;
        }

        public void setMaster_medal(int master_medal) {
            this.master_medal = master_medal;
        }

        public int getActive_medal() {
            return active_medal;
        }

        public void setActive_medal(int active_medal) {
            this.active_medal = active_medal;
        }

        public String getMaster_medal_img() {
            return master_medal_img;
        }

        public void setMaster_medal_img(String master_medal_img) {
            this.master_medal_img = master_medal_img;
        }

        public String getNormal_medal_img() {
            return normal_medal_img;
        }

        public void setNormal_medal_img(String normal_medal_img) {
            this.normal_medal_img = normal_medal_img;
        }

        public String getActive_medal_img() {
            return active_medal_img;
        }

        public void setActive_medal_img(String active_medal_img) {
            this.active_medal_img = active_medal_img;
        }
    }

    public static class TokenBean {
        /**
         * loginid : 90ae6cd9628e1a66c25b3eb1be8a06b3
         * openid : oBPBmw0jqu3YOiK60SaJHq462kcI
         * uid : 135731
         */

        private String loginid;
        private String openid;
        private String uid;

        public String getLoginid() {
            return loginid;
        }

        public void setLoginid(String loginid) {
            this.loginid = loginid;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}

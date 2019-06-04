package com.gwkj.qixiubaodian.obd.module.login.emty;

/**
 * author : Administrator
 * time   : 2018/04/12
 * desc   :
 * version: 1.0
 */

public class LoginItem {
    
    /**
     * status : ok
     * data : {"openid":"osr890Y4o_UkTaOA2KaO1ovnOrkw","loginid":"3127fc6713a5275e24c3a3cc91df517c","uid":"985318"}
     */
    
    private String status;
    private DataBean data;
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status=status;
    }
    
    public DataBean getData(){
        return data;
    }
    
    public void setData(DataBean data){
        this.data=data;
    }
    
    public static class DataBean {
        /**
         * openid : osr890Y4o_UkTaOA2KaO1ovnOrkw
         * loginid : 3127fc6713a5275e24c3a3cc91df517c
         * uid : 985318
         */
        
        private String openid;
        private String loginid;
        private String uid;
        
        public String getOpenid(){
            return openid;
        }
        
        public void setOpenid(String openid){
            this.openid=openid;
        }
        
        public String getLoginid(){
            return loginid;
        }
        
        public void setLoginid(String loginid){
            this.loginid=loginid;
        }
        
        public String getUid(){
            return uid;
        }
        
        public void setUid(String uid){
            this.uid=uid;
        }
    }
}

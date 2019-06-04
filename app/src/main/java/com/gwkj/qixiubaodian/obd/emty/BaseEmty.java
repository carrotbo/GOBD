package com.gwkj.qixiubaodian.obd.emty;

/**
 * author : Administrator
 * time   : 2018/03/28
 * desc   :
 * version: 1.0
 */

public class BaseEmty {
    
    /**
     * status : ok
     * data : []
     * token : {"loginid":"d0f0e780bae93541b640d1a3c0acff5e","openid":"oBPBmw4AicBJk-VDPp2X21mG5w7o","uid":"139517"}
     */
    
    private String status;
    private TokenBean token;
    private String message;
   
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status=status;
    }
    
    public TokenBean getToken(){
        return token;
    }
    
    public void setToken(TokenBean token){
        this.token=token;
    }
    
    
    public String getMessage(){
        return message;
    }
    
    public void setMessage(String message){
        this.message=message;
    }
    
    public static class TokenBean {
        /**
         * loginid : d0f0e780bae93541b640d1a3c0acff5e
         * openid : oBPBmw4AicBJk-VDPp2X21mG5w7o
         * uid : 139517
         */
        
        private String loginid;
        private String openid;
        private String uid;
        
        public String getLoginid(){
            return loginid;
        }
        
        public void setLoginid(String loginid){
            this.loginid=loginid;
        }
        
        public String getOpenid(){
            return openid;
        }
        
        public void setOpenid(String openid){
            this.openid=openid;
        }
        
        public String getUid(){
            return uid;
        }
        
        public void setUid(String uid){
            this.uid=uid;
        }
    }
}

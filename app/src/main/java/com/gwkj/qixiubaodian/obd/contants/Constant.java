package com.gwkj.qixiubaodian.obd.contants;

/**
 * 接口类
 * Created by admin on 2017/3/13.
 */

public class Constant {


            public static final String INTERNET = "https://qxbd.haohaoxiuche.com";
//    public static final String INTERNET = "https://csqxbd.haohaoxiuche.com";
      public static final String BASE_URL="http://www.haohaoxiuche.com/wxqxbd/baodian/api/api_uds.php?";
      public static final String MPCHART=BASE_URL+"type=get_stream";//图表数据
      public static final String FAULT_DETAIL=BASE_URL+"type=get_obdcode&code=";//图表数据


    /**
     * 用户注册模块
     */
    public static final String LOGIN = INTERNET + "/qxbd_new/api/user/login/mobile";//登陆

    public static final String GETUSER = INTERNET + "/qxbd_new/api/user/info/detail";//个人信息

    /* 通用参数 */
    public static String Coding = "";


}

package com.gwkj.qixiubaodian.obd.contants;

/**
 * Created by admin on 2018/3/21.
 */


import android.util.Log;

import com.google.gson.Gson;

/**
 * @author wck
 *
 */
public class JSONUtil {
    /**
     * 将对象转换为json格式字符串
     *
     * @param obj
     * @return json string
     */
//    public static String toJSON(Object obj) {
//        ObjectMapper om = new ObjectMapper();
//        String json = null;
//        try {
//            json = om.writeValueAsString(obj);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return json;
//    }

    /**
     *
     * 将json形式字符串转换为java实体类
     *
     */
    public static <T> T parse(String result, Class<T> clazz) {
        Log.d("response", result);
        try{
            if (result != null && result.length() > 0) {
                Gson gson = new Gson();
                T responseObject = gson.fromJson(result, clazz);
                return responseObject;
            } else {
                Log.d("response", "服务器无响应内容");
            }
        }catch(Exception e){
            return null;
        }
        return null;
    }
}

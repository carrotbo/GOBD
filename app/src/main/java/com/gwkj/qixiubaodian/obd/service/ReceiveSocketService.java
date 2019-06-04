package com.gwkj.qixiubaodian.obd.service;

import android.os.Handler;
import android.os.Message;

import com.gwkj.qixiubaodian.obd.appliaction.BltAppliaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Luhao on 2016/9/28.
 * 接收消息的服务
 */
public class ReceiveSocketService {
    private static Handler mhandler;
    private static boolean isread=true;
    private static boolean isbyte=false;
    public static void receiveMessage(Handler handler) {
        if(handler!=null) {
            mhandler = handler;
        }
        if (BltAppliaction.bluetoothSocket == null || handler == null) return;
        try {
            InputStream inputStream = BltAppliaction.bluetoothSocket.getInputStream();
            // 从客户端获取信息
            BufferedReader bff = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String json="";
            isread=true;

                while (isread&&BltAppliaction.bluetoothSocket!=null&&bff!=null&&(json = bff.readLine()) != null) {
                    if(json.length()>1) {
                        Message message = new Message();
                        message.obj = json;
                        message.what = 1;
                        mhandler.sendMessage(message);
                    }


                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setIsread(){
        isread=false;
    }
    public static void setReceiveHandle(Handler handler){
        mhandler=handler;
    }

}

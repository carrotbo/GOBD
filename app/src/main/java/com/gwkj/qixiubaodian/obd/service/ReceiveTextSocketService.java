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
public class ReceiveTextSocketService {
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
            byte[] temp = new byte[256];// 默认最带为256
            int size = 0;
            String line = "";
            while ((size = inputStream.read(temp)) > 0) {
                // -1表示文件结尾
                byte[] res = new byte[size];// 默认最带为256

                System.arraycopy(temp, 0, res, 0, size);
                line=byte2HexStr(res);
                Message message = new Message();
                        message.obj = line;
                        message.what = 1;
                        mhandler.sendMessage(message);
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
    public static String byte2HexStr(byte[] b)
    {
        String stmp="";
        StringBuilder sb = new StringBuilder("");
        for (int n=0;n<b.length;n++)
        {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length()==1)? "0"+stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }
}

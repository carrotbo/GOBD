package com.gwkj.qixiubaodian.obd.service;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.gwkj.qixiubaodian.obd.appliaction.BltAppliaction;
import com.gwkj.qixiubaodian.obd.contants.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.gwkj.qixiubaodian.obd.manager.BltBleManager.mBluetoothGatt;

/**
 * Created by Luhao on 2016/9/28.
 * 发送消息的服务
 */
public class SendSocketService {

    /**
     * 发送文本消息
     *
     * @param message
     */
    public static void sendMessage(String message, Handler handle) {
        if (BltAppliaction.bluetoothSocket == null || TextUtils.isEmpty(message)) return;
        String cmd=message;
        try {
            message += "\r";
            OutputStream outputStream = BltAppliaction.bluetoothSocket.getOutputStream();
            outputStream.write(message.getBytes());
            outputStream.flush();
            Log.e("send OBD",message);
        } catch (IOException e) {
            e.printStackTrace();
            if(handle!=null) {
                Message msg=new Message();
                msg.obj=cmd;
                msg.what=8;
                handle.sendMessageDelayed(msg,500);
            }

        }
    }
    public static void sendBleMessage(String message, Handler handle){
        //往蓝牙数据通道的写入数据
        String cmd=message;
        if(mBluetoothGatt!=null&&message!=null&&!message.isEmpty()) {
            BluetoothGattService service = mBluetoothGatt.getService(Common.ser_UUID);
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(Common.char_UUID);
            message += "\r";
            characteristic.setValue(message.getBytes());

            for (BluetoothGattDescriptor dp : characteristic.getDescriptors()) {
                if (dp != null) {
                    if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
                        dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    } else if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
                        dp.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                    }
                    mBluetoothGatt.writeDescriptor(dp);
                }
            }
            mBluetoothGatt.setCharacteristicNotification(characteristic,true);//设置该特征具有Notification功能
            characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            mBluetoothGatt.writeCharacteristic(characteristic);
            Log.e("===", "写入:"+message);
        }else{
            if(handle!=null) {
                Message msg=new Message();
                msg.obj=cmd;
                msg.what=8;
                handle.sendMessageDelayed(msg,500);
            }
        }
    }
    /**
     * 发送文件
     */
    public static void sendMessageByFile(String filePath) {
        if (BltAppliaction.bluetoothSocket == null || TextUtils.isEmpty(filePath)) return;
        try {
            OutputStream outputStream = BltAppliaction.bluetoothSocket.getOutputStream();
            //要传输的文件路径
            File file = new File(filePath);
            //说明不存在该文件
            if (!file.exists()) return;
            //说明该文件是一个文件夹
            if (file.isDirectory()) return;
            //1、发送文件信息实体类
            outputStream.write("file".getBytes("utf-8"));
            //将文件写入流
            FileInputStream fis = new FileInputStream(file);
            //每次上传1M的内容
            byte[] b = new byte[1024];
            int length;
            int fileSize = 0;//实时监测上传进度
            while ((length = fis.read(b)) != -1) {
                fileSize += length;
                Log.i("socketChat", "文件上传进度：" + (fileSize / file.length() * 100) + "%");
                //2、把文件写入socket输出流
                outputStream.write(b, 0, length);
            }
            //关闭文件流
            fis.close();
            //该方法无效
            //outputStream.write("\n".getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

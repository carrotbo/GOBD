package com.gwkj.qixiubaodian.obd.manager;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;


/**
 * Created by LuHao on 2016/9/26.
 * 蓝牙对象管理器
 * 蓝牙4.0 必须在api18 android4.3以上才能运行
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BltBleManager {
    private Context context;

    public static BluetoothAdapter mBluetoothAdapter;
    public static BluetoothGatt mBluetoothGatt;
    private UUID ser_UUID = UUID.fromString("00001800-0000-1000-8000-00805F9B34FB");
    private UUID char_UUID = UUID.fromString("0002a00-0000-1000-8000-00805F9B34FB");
    private Handler myhandler;
    /**
     * 设置成单例模式
     */
    private BltBleManager() {
    }

    private static class BltManagers {
        private static BltBleManager bltManager = new BltBleManager();
    }

    public static BltBleManager getInstance() {
        return BltManagers.bltManager;
    }
    public BluetoothAdapter getBluetoothAdapter(){
        return mBluetoothAdapter;
    }
    public void init(Activity context){
        this.context=context;
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(context, "您的设备不支持蓝牙BLE，将关闭", Toast.LENGTH_SHORT).show();
            return;        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    2);
        }
        final BluetoothManager bluetoothManager = (BluetoothManager)context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }
    /**
     * 判断是否支持蓝牙，并打开蓝牙
     * 获取到BluetoothAdapter之后，还需要判断是否支持蓝牙，以及蓝牙是否打开。
     * 如果没打开，需要让用户打开蓝牙：
     */
    public void checkBleDevice() {
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(enableBtIntent);
            }
        } else {
            Log.i("blueTooth", "该手机不支持蓝牙");
        }
    }
    public void connectBle(BluetoothDevice device,Handler handler){
        myhandler=handler;
        mBluetoothGatt = device.connectGatt(context, true, mGattCallback);
    }
    public void setHandler(Handler handler){
        myhandler=handler;
    }
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        //连接状态改变的回调
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // 连接成功后启动服务发现
                Log.e("AAAAAAAA", "启动服务发现:");
                mBluetoothGatt.discoverServices();
                myhandler.sendEmptyMessage(9);
                System.out.println("---------------------------->已经连接");
            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                // 连接断开
                /*连接断开后的相应处理*/
                Log.e("================", "连接断开:");
                gatt.close();
                myhandler.sendEmptyMessage(20);
                if(mBluetoothGatt!=null) {
                    BltBleManager.mBluetoothGatt.disconnect();
                    mBluetoothGatt = null;
                }
            }
        }

        //发现服务的回调
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.e("===", "成功发现服务");
            } else{
                Log.e("===", "服务发现失败，错误码为:" + status);
            }
        }

        //写操作的回调
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {

                UUID ser_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805F9B34FB");
                UUID char_UUID = UUID.fromString("000fff1-0000-1000-8000-00805F9B34FB");
                BluetoothGattService service = mBluetoothGatt.getService(ser_UUID);
                BluetoothGattCharacteristic mcharacteristic = service.getCharacteristic(char_UUID);
                mBluetoothGatt.setCharacteristicNotification(mcharacteristic, true);
                gatt.readCharacteristic(mcharacteristic);
                Log.e("===", "写入成功：" + new String(characteristic.getValue()));

            }else {
                Log.e("===", "发送失败：" );
            }
        }


        //读操作的回调
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
//            Log.e("===", "读取成功：" + new String(characteristic.getValue()));
            }


        }

        //数据返回的回调（此处接收BLE设备返回数据）
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//            Log.e("===", "返回数据：" + new String(characteristic.getValue()));
            Message msg=new Message();
            msg.what=1;
            msg.obj=new String(characteristic.getValue());
            myhandler.sendMessage(msg);
        }

    };

}

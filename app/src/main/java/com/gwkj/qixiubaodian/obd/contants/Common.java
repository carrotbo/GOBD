package com.gwkj.qixiubaodian.obd.contants;

import android.bluetooth.BluetoothDevice;

import java.util.UUID;

/**
 * Created by carrot on 2018/8/10.
 */

public class Common {

    public static String sendLock1 = "intCmdLock";
    public static String sendLock2 = "tableCmdLock";
    public static final int NEXT_BACK=3;
    public static final int LIST_DATA=4;
    public static BluetoothDevice blueDevice; //蓝牙设备
    public static UUID ser_UUID = UUID.fromString("00001800-0000-1000-8000-00805F9B34FB");
    public static UUID char_UUID = UUID.fromString("0002a00-0000-1000-8000-00805F9B34FB");
}

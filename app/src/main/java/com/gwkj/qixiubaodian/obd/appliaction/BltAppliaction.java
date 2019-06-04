package com.gwkj.qixiubaodian.obd.appliaction;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

import com.gwkj.qixiubaodian.obd.item.OBDItem;

/**
 * Created by Luhao on 2016/9/28.
 *
 */
public class BltAppliaction extends Application {

    //不管是蓝牙连接方还是服务器方，得到socket对象后都传入
    public static BluetoothSocket bluetoothSocket;
    public static OBDItem BaseOBD=null;

    public static OBDItem getBaseOBD() {
        return BaseOBD;
    }

    public static void setBaseOBD(OBDItem baseOBD) {
        BaseOBD = baseOBD;
    }
}

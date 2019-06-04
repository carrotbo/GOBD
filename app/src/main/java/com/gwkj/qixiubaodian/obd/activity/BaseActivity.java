package com.gwkj.qixiubaodian.obd.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gwkj.qixiubaodian.obd.Utils.Utils;
import com.gwkj.qixiubaodian.obd.view.LoadingDialog;

/**
 * Created by carrot on 2018/9/1.
 */

public class BaseActivity  extends AppCompatActivity {
    private MyBaseActiviy_Broad oBaseActiviy_Broad;
    private Dialog loadingDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //动态注册广播
        oBaseActiviy_Broad = new MyBaseActiviy_Broad();
        IntentFilter intentFilter = new IntentFilter("drc.xxx.yyy.baseActivity");
        registerReceiver(oBaseActiviy_Broad, intentFilter);
    }
    //在销毁的方法里面注销广播
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(oBaseActiviy_Broad);//注销广播
    }
    //定义一个广播
    public class MyBaseActiviy_Broad extends BroadcastReceiver {

        public void onReceive(Context arg0, Intent intent) {
//接收发送过来的广播内容
            Log.e("============","=============");
            int closeAll = intent.getIntExtra("closeAll", 0);
            if (closeAll == 1) {
                BaseActivity.this.finish();//销毁BaseActivity
            }

        }
    }
    public void showDialog(String text) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.createLoadingDialog(this, text);
            loadingDialog.show();
        }
    }
    /**
     * 关闭Dialog
     */
    public void closeDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
    public void toast(String text) {
        Utils.toastShow(this,text);
//        toast(this, text, Toast.LENGTH_SHORT);
    }
}


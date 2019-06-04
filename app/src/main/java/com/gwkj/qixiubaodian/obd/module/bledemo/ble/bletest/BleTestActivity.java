package com.gwkj.qixiubaodian.obd.module.bledemo.ble.bletest;


import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.adapter.BlueAdapter;
import com.gwkj.qixiubaodian.obd.contants.Common;
import com.gwkj.qixiubaodian.obd.databinding.BleDemoActivityBinding;
import com.gwkj.qixiubaodian.obd.manager.BltBleManager;
import com.gwkj.qixiubaodian.obd.mvp.MVPBaseActivity;
import com.gwkj.qixiubaodian.obd.service.SendSocketService;
import com.gwkj.qixiubaodian.obd.view.CommonDialogManager;
import com.gwkj.qixiubaodian.obd.view.CustomDialog;
import com.gwkj.qixiubaodian.obd.view.LoadingDialog;

import static com.gwkj.qixiubaodian.obd.manager.BltBleManager.mBluetoothAdapter;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BleTestActivity extends MVPBaseActivity<BleTestContract.View, BleTestPresenter> implements BleTestContract.View ,View.OnClickListener{

    private BlueAdapter myAdapter;
    private BluetoothDevice mdevice;

    private Dialog loadingDialog;
    private String revicedata="";

    private BleDemoActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ble_demo_activity);
        myAdapter = new BlueAdapter(getContext());
        initView();
        initData();

    }
    private void initView(){
        binding.tvSend.setOnClickListener(this);
    }
    private void initData() {

        //检查蓝牙是否开启
        BltBleManager.getInstance().init(this);
        BltBleManager.getInstance().checkBleDevice();
        BltBleManager.getInstance().getBluetoothAdapter().startLeScan(mLeScanCallback);

    }
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        /**
         * 简单说一下这三个参数的含义：
         * @param device：识别的远程设备
         * @param rssi：  RSSI的值作为对远程蓝牙设备的报告; 0代表没有蓝牙设备;
         * @param scanRecord：远程设备提供的配对号(公告)
         */
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            if (myAdapter != null && !myAdapter.getBlueList().contains(device)) {
                closeDialog();
                if (myAdapter.getCount() == 0) {
                    showBlueDialog();
                }
                myAdapter.addBlueList(device);
            }
        }
    };
    private void showBlueDialog(){
        CustomDialog dialog = CommonDialogManager.getInstance().createDialog(getContext(), R.layout.blue_list_item);
        dialog.setCdHelper(new CustomDialog.CustomDialogHelper() {
            @Override
            public void showDialog(final CustomDialog dialog) {
                ListView listView=(ListView)dialog.findViewById(R.id.blue_list);
                listView.setAdapter(myAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mdevice=(BluetoothDevice)myAdapter.getItem(i);

                        dialog.dismiss();
                        handler.sendEmptyMessageDelayed(6,100);
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                        mBluetoothAdapter.cancelDiscovery();
                        BltBleManager.getInstance().connectBle(mdevice,handler);
                    }
                });


            }
        });
        dialog.show();
    }
    private void showDialogLoad(String text) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.createLoadingDialog(getContext(), text);
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(BltBleManager.mBluetoothGatt!=null) {
            BltBleManager.mBluetoothGatt.disconnect();
        }
        if(mBluetoothAdapter!=null) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mBluetoothAdapter.cancelDiscovery();
        }
    }
    @Override
    public void onClick(View view) {
        if (view == binding.tvSend) {
            String message = binding.etCmd.getText().toString();
            if (message.equals("")) {
                Toast.makeText(this,"请输入OBD命令",Toast.LENGTH_LONG).show();
                return;
            }
            message += "\r";
            SendSocketService.sendBleMessage(message,handler);
        }
    }
    //
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String data=(String)msg.obj;
                    revicedata=revicedata+data+"\n";
                    binding.tvRevice.setText(revicedata);
                    break;
                case 6:
                    showDialogLoad("正在连接蓝牙");
                    break;
                case 9://设备已经接入
                    closeDialog();
                    Common.blueDevice=mdevice;
                    Toast.makeText(BleTestActivity.this,"连接成功",Toast.LENGTH_LONG).show();
                    break;
                case 20:
                    if(mdevice!=null) {//连接失败重连
                        Log.e("============","重连接。。");
                        BltBleManager.getInstance().connectBle(mdevice, handler);
                    }
                    break;
            }
        }
    };
}

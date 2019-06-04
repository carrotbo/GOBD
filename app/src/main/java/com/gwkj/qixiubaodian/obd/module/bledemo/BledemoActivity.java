package com.gwkj.qixiubaodian.obd.module.bledemo;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.adapter.BlueAdapter;
import com.gwkj.qixiubaodian.obd.contants.BltContant;
import com.gwkj.qixiubaodian.obd.databinding.BleDemoActivityBinding;
import com.gwkj.qixiubaodian.obd.manager.BltManager;
import com.gwkj.qixiubaodian.obd.mvp.MVPBaseActivity;
import com.gwkj.qixiubaodian.obd.service.ReceiveSocketService;
import com.gwkj.qixiubaodian.obd.service.ReceiveTextSocketService;
import com.gwkj.qixiubaodian.obd.service.SendSocketService;
import com.gwkj.qixiubaodian.obd.view.CommonDialogManager;
import com.gwkj.qixiubaodian.obd.view.CustomDialog;
import com.gwkj.qixiubaodian.obd.view.LoadingDialog;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BledemoActivity extends MVPBaseActivity<BledemoContract.View, BledemoPresenter> implements BledemoContract.View, View.OnClickListener {

    public static final int MESSAGE_STATE_CHANGE = 0;
    public static final int MESSAGE_DEVICE_NAME = 1; //名字
    public static final String DEVICE_NAME = "ble_name";
    public static final int MESSAGE_TOAST = 2;
    public static final int MESSAGE_READ = 3;
    public static final int MESSAGE_WRITE = 4;
    public static final String TOAST = "show";
    private BlueAdapter myAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private Dialog loadingDialog;
    private String revicedata="";

    private BleDemoActivityBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ble_demo_activity);
        myAdapter = new BlueAdapter(getContext());
        initView();


    }

    private void initView() {
        binding.tvSend.setOnClickListener(this);
        ReceiveSocketService.setIsread();

        //检查蓝牙是否开启
        BltManager.getInstance().checkBleDevice(getContext());
        //注册蓝牙扫描广播
        blueToothRegister();
        //第一次进来搜索设备
        BltManager.getInstance().clickBlt(getContext(), BltContant.BLUE_TOOTH_SEARTH);
    }
    /**
     * 注册蓝牙回调广播
     */
    private void blueToothRegister() {
        BltManager.getInstance().registerBltReceiver(getContext(), new BltManager.OnRegisterBltReceiver() {

            /**搜索到新设备
             * @param device
             */
            @Override
            public void onBluetoothDevice(final BluetoothDevice device) {
                if(device!=null&&device.getAddress()!=null) {

                    if (myAdapter != null&&!myAdapter.getBlueList().contains(device)) {
                        if(myAdapter.getCount()==0){
                            showBlueDialog();
                        }
                        myAdapter.addBlueList(device);
                    }

                }
            }

            /**连接中
             * @param device
             */
            @Override
            public void onBltIng(BluetoothDevice device) {
            }

            /**连接完成
             * @param device
             */
            @Override
            public void onBltEnd(final BluetoothDevice device) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        BltManager.getInstance().disConnectBlt();
                        BltManager.getInstance().stopSearthBltDevice();
                        BltManager.getInstance().createBond(device, myhandl);

                    }
                }).start();
            }

            /**取消链接
             * @param device
             */
            @Override
            public void onBltNone(BluetoothDevice device) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: // If request is cancelled, the result arrays are empty.
                if ((grantResults.length > 0)
                        && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) ;
                else Toast.makeText(this, "app没有定位权限将无法搜索蓝牙设备!", Toast.LENGTH_LONG);
                break;
            case 2:
                if ((grantResults.length > 0)
                        && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) ;
                else Toast.makeText(this, "app没有定位权限将无法搜索蓝牙设备!", Toast.LENGTH_LONG);
                break;
        }
    }

    private void showBlueDialog() {
        CustomDialog dialog = CommonDialogManager.getInstance().createDialog(getContext(), R.layout.blue_list_item);
        dialog.setCdHelper(new CustomDialog.CustomDialogHelper() {
            @Override
            public void showDialog(final CustomDialog dialog) {
                ListView listView = (ListView) dialog.findViewById(R.id.blue_list);
                listView.setAdapter(myAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final BluetoothDevice device=(BluetoothDevice)myAdapter.getItem(i);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                myhandl.sendEmptyMessageDelayed(6,100);
                                BltManager.getInstance().disConnectBlt();
                                BltManager.getInstance().stopSearthBltDevice();
                                BltManager.getInstance().createBond(device, myhandl);

                            }
                        }).start();
                    }
                });
            }
        });
        dialog.show();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReceiveTextSocketService.setIsread();
        myhandl.removeMessages(2);
        BltManager.getInstance().unregisterReceiver(getContext());
        BltManager.getInstance().disConnectBlt();
    }

    @SuppressLint("HandlerLeak")
    private Handler myhandl = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    break;
                case 1:
                    String data=(String)msg.obj;
                    revicedata=revicedata+data+"\n";
                    binding.tvRevice.setText(revicedata);
                    break;
                case 4:
                    closeDialog();
                    BltManager.getInstance().unregisterReceiver(getContext());
                    Toast.makeText(BledemoActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ReceiveTextSocketService.receiveMessage(myhandl);
                        }
                    }).start();
                    break;
                case 5://连接失败重连
                    removeMessages(2);
                    BluetoothDevice device2 = (BluetoothDevice) msg.obj;
                    BltManager.getInstance().createBond(device2, myhandl);
                    break;
                case 6:
                    showDialogLoad("正在连接蓝牙");
                    break;

            }
            super.handleMessage(msg);
        }
    };

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
    public void onClick(View view) {
        if (view == binding.tvSend) {
            String message = binding.etCmd.getText().toString();
            if (message.equals("")) {
                Toast.makeText(this,"请输入OBD命令",Toast.LENGTH_LONG).show();
                return;
            }
            message += "\r";
            SendSocketService.sendMessage(message,myhandl);
        }
    }
}

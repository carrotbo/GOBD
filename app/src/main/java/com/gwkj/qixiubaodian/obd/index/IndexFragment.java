package com.gwkj.qixiubaodian.obd.index;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.Utils.BaseCacheUtil;
import com.gwkj.qixiubaodian.obd.adapter.BlueAdapter;
import com.gwkj.qixiubaodian.obd.contants.BltContant;
import com.gwkj.qixiubaodian.obd.contants.Common;
import com.gwkj.qixiubaodian.obd.databinding.IndexFrament1Binding;
import com.gwkj.qixiubaodian.obd.frament.BaseFragment;
import com.gwkj.qixiubaodian.obd.manager.BltManager;
import com.gwkj.qixiubaodian.obd.module.diagnose.BltSocketAcivity;
import com.gwkj.qixiubaodian.obd.module.login.LoginActivity;
import com.gwkj.qixiubaodian.obd.module.mpchart.MpChartActivity;
import com.gwkj.qixiubaodian.obd.view.CommonDialogManager;
import com.gwkj.qixiubaodian.obd.view.CustomDialog;
import com.gwkj.qixiubaodian.obd.view.LoadingDialog;

import static com.gwkj.qixiubaodian.obd.R.id.tv_connect;
import static com.gwkj.qixiubaodian.obd.R.id.tv_continue;

public class IndexFragment extends BaseFragment implements View.OnClickListener {


    private boolean isFirst = false;
    private String bltname;
    private String  name="V-LINK";
    private Dialog loadingDialog;
    private boolean ischange=false;
    private IndexFrament1Binding binding;

    private BlueAdapter myAdapter;
    public IndexFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (!isFirst) {
                isFirst = true;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.index_frament1, container, false);
        initview();
        connectInfo();
        return binding.getRoot();
    }

    private void initview() {

        myAdapter = new BlueAdapter(getContext());
        binding.tvConnect.setOnClickListener(this);
        binding.tvContinue.setOnClickListener(this);
        binding.rlBack.setOnClickListener(this);
        binding.tvGood.setOnClickListener(this);


    }
    private void connectInfo(){
        if(bltname!=null&&!bltname.isEmpty()){
            binding.tvConnectInfo.setText("已连接设备："+bltname);
            binding.tvConnectInfo.setVisibility(View.VISIBLE);
            binding.tvContinue.setVisibility(View.VISIBLE);
            binding.tvConnect.setText("断开连接");
        }else{
            binding.tvConnectInfo.setVisibility(View.GONE);
            binding.tvContinue.setVisibility(View.GONE);
            binding.tvConnect.setText("连接设备");
        }
    }

    private void initData() {

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
                    closeDialog();
                    if (myAdapter != null&&!myAdapter.getBlueList().contains(device)) {
                        if(myAdapter.getCount()==0){
                            showBlueDialog();
                        }
                        myAdapter.addBlueList(device);
                    }

//                    if (device.getName().contains(name)) {
//                        BltManager.getInstance().unregisterReceiver(getContext());
//                        Toast.makeText(getContext(),"找到"+name+",开始连接",Toast.LENGTH_SHORT).show();
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                BltManager.getInstance().createBond(device, handler);
//                            }
//                        }).start();
//                    }
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
                        BltManager.getInstance().createBond(device, handler);

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
                        final BluetoothDevice device=(BluetoothDevice)myAdapter.getItem(i);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                handler.sendEmptyMessageDelayed(6,100);
                                BltManager.getInstance().disConnectBlt();
                                BltManager.getInstance().stopSearthBltDevice();
                                BltManager.getInstance().createBond(device, handler);

                            }
                        }).start();
                    }
                });

            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case tv_connect:
                String openid= BaseCacheUtil.getString(getContext(),"openid");
                    if(openid.isEmpty()) {
                        intent=new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                if(bltname!=null&&!bltname.isEmpty()){
                    try {
                        ischange=true;
                        Toast.makeText(getContext(), "断开了蓝牙", Toast.LENGTH_SHORT).show();
                        bltname = "";
                        connectInfo();
                        BltManager.getInstance().disConnectBlt();
                        if(BltSocketAcivity.bltSocketAcivity!=null){
                            BltSocketAcivity.bltSocketAcivity.finish();
                        }
                    }catch (Exception e) {
                        Log.e("=========",e.getMessage());
                    }
                }else {
                    if(myAdapter!=null){
                        myAdapter.cleanList();
                    }
                    showDialogLoad("正在搜索蓝牙");
                    BltManager.getInstance().disConnectBlt();
                    BltManager.getInstance().stopSearthBltDevice();
                    initData();
                    handler.removeMessages(2);
                    handler.sendEmptyMessageDelayed(2, 30000);
                }
                break;
            case tv_continue:
                intent=new Intent(getContext(),BltSocketAcivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
//                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.rl_back:
                Intent intentb = new Intent("drc.xxx.yyy.baseActivity");
                intentb.putExtra("closeAll", 1);
                getActivity().sendBroadcast(intentb);//发送广播

                getActivity().finish();
                break;
            case R.id.tv_good:
//                intent=new Intent(getContext(),EditReportActivity.class);
//                intent=new Intent(getContext(),BledemoActivity.class);
                intent=new Intent(getContext(),MpChartActivity.class);
                startActivity(intent);
                break;
        }
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
    private void closeDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
    private void startBlueActivity(String name){
        Intent intent = new Intent(getContext(), BltSocketAcivity.class);
        intent.putExtra("name",name);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        binding.tvConnectInfo.setText("已连接设备："+name);
//                    getActivity().finish();
        bltname=name;
        connectInfo();
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(2);
        BltManager.getInstance().unregisterReceiver(getContext());
        BltManager.getInstance().disConnectBlt();
        if(BltSocketAcivity.bltSocketAcivity!=null){
            BltSocketAcivity.bltSocketAcivity.finish();
        }
    }

    @Override
    protected void refreshWebView(String info) {

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://搜索蓝牙
                    break;
                case 2://重搜索
                    initData();
//                    Toast.makeText(getContext(),"没找到设备，重新搜索",Toast.LENGTH_SHORT).show();
                    sendEmptyMessageDelayed(2,30000);
                    break;
                case 3://设备已经接入
                    BltManager.getInstance().unregisterReceiver(getContext());
                    BluetoothDevice device = (BluetoothDevice) msg.obj;
                    closeDialog();
                    startBlueActivity(device.getName());
                    Common.blueDevice=device;
                    break;
                case 4://已连接某个设备
                    BltManager.getInstance().unregisterReceiver(getContext());
                    removeMessages(2);
                    closeDialog();
                    BluetoothDevice device1 = (BluetoothDevice) msg.obj;
                    startBlueActivity(device1.getName());
                    Common.blueDevice=device1;

                    break;
                case 5://连接失败重连
                    removeMessages(2);
                    BluetoothDevice device2 = (BluetoothDevice) msg.obj;
                    BltManager.getInstance().createBond(device2, handler);
                    break;
                case 6:
                    showDialogLoad("正在连接蓝牙");
                    break;
            }
        }
    };

}

package com.gwkj.qixiubaodian.obd.index;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
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
import com.gwkj.qixiubaodian.obd.adapter.BlueAdapter;
import com.gwkj.qixiubaodian.obd.contants.Common;
import com.gwkj.qixiubaodian.obd.databinding.IndexFrament1Binding;
import com.gwkj.qixiubaodian.obd.frament.BaseFragment;
import com.gwkj.qixiubaodian.obd.manager.BltBleManager;
import com.gwkj.qixiubaodian.obd.module.bledemo.ble.bletest.BleTestActivity;
import com.gwkj.qixiubaodian.obd.module.diagnose.BltBleSocketAcivity;
import com.gwkj.qixiubaodian.obd.view.CommonDialogManager;
import com.gwkj.qixiubaodian.obd.view.CustomDialog;
import com.gwkj.qixiubaodian.obd.view.LoadingDialog;

import static com.gwkj.qixiubaodian.obd.R.id.tv_connect;
import static com.gwkj.qixiubaodian.obd.R.id.tv_continue;
import static com.gwkj.qixiubaodian.obd.manager.BltBleManager.mBluetoothAdapter;

public class IndexBleFragment extends BaseFragment implements View.OnClickListener {


    private boolean isFirst = false;
    private String bltname;
    private String  name="V-LINK";
    private Dialog loadingDialog;
    private boolean ischange=false;
    private IndexFrament1Binding binding;

    private BlueAdapter myAdapter;
    private BluetoothDevice mdevice;

    public IndexBleFragment() {
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
        BltBleManager.getInstance().init(getActivity());
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
                        BltBleManager.mBluetoothAdapter.stopLeScan(mLeScanCallback);
                        mBluetoothAdapter.cancelDiscovery();
                        BltBleManager.getInstance().connectBle(mdevice,handler);
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
                if(bltname!=null&&!bltname.isEmpty()){
                    try {
                        ischange=true;
                        Toast.makeText(getContext(), "断开了蓝牙", Toast.LENGTH_SHORT).show();
                        BltBleManager.mBluetoothGatt.disconnect();
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                        if(BltBleSocketAcivity.bltSocketAcivity!=null){
                            BltBleSocketAcivity.bltSocketAcivity.finish();
                        }
                        bltname = "";
                        connectInfo();
                    }catch (Exception e) {
                        Log.e("=========",e.getMessage());
                    }
                }else {
                    if(myAdapter!=null){
                        myAdapter.cleanList();
                    }
                    showDialogLoad("正在搜索蓝牙");
                    initData();
//                    String openid= BaseCacheUtil.getString(getContext(),"openid");
//                    if(!openid.isEmpty()) {
//                        showDialogLoad("正在搜索蓝牙");
//                        initData();
//                    }else{
//                        intent=new Intent(getContext(), LoginActivity.class);
//                        startActivity(intent);
//                    }

                }
                break;
            case tv_continue:
                if(BltBleManager.mBluetoothGatt!=null) {
                    intent = new Intent(getContext(), BltBleSocketAcivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
//                getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else{
                    if(mdevice!=null) {//连接失败重连
                        Log.e("============","重连接。。");
                        BltBleManager.getInstance().connectBle(mdevice, handler);
                    }
                }
                break;
            case R.id.rl_back:

                break;
            case R.id.tv_good:
//                intent=new Intent(getContext(),EditReportActivity.class);
//                intent=new Intent(getContext(),BledemoActivity.class);
                intent=new Intent(getContext(),BleTestActivity.class);
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
        Intent intent = new Intent(getContext(), BltBleSocketAcivity.class);
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
        if(BltBleManager.mBluetoothGatt!=null) {
            BltBleManager.mBluetoothGatt.disconnect();
        }
        if(mBluetoothAdapter!=null) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mBluetoothAdapter.cancelDiscovery();
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
                case 6:
                    showDialogLoad("正在连接蓝牙");
                    break;
                case 9://设备已经接入
                    closeDialog();
                    startBlueActivity(mdevice.getName());
                    Common.blueDevice=mdevice;
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

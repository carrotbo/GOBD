package com.gwkj.qixiubaodian.obd.module.report.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.frament.BaseFragment;


public class ReportFragment1 extends BaseFragment implements View.OnClickListener {
    View view;



    public ReportFragment1() {
    }
    public static ReportFragment1 newInstance(String type, String pinpai_id, String status){
        ReportFragment1 fragmentOne = new ReportFragment1();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("pinpai_id", pinpai_id);
        bundle.putString("status", status);
        //fragment保存参数，传入一个Bundle对象
        fragmentOne.setArguments(bundle);
        return fragmentOne;
    }
    private int article_num = 1;
    private String article_data;
    private boolean isFirst = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (!isFirst) {
                isFirst = true;
                myhandl.sendEmptyMessageDelayed(0,100);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        return view;
    }

    private void initview() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    
    @Override
    protected void refreshWebView(String info) {

    }
    @SuppressLint("HandlerLeak") Handler myhandl=new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:

                    break;

                
            }
            super.handleMessage(msg);
        }
    };
}

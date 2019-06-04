package com.gwkj.qixiubaodian.obd.frament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.appliaction.BltAppliaction;
import com.gwkj.qixiubaodian.obd.item.OBDItem;

public class BlueZDFragment2 extends BaseFragment implements View.OnClickListener {
    View view;
    private OBDItem item;
    private String strData="";
    private boolean isFirst = false;
    private GridView gr_order;
    public BlueZDFragment2() {
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
        view = inflater.inflate(R.layout.bzdfremate, container, false);
        item= BltAppliaction.getBaseOBD();
        initview();

        return view;
    }

    private void initview() {

    }

    public void refreshData(){

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    protected void refreshWebView(String info) {

    }

}

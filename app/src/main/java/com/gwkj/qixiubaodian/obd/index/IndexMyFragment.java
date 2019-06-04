package com.gwkj.qixiubaodian.obd.index;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.databinding.IndexMyFramentBinding;
import com.gwkj.qixiubaodian.obd.frament.BaseFragment;

public class IndexMyFragment extends BaseFragment implements View.OnClickListener {
   private IndexMyFramentBinding binding;
    private boolean isFirst=false;



    public IndexMyFragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.index_my_frament, container, false);

        initview();
        return binding.getRoot();
    }

    private void initview() {


    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

            }
        }
    };

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void refreshWebView(String info) {

    }

}

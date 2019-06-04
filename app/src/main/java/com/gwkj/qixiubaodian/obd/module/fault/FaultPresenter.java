package com.gwkj.qixiubaodian.obd.module.fault;

import com.gwkj.qixiubaodian.obd.contants.Constant;
import com.gwkj.qixiubaodian.obd.contants.NetHelper;
import com.gwkj.qixiubaodian.obd.contants.NetInterfaceEngine;
import com.gwkj.qixiubaodian.obd.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FaultPresenter extends BasePresenterImpl<FaultContract.View> implements FaultContract.Presenter{

    @Override
    public void getFaultData(String code) {
        String url= Constant.BASE_URL+"type=get_obdcode_list_show&code=P0010&pinpai=81&id=10487&data_type=1";
        NetInterfaceEngine.getEngine().OkHttpGet(url, new NetHelper() {
            @Override
            public void onSuccess(String result) {
                if(mView!=null){
                    mView.faultData(result);
                }
            }

            @Override
            public void onFail(Exception e, String err) {

            }
        });
    }
}

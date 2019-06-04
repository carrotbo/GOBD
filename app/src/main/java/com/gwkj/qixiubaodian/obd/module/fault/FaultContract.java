package com.gwkj.qixiubaodian.obd.module.fault;

import com.gwkj.qixiubaodian.obd.mvp.BasePresenter;
import com.gwkj.qixiubaodian.obd.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FaultContract {
    interface View extends BaseView {
        void faultData(String result);
    }

    interface  Presenter extends BasePresenter<View> {
        void getFaultData(String code);
    }
}

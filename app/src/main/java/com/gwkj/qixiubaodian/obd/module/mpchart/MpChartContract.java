package com.gwkj.qixiubaodian.obd.module.mpchart;

import android.content.Context;

import com.github.mikephil.charting.data.LineData;
import com.gwkj.qixiubaodian.obd.mvp.BasePresenter;
import com.gwkj.qixiubaodian.obd.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MpChartContract {
    interface View extends BaseView {
        void ChartData(LineData data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getChartData(Context context, String pid);
    }
}

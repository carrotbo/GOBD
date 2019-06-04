package com.gwkj.qixiubaodian.obd.module.mpchart;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.gwkj.qixiubaodian.obd.contants.Constant;
import com.gwkj.qixiubaodian.obd.contants.NetHelper;
import com.gwkj.qixiubaodian.obd.contants.NetInterfaceEngine;
import com.gwkj.qixiubaodian.obd.mvp.BasePresenterImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MpChartPresenter extends BasePresenterImpl<MpChartContract.View> implements MpChartContract.Presenter {

    @Override
    public void getChartData(Context context, String pid) {

        String url = Constant.MPCHART;
        NetInterfaceEngine.getEngine().commitPostPid(context, url, pid, new NetHelper() {
            @Override
            public void onSuccess(String result) {
                if (result != null && !result.isEmpty()) {
                    setChartData(result);
                }else{
                    if (mView != null) {
                        mView.ChartData(null);
                    }
                }
            }

            @Override
            public void onFail(Exception e, String err) {

            }
        });


    }


    private void setChartData(String result) {
        try {

            result = result.substring(1, result.length() - 1);
            JSONObject json1 = new JSONObject(result);
            String piddata = json1.getString("data");
            String[] list = piddata.split(",");
            List<Entry> entries = new ArrayList<>();

            for (int i = 0; i < list.length-1; i++) {
                entries.add(new Entry(i, Float.parseFloat(list[i])));
            } //一个LineDataSet就是一条线
            LineDataSet lineDataSet = setYLineData(entries);
            LineData data = new LineData(lineDataSet);
            if (mView != null) {
                mView.ChartData(data);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            if (mView != null) {
                mView.ChartData(null);
            }
        }
    }
    private LineDataSet setYLineData(List<Entry> entries){
// 新建数据源1
        LineDataSet lineDataSet1 = new LineDataSet(entries, "速度");
        // 设置数据内容的样式
        lineDataSet1.setColor(Color.BLUE);                     // 设置数据中线的颜色
        lineDataSet1.setDrawValues(true);                     // 设置是否显示数据点的值
        lineDataSet1.setDrawCircleHole(false);                 // 设置数据点是空心还是实心，默认空心
        lineDataSet1.setCircleColor(Color.GREEN);              // 设置数据点的颜色
        lineDataSet1.setCircleSize(3);                         // 设置数据点的大小
        lineDataSet1.setHighLightColor(Color.RED);            // 设置点击时高亮的点的颜色


        return lineDataSet1;
    }
}

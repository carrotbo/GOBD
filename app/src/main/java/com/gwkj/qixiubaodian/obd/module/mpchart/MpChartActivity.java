package com.gwkj.qixiubaodian.obd.module.mpchart;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.databinding.ActivityMpchartBinding;
import com.gwkj.qixiubaodian.obd.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MpChartActivity extends MVPBaseActivity<MpChartContract.View, MpChartPresenter> implements MpChartContract.View , View.OnClickListener{

    private ActivityMpchartBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_mpchart);
        initView();
        mPresenter.getChartData(this,"0D");

    }
    private void initView(){
        binding.tvTitle.setText("速度");

        binding.lineChart.setDrawBorders(true);
        setChartProperties();

        binding.btnBack.setOnClickListener(this);
    }

    @Override
    public void ChartData(final LineData data) {
        if(data==null){
            binding.lineChart.setNoDataText("您暂无数据,请添加数据...");
            binding.lineChart.setNoDataTextColor(Color.BLACK);
            binding.lineChart.invalidate();

        }else {
            binding.lineChart.setData(data);
            binding.lineChart.invalidate();
        }
    }
    private void setChartProperties() {
        //设置描述文本不显示
       LineChart mLineChart=binding.lineChart;

         mLineChart.getDescription().setEnabled(false);
        //设置是否显示表格背景
        mLineChart.setDrawGridBackground(true);
        //设置是否可以触摸
         mLineChart.setTouchEnabled(true);
        mLineChart.setDragDecelerationFrictionCoef(0.9f);
        //设置是否可以拖拽
        mLineChart.setDragEnabled(true);
        //设置是否可以缩放
        mLineChart.setScaleEnabled(false);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setHighlightPerDragEnabled(true);
        mLineChart.setPinchZoom(true);

        //设置背景颜色
//        mLineChart.setBackgroundColor(ColorAndImgUtils.CHART_BACKGROUND_COLOR);
        //设置一页最大显示个数为6，超出部分就滑动
        float ratio = (float) 80/(float) 10;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        mLineChart.zoom(10,1f,0,0); //设置从X轴出来的动画时间
        // mLineChart.animateX(1500);
        // 设置XY轴动画
        mLineChart.animateXY(1500,1500, Easing.EasingOption.EaseInSine, Easing.EasingOption.EaseInSine);

        XAxis xAxis = binding.lineChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(10);
        xAxis.setGridColor(getResources().getColor(R.color.cut_off_rule_line));
        //  xAxis.setDrawGridLines(false);//网络

        YAxis yAxisr=binding.lineChart.getAxisRight();
        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setGridLineWidth(1);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisLineWidth(1f);
        leftAxis.setLabelCount(10);
        leftAxis.setGridColor(getResources().getColor(R.color.cut_off_rule_line));
        //  leftAxis.setDrawGridLines(false);//网络
        yAxisr.setEnabled(false);
        setLegend();
    }
    private void setLegend() {
        Legend legend = binding.lineChart.getLegend();
        // legend.setEnabled(false);                                // 不需要Legend
        legend.setTextColor(Color.BLACK);                           // 设置字的颜色
//        legend.setForm(Legend.LegendForm.CIRCLE);                   // 设置图形为圆形
        legend.setFormSize(10);
        legend.setTextSize(10);

    }

    @Override
    public void onClick(View view) {
        if(view==binding.btnBack){
            finish();
        }
    }
}

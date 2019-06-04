package com.gwkj.qixiubaodian.obd.index;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.contants.CommonUtils;
import com.gwkj.qixiubaodian.obd.frament.BaseFragment;
import com.gwkj.qixiubaodian.obd.module.report.fragment.ReportFragment1;

import shanyao.tabpagerindictor.TabPageIndicator;

public class IndexReportFragment extends BaseFragment implements View.OnClickListener {
    View view;
    private String[] titles = new String[]{"最后编辑", "全部品牌", "全部时间"};
    private TabPageIndicator indicator;
    private ViewPager viewPager;
    private boolean isFirst = false;
    private ReportFragment1 fragment1;
    private ReportFragment1 fragment2;
    private ReportFragment1 fragment3;


    public IndexReportFragment() {
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
        view = inflater.inflate(R.layout.index_report_frament, container, false);

        initview();
        return view;
    }

    private void initview() {
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        BasePagerAdapter adapter = new BasePagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(titles.length);
        indicator.setViewPager(viewPager);
        setTabPagerIndicator();

    }
    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_NOSAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(Color.parseColor("#666666"));// 设置分割线的颜色
        indicator.setDividerPadding(CommonUtils.dip2px(getContext(), 10));
        indicator.setIndicatorColor(Color.parseColor("#0192f8"));// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.parseColor("#0192f8"));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#666666"));// 设置tab标题未被选中的颜色
        indicator.setTextSize(CommonUtils.sp2px(getContext(), 15));// 设置字体大小
        indicator.setBackgroundResource(R.color.white);

    }
    //Fragment适配器
    class BasePagerAdapter extends FragmentStatePagerAdapter {
        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (fragment1 == null) {
                        fragment1 = ReportFragment1.newInstance("3", "", "1");
                    }
                    return fragment1;
                case 1:
                    if (fragment2 == null) {
                        fragment2 = ReportFragment1.newInstance("3", "", "0");
                    }
                    return fragment2;
                case 2:
                    if (fragment3 == null) {
                        fragment3 = ReportFragment1.newInstance("3", "", "8");
                    }
                    return fragment3;

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
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

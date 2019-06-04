package com.gwkj.qixiubaodian.obd.index;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.databinding.IndexDiscoverFramentBinding;
import com.gwkj.qixiubaodian.obd.frament.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class IndexDiscoverFragment extends BaseFragment implements View.OnClickListener,OnBannerListener {
    private IndexDiscoverFramentBinding binding;

    private boolean isFirst = false;
    private String urls1 = "https://gss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=e9873bfca944ad342eea8f81e09220cc/a8ec8a13632762d08fa73daea8ec08fa513dc602.jpg";
    private String urls2 = "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg";
    private String urls3 = "http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg";
    List<String> images = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    private Banner banner;
    private BannerViewPager viewPager;
    private int num=0;


    public IndexDiscoverFragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.index_discover_frament, container, false);
        initview();
        binding.tvNum.setOnClickListener(this);

        return binding.getRoot();
    }

    private void initview() {
        images.add(urls1);
        images.add(urls2);
        images.add(urls3);
        titles.add("111");
        titles.add("222");
        titles.add("333");


        banner = binding.banner;
        viewPager=(BannerViewPager)banner.findViewById(R.id.bannerViewPager);
        handler.sendEmptyMessage(0);
        banner.setOnBannerListener(this);
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                num=position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void OnBannerClick(int position) {

    }

    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext()).load((String)path).into(imageView);
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
                case 0:

                    banner.setImageLoader(new MyImageLoader());
                    //设置轮播样式（没有标题默认为右边,有标题时默认左边）
                    //可选样式:
                    //Banner.LEFT   指示器居左
                    //Banner.CENTER 指示器居中
                    //Banner.RIGHT  指示器居右
//                    banner.setIndicatorGravity(BannerConfig.CENTER);
                    //设置是否自动轮播（不设置则默认自动）
                    banner.isAutoPlay(false);
                    //设置轮播图片间隔时间（不设置默认为2000）
                   // banner.setDelayTime(3500);
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//                    banner.setBannerTitles(titles);
                    banner.setImages(images).start();

                    break;

            }
        }
    };

    @Override
    public void onClick(View view) {
        int cuNum=num+1;
        if(view==binding.tvNum){
            viewPager.setCurrentItem(2);

        }

    }

    @Override
    protected void refreshWebView(String info) {

    }

}

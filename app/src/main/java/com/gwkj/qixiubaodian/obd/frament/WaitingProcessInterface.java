package com.gwkj.qixiubaodian.obd.frament;

/**
 * 等待加载接口
 * Created by John on 2016/7/13.
 */
public interface WaitingProcessInterface {
    /**
     * 显示等待加载
     */
    void showProgressDialog();

    /**
     * 显示等待加载
     *
     * @param magess 提示信息
     */
    void showProgressDialog(CharSequence magess);

    /**
     * 隐藏等待加载
     */
    void dismissProgressDialog();
}

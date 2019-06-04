package com.gwkj.qixiubaodian.obd.contants;

/**
 * 网络操作类
 *
 * @author admin
 */
public abstract class NetHelper {
    public abstract void onSuccess(String result);

    public abstract void onFail(Exception e, String err);

    public void onLoadding(long total, long current, boolean isUploading) {

    }
}

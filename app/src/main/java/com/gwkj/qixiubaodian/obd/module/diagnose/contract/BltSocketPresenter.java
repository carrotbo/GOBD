package com.gwkj.qixiubaodian.obd.module.diagnose.contract;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gwkj.qixiubaodian.obd.contants.Constant;
import com.gwkj.qixiubaodian.obd.contants.NetHelper;
import com.gwkj.qixiubaodian.obd.contants.NetInterfaceEngine;
import com.gwkj.qixiubaodian.obd.helper.OBDVarDataHelper;
import com.gwkj.qixiubaodian.obd.item.OBDSer;
import com.gwkj.qixiubaodian.obd.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BltSocketPresenter extends BasePresenterImpl<BltSocketContract.View> implements BltSocketContract.Presenter {

    @Override
    public void initBaseData() {
        String url = Constant.BASE_URL+"type=get_menu";
        NetInterfaceEngine.getEngine().OkHttpGet(url, new NetHelper() {
            @Override
            public void onSuccess(String result) {
                if(mView!=null){
                    mView.BaseDataSuccess(result);
                }
            }
            @Override
            public void onFail(Exception e, String err) {

            }
        });
    }

    @Override
    public void sendData(Context context,String data, String cmd, String type, String sendParam) {
        String url = Constant.BASE_URL+"type=set_init";

        NetInterfaceEngine.getEngine().commitPostData(context, url, data, cmd, type, sendParam, new NetHelper() {
            @Override
            public void onSuccess(String result) {
                if(mView!=null){
                    mView.sendDataSuccess(result);
                }
            }

            @Override
            public void onFail(Exception e, String err) {

            }
        });
    }

    @Override
    public void sendTableData(Context context, List<OBDSer> list) {
        String url = "http://www.haohaoxiuche.com/wxqxbd/baodian/api/api_uds.php?type=set_pid";
        NetInterfaceEngine.getEngine().commitPostList(context, url, list, new NetHelper() {
            @Override
            public void onSuccess(String result) {
                if(mView!=null){
                    mView.sendTableSuccess(result);
                }
            }

            @Override
            public void onFail(Exception e, String err) {
            }
        });
    }

    @Override
    public void saveVarName(Context context, String name, String value) {
        try {
            SQLiteDatabase db = OBDVarDataHelper.getInstance(context).getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from varname where name = '"
                    + name + "'", null);
            if (cursor.getCount() > 0) { //
                db.delete("varname", "name = ?", new String[]{name});
            }
            if (value != null && !value.isEmpty()) {
                db.execSQL("insert into varname(name,value) values('" + name + "', '"
                        + value + "')");
            }
            cursor.close();
            db.close();
        }catch (Exception e ){
            Log.e("======","数据异常");
        }
    }
}

package com.gwkj.qixiubaodian.obd.module.diagnose.contract;

import android.content.Context;

import com.gwkj.qixiubaodian.obd.item.OBDSer;
import com.gwkj.qixiubaodian.obd.mvp.BasePresenter;
import com.gwkj.qixiubaodian.obd.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BltSocketContract {
    public interface View extends BaseView {
      void BaseDataSuccess(String result);
        void sendDataSuccess(String result);
        void sendTableSuccess(String result);
    }

    interface  Presenter extends BasePresenter<View> {

        void initBaseData();
        void sendData(Context context, String data, String cmd, String type, String sendParam);
        void sendTableData(Context context, List<OBDSer> list);
        void saveVarName(Context context,String name, String value);
    }
}

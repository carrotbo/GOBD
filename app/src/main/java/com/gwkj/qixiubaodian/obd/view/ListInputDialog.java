package com.gwkj.qixiubaodian.obd.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwkj.qixiubaodian.obd.BR;
import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.adapter.InputListAdapter;
import com.gwkj.qixiubaodian.obd.item.InputparamBean;

import java.util.List;

/**
 * 认证Dialog
 */
public class ListInputDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String titleStr;
    private String cmd;
    private List<InputparamBean> list;
    private ListView lv_input;
    private InputListAdapter adapter;
    private boolean isOtherDimss = false;

    public interface ApproveDialogHelper {
        void go(String inputstr);
        void cancel();
    }

    private ApproveDialogHelper mConfirmDialogHelper;

    /**
     * 新建时 , 用这个构造函数
     */
    public ListInputDialog(Context context, String titleStr, String cmd,List<InputparamBean> list, ApproveDialogHelper helper) {
        super(context, R.style.CustomDialog);
        this.titleStr = titleStr;
        this.list=list;
        this.context=context;
        this.cmd=cmd;
        this.mConfirmDialogHelper = helper;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_input_dialog);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();// 获得窗口
        WindowManager manager = window.getWindowManager();// 获得管理器
        Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (display.getWidth() * 0.9);
        window.setAttributes(attributes);
        init();
        if (isOtherDimss) {
            setCancelable(false);
        }
    }

    private void init() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);

        TextView tv_go = (TextView) findViewById(R.id.tv_go);
        TextView tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        lv_input=(ListView)findViewById(R.id.lv_input);
        adapter=new InputListAdapter<>(context, R.layout.input_list_item, BR.input);
        lv_input.setAdapter(adapter);
        adapter.setDataList(list);
        tv_title.setText(titleStr);


        tv_go.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_go:
                // 确定
                String inputstr="";
                for (int i = 0; i < lv_input.getChildCount(); i++) {
                    InputparamBean item=(InputparamBean)adapter.getItem(i);
                    LinearLayout layout = (LinearLayout)lv_input.getChildAt(i);// 获得子item的layout
                    EditText et = (EditText) layout.findViewById(R.id.et_content);// 从layout中获得控件,根据其id
                    TextView tv = (TextView) layout.findViewById(R.id.tv_text);// 从layout中获得控件,根据其id
                    String etstr=et.getText().toString();
                    if(etstr.isEmpty()){
                        Toast.makeText(context,tv.getText().toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        cmd=cmd.replaceAll(item.getKey(),etstr);
                    }
                }
                if (mConfirmDialogHelper != null) {
                    mConfirmDialogHelper.go(cmd);
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                // 取消
                if (mConfirmDialogHelper != null) {
                    mConfirmDialogHelper.cancel();
                }
                dismiss();
                break;

            default:
                break;
        }
    }
}

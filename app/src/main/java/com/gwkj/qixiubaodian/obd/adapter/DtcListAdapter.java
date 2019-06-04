package com.gwkj.qixiubaodian.obd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.item.OBDSer;
import com.gwkj.qixiubaodian.obd.module.fault.FaultActivity;

import java.util.ArrayList;
import java.util.List;

import static com.gwkj.qixiubaodian.obd.R.id.tv_code;

/**
 * Created by carrot on 2018/8/6.
 */

public class DtcListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int selectnum=0;
    private boolean isleft=false;
    private List<OBDSer.DtcBean> list=new ArrayList<>();


    public DtcListAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    public void setData(List<OBDSer.DtcBean> mselectlist){
        list.clear();;
        list.addAll(mselectlist);
        notifyDataSetChanged();
    }
    public void setData(List<OBDSer.DtcBean> mselectlist,boolean left){
        isleft=left;
        list.clear();;
        list.addAll(mselectlist);
        notifyDataSetChanged();
    }
    @Override
    public int getCount(){
        return this.list.size();
    }

    @Override
    public OBDSer.DtcBean getItem(int position){
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public boolean hasStableIds(){
        return true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder orderHolder;
        final OBDSer.DtcBean item=list.get(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.table_dtc__list, null);
            orderHolder=new ViewHolder();
            orderHolder.tv_code=(TextView) convertView.findViewById(tv_code);
            orderHolder.tv_value=(TextView) convertView.findViewById(R.id.tv_value);
            orderHolder.ll_fault=(LinearLayout) convertView.findViewById(R.id.ll_fault);


            convertView.setTag(orderHolder);
        }else{
            orderHolder=(ViewHolder) convertView.getTag();
        }
        orderHolder.tv_code.setText(item.getCode());
        orderHolder.tv_value.setText(item.getDesc());
        orderHolder.ll_fault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FaultActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("code",item.getCode());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {


        TextView tv_code;
        TextView tv_value;
        LinearLayout ll_fault;

    }


}

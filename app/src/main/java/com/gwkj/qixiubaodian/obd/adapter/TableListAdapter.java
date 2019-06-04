package com.gwkj.qixiubaodian.obd.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.item.OBDPid;
import com.gwkj.qixiubaodian.obd.module.mpchart.MpChartActivity;

import java.util.ArrayList;
import java.util.List;

import static com.gwkj.qixiubaodian.obd.R.id.tv_unit;

/**
 * Created by carrot on 2018/8/6.
 */

public class TableListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int selectnum=0;
    private boolean isleft=false;
    private List<OBDPid.DataBean> list=new ArrayList<>();


    public TableListAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    public void setData(List<OBDPid.DataBean> mselectlist){
        list.clear();;
        list.addAll(mselectlist);
        notifyDataSetChanged();
    }
    public void setData(List<OBDPid.DataBean> mselectlist,boolean left){
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
    public OBDPid.DataBean getItem(int position){
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
        OBDPid.DataBean item=list.get(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.obd_table_data_list, null);
            orderHolder=new ViewHolder();
            orderHolder.tv_code=(TextView) convertView.findViewById(R.id.tv_code);
            orderHolder.tv_unit=(TextView) convertView.findViewById(tv_unit);
            orderHolder.tv_value=(TextView) convertView.findViewById(R.id.tv_value);


            convertView.setTag(orderHolder);
        }else{
            orderHolder=(ViewHolder) convertView.getTag();
        }
        orderHolder.tv_code.setText(Html.fromHtml("<u>"+item.getTitle()+"</u>"));
        orderHolder.tv_unit.setText(item.getUnit());
        orderHolder.tv_value.setText(item.getData());

        orderHolder.tv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MpChartActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {


        TextView tv_code;
        TextView tv_value;
        TextView tv_unit;

    }


}

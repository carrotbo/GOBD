package com.gwkj.qixiubaodian.obd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.item.OBDOrder;
import com.gwkj.qixiubaodian.obd.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carrot on 2018/8/6.
 */

public class GvOrderChildAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<OBDOrder.DataBean.SubmenuBeanX.SubmenuBean> list=new ArrayList<>();


    public GvOrderChildAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    public void setData(List<OBDOrder.DataBean.SubmenuBeanX.SubmenuBean> mselectlist){
        list.clear();;
        if(mselectlist!=null) {
            list.addAll(mselectlist);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return this.list.size();
    }

    @Override
    public OBDOrder.DataBean.SubmenuBeanX.SubmenuBean getItem(int position){
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
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder orderHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.gv_order_item, null);
            orderHolder=new ViewHolder();
            orderHolder.tv_order=(TextView) convertView.findViewById(R.id.tv_order);

            convertView.setTag(orderHolder);
        }else{
            orderHolder=(ViewHolder) convertView.getTag();
        }
        OBDOrder.DataBean.SubmenuBeanX.SubmenuBean item=list.get(position);
        orderHolder.tv_order.setText(item.getTitle());

        return convertView;
    }

    class ViewHolder {

        TextView tv_order;
        MyListView lv_pid;
    }


}

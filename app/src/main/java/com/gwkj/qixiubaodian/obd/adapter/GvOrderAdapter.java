package com.gwkj.qixiubaodian.obd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.item.OBDOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carrot on 2018/8/6.
 */

public class GvOrderAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int selectnum=0;
    private boolean isleft=false;
    private List<OBDOrder.DataBean.SubmenuBeanX> list=new ArrayList<>();


    public GvOrderAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    public void setData(List<OBDOrder.DataBean.SubmenuBeanX> mselectlist){
        list.clear();;
        list.addAll(mselectlist);
        notifyDataSetChanged();
    }
    public void setData(List<OBDOrder.DataBean.SubmenuBeanX> mselectlist,boolean left){
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
    public OBDOrder.DataBean.SubmenuBeanX getItem(int position){
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
        if(convertView==null){
            convertView=inflater.inflate(R.layout.gv_order_item, null);
            orderHolder=new ViewHolder();
            orderHolder.tv_order=(TextView) convertView.findViewById(R.id.tv_order);
            orderHolder.img_right=(ImageView) convertView.findViewById(R.id.img_right);
            orderHolder.img_left=(ImageView) convertView.findViewById(R.id.img_left);

            convertView.setTag(orderHolder);
        }else{
            orderHolder=(ViewHolder) convertView.getTag();
        }

        OBDOrder.DataBean.SubmenuBeanX item=list.get(position);
        orderHolder.tv_order.setText(item.getTitle());
        if(item.getSubmenu()!=null&&item.getSubmenu().size()>0){
            if(isleft){
                orderHolder.img_left.setVisibility(View.VISIBLE);
                orderHolder.img_right.setVisibility(View.GONE);
            }else{
                orderHolder.img_right.setVisibility(View.VISIBLE);
                orderHolder.img_left.setVisibility(View.GONE);
            }
        }else{
            orderHolder.img_right.setVisibility(View.GONE);
            orderHolder.img_left.setVisibility(View.GONE);
        }

        if (position == selectItem) {
            convertView.setBackgroundResource(R.color.blue);
            orderHolder.tv_order.setTextColor(Color.parseColor("#ffffff"));
        }else {
            convertView.setBackgroundResource(R.color.white);
            orderHolder.tv_order.setTextColor(Color.parseColor("#666666"));
        }
        return convertView;
    }

    class ViewHolder {

        LinearLayout ll_all;
        TextView tv_order;
        ImageView img_right,img_left;
    }
    public  void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
        notifyDataSetInvalidated();
    }
    private int  selectItem=-1;

}

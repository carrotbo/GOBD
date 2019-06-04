package com.gwkj.qixiubaodian.obd.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.item.OBDPid;
import com.gwkj.qixiubaodian.obd.item.OBDSer;
import com.gwkj.qixiubaodian.obd.item.PIDData;
import com.gwkj.qixiubaodian.obd.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 找资料适配器
 * Created by admin on 2018/5/24.
 */

public class BaseOrderAdapter<L> extends BaseAdapter {
    private Context context;
    public List<OBDSer> bean = new ArrayList<>();
    private LayoutInflater inflater;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;//申请远程协助
    final int TYPE_4 = 3; //发送数据
    final int TYPE_5 = 4;
    final int TYPE_6 = 5;

    public BaseOrderAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<OBDSer> mlist) {
        if (bean != null && !bean.isEmpty()) {
            bean.clear();
        }

        bean.addAll(mlist);
        this.notifyDataSetChanged();
    }
    public void changeItem(OBDSer item,int num){
        bean.get(num-1).setTable(item.getTable());
        notifyDataSetChanged();
    }
    public void addItem(OBDSer item){

        bean.add(item);
        notifyDataSetChanged();
    }
    public void setBaseList(List<PIDData.DataBean> baseList,int num){
        if(num>=0&&num<bean.size()){
            List<OBDPid.DataBean> table=bean.get(num).getTable();
            for (PIDData.DataBean item:baseList){
                for (int i=0;i<table.size();i++){
                    if(item.getPid().equals((table.get(i).getSid()+table.get(i).getPid()))){
                        table.get(i).setData(item.getData());
                    }
                }

            }
        }

        notifyDataSetChanged();
    }
    public void addList(List<OBDSer> mlist) {
        bean.clear();
        bean.addAll(mlist);
        notifyDataSetChanged();
    }

    public void addMoreList(List<OBDSer> mlist) {
        bean.addAll(mlist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        OBDSer item=bean.get(position);
        if(item.getType()!=null&&item.getType().equals("1")){
            return TYPE_1;
        } else if(item.getType()!=null&&item.getType().equals("DTC")){
            return TYPE_2;
        }else if(item.getType()!=null&&item.getType().equals("2")){
            return TYPE_3;//申请远程协助
        }else if(item.getType()!=null&&item.getType().equals("3")){
            return TYPE_4;//发送对话
        }else if(item.getType()!=null&&item.getType().equals("5")){
            return TYPE_6;//收到协助确认
        }else {
            return TYPE_5;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 20;
    }
    public int getCount() {
        return this.bean.size();
    }

    @Override
    public OBDSer getItem(int position) {
        return this.bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final OBDSer item = getItem(position);
        ViewHolder table=null;
        ViewHolder2 holder2=null;
        ViewHolder3 holder3=null;
        ViewHolder4 holder4=null;
        ViewHolder5 holder5=null;
        ViewHolder6 holder6=null;
        int kind = getItemViewType(position);
        if (convertView == null) {
            switch (kind){
                case TYPE_1:
                    convertView = inflater.inflate(R.layout.table_list_item, null);
                    table = new ViewHolder();
                    table.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
                    table.lv_pid=(MyListView) convertView.findViewById(R.id.lv_pid);
                    convertView.setTag(table);
                    break;
                case TYPE_2:
                    convertView = inflater.inflate(R.layout.table_list_item2, null);
                    holder2 = new ViewHolder2();
                    holder2.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
                    holder2.tv_title=(TextView)convertView.findViewById(R.id.tv_title);
                    holder2.lv_pid=(MyListView) convertView.findViewById(R.id.lv_pid);
                    holder2.tv_data=(TextView)convertView.findViewById(R.id.tv_data);
                    convertView.setTag(holder2);
                    break;
                case TYPE_3://申请远程协助
                    convertView = inflater.inflate(R.layout.table_list_item3, null);
                    holder3 = new ViewHolder3();
                    holder3.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
                    holder3.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
                    convertView.setTag(holder3);
                    break;
                case TYPE_4://发送对话
                    convertView = inflater.inflate(R.layout.table_list_item4, null);
                    holder4 = new ViewHolder4();
                    holder4.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
                    holder4.tv_content=(TextView)convertView.findViewById(R.id.tv_content);
                    convertView.setTag(holder4);
                    break;
                case TYPE_5:
                    convertView = inflater.inflate(R.layout.table_list_item5, null);
                    holder5 = new ViewHolder5();
                    holder5.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
                    holder5.tv_title=(TextView)convertView.findViewById(R.id.tv_title);
                    holder5.tv_data=(TextView)convertView.findViewById(R.id.tv_data);
                    convertView.setTag(holder5);
                    break;
                case TYPE_6:
                    convertView = inflater.inflate(R.layout.table_list_item6, null);
                    holder6 = new ViewHolder6();
                    holder6.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
                    holder6.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
                    holder6.tv_text=(TextView)convertView.findViewById(R.id.tv_text);

                    convertView.setTag(holder6);
                    break;
            }

        } else {
            switch (kind) {
                case TYPE_1:
                    table = (ViewHolder) convertView.getTag();
                    break;
                case TYPE_2:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
                case TYPE_3:
                    holder3 = (ViewHolder3) convertView.getTag();
                    break;
                case TYPE_4:
                    holder4 = (ViewHolder4) convertView.getTag();
                    break;
                case TYPE_5:
                    holder5 = (ViewHolder5) convertView.getTag();
                    break;
                case TYPE_6:
                    holder6 = (ViewHolder6) convertView.getTag();
                    break;
            }

        }
        switch (kind){
            case TYPE_1:
                table.tv_time.setText(item.getTime());
                if(item.getTable()!=null&&item.getTable().size()>0){
                    TableListAdapter pidAdapter = new TableListAdapter(context);
                    table.lv_pid.setAdapter(pidAdapter);
                    pidAdapter.setData(item.getTable());
                }
                break;
            case TYPE_2:
                holder2.tv_time.setText(item.getTime());
                holder2.tv_title.setText(item.getTitle());

                if(item.getDtc()!=null&&item.getDtc().size()>0){
                    holder2.tv_data.setVisibility(View.GONE);
                    DtcListAdapter adapter=new DtcListAdapter(context);
                    holder2.lv_pid.setAdapter(adapter);
                    adapter.setData(item.getDtc());
                }else{
                    holder2.tv_data.setVisibility(View.VISIBLE);
                    holder2.tv_data.setText("接收：NO DATA");
                }
                break;
            case TYPE_3:
                holder3.tv_time.setText(item.getTime());
                String title="您邀请了<font color='#0192f8'>"+item.getVarname()+"</font>进行远程协助";
                holder3.tv_name.setText(Html.fromHtml(title));

                break;
            case TYPE_4:
                holder4.tv_name.setText(item.getVarname());
                holder4.tv_content.setText(item.getTitle());

                break;
            case TYPE_5:
                holder5.tv_time.setText(item.getTime());
                String sendData="";
                if(item.getTitle()!=null&&!item.getTitle().isEmpty()){
                    sendData="发送："+item.getTitle()+"  <small>("+item.getCmd()+")</small>";
                }else{
                    sendData="发送："+item.getCmd()+"  <small>("+item.getCmd()+")</small>";
                }
                holder5.tv_title.setText(Html.fromHtml(sendData));
                holder5.tv_data.setText("接收："+item.getData());
                break;
            case TYPE_6:
                holder6.tv_time.setText(item.getTime());
                String accept="<font color='#0192f8'>"+item.getVarname()+"</font>已经同意对您进行远程协助";
                holder6.tv_name.setText(Html.fromHtml(accept));
                holder6.tv_text.setText(item.getVarname());
                break;
        }
        return convertView;
    }

    class ViewHolder {

        TextView tv_time;

        MyListView lv_pid;


    }
    class  ViewHolder2{

        TextView tv_time;
        TextView tv_title;
        MyListView lv_pid;
        TextView tv_data;

    }
    class  ViewHolder3{
        TextView tv_time;
        TextView tv_name;
        TextView tv_data;
    }
    class  ViewHolder4{
        TextView tv_time;
        TextView tv_name;
        TextView tv_content;
    }
    class  ViewHolder5{
        TextView tv_time;
        TextView tv_title;
        TextView tv_data;

    }
    class  ViewHolder6{
        TextView tv_time;
        TextView tv_name;
        TextView tv_text;


    }
}

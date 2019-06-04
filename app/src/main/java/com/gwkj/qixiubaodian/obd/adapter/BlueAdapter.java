package com.gwkj.qixiubaodian.obd.adapter;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gwkj.qixiubaodian.obd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carrot on 2018/12/25.
 */

public class BlueAdapter  extends BaseAdapter {
    private List<BluetoothDevice> bltList = new ArrayList<>();
    private Context context;
    public BlueAdapter(Context context){
        this.context=context;

    }
    public void addBlueList(BluetoothDevice device){
        bltList.add(device);
        notifyDataSetChanged();
    }
    public void cleanList(){
        bltList.clear();
        notifyDataSetChanged();
    }
    public List<BluetoothDevice> getBlueList(){
        return bltList;
    }
    @Override
    public int getCount() {
        return bltList.size();
    }

    @Override
    public Object getItem(int position) {
        return bltList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        ViewHolder vh;
        BluetoothDevice device = bltList.get(position);// 从集合中获取当前行的数据
        if (convertView == null) {
            // 说明当前这一行不是重用的
            // 加载行布局文件，产生具体的一行
            v = LayoutInflater.from(context).inflate(R.layout.item_blt, null);
            // 创建存储一行控件的对象
            vh = new ViewHolder();
            // 将该行的控件全部存储到vh中
            vh.blt_name = (TextView) v.findViewById(R.id.blt_name);
            vh.blt_address = (TextView) v.findViewById(R.id.blt_address);
            vh.blt_type = (TextView) v.findViewById(R.id.blt_type);
            vh.blt_bond_state = (TextView) v.findViewById(R.id.blt_bond_state);
            v.setTag(vh);// 将vh存储到行的Tag中
        } else {
            v = convertView;
            // 取出隐藏在行中的Tag--取出隐藏在这一行中的vh控件缓存对象
            vh = (ViewHolder) convertView.getTag();
        }

        // 从ViewHolder缓存的控件中改变控件的值
        // 这里主要是避免多次强制转化目标对象而造成的资源浪费
        vh.blt_name.setText("蓝牙名称：" + device.getName());
        vh.blt_address.setText("蓝牙地址：" + device.getAddress());
//        vh.blt_type.setText("蓝牙类型:" + device.getType());
//        vh.blt_bond_state.setText("蓝牙状态:" + BltManager.getInstance().bltStatus(device.getBondState()));
        return v;
    }

    private class ViewHolder {
        TextView blt_name, blt_address, blt_type, blt_bond_state;
    }
}


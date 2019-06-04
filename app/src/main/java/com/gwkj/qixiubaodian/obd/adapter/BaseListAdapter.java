package com.gwkj.qixiubaodian.obd.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gwkj.qixiubaodian.obd.databinding.ObdPidListBinding;
import com.gwkj.qixiubaodian.obd.item.OBDPid;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的adapter
 * Created by LQY on 2016/10/10.
 */
public class BaseListAdapter<T> extends BaseAdapter {
    private Context context;
    private List<OBDPid.DataBean> list=new ArrayList<>();
    private int layoutId;//单布局
    private int variableId;
    
    public BaseListAdapter(Context context, int layoutId, int variableId){
        this.context=context;
        this.layoutId=layoutId;
        this.variableId=variableId;
    }
    public void setDataList(List<OBDPid.DataBean> mlist) {
        if (list != null && !list.isEmpty()) {
            list.clear();
        }
        for (int i=0;i<mlist.size();i++){
            mlist.get(i).setIscheck(false);
        }
        list.addAll(mlist);
        this.notifyDataSetChanged();
    }
    public void cleanList(){
        if (list != null && !list.isEmpty()) {
            list.clear();
            notifyDataSetChanged();
        }
    }
    public void addDataList(List<OBDPid.DataBean> mlist) {
        if (list != null) {
            list.addAll(mlist);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount(){
        if(list==null){
            return 0;
        }else{
            return list.size();
        }
    }
    
    @Override
    public Object getItem(int position){
        return list.get(position);
    }
    
    @Override
    public long getItemId(int position){
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ObdPidListBinding binding=null;
        final OBDPid.DataBean item=list.get(position);
        if(convertView==null){
            binding=DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false);
        }else{
            binding=DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(variableId, list.get(position));
        final ObdPidListBinding finalBinding = binding;
        binding.ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalBinding.ckb.isChecked()){
                    item.setIscheck(false);
                }else{
                    item.setIscheck(true);
                }
            }
        });
        return binding.getRoot();
    }
}
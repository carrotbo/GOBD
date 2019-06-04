package com.gwkj.qixiubaodian.obd.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gwkj.qixiubaodian.obd.databinding.ObdPidDataListBinding;
import com.gwkj.qixiubaodian.obd.item.OBDPid;
import com.gwkj.qixiubaodian.obd.item.OBDSer;
import com.gwkj.qixiubaodian.obd.item.PIDData;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的adapter
 * Created by LQY on 2016/10/10.
 */
public class PidDataListAdapter<T> extends BaseAdapter {
    private Context context;
    private List<OBDSer> list=new ArrayList<>();
    private int layoutId;//单布局
    private int variableId;

    public PidDataListAdapter(Context context, int layoutId, int variableId){
        this.context=context;
        this.layoutId=layoutId;
        this.variableId=variableId;
    }
    public void setDataList(List<OBDSer> mlist) {
        if (list != null && !list.isEmpty()) {
            list.clear();
        }

        list.addAll(mlist);
        this.notifyDataSetChanged();
    }
    public void setPidList(List<OBDPid.DataBean> mlist) {
        if (list != null && !list.isEmpty()) {
            list.clear();
        }
        for(OBDPid.DataBean pid:mlist){
            OBDSer item=new OBDSer();
            item.setValue("");
            item.setTitle(pid.getTitle());
            item.setUnit(pid.getUnit());
            item.setCmd(pid.getSid()+pid.getPid());
            list.add(item);
        }
        this.notifyDataSetChanged();
    }
    public void cleanList(){
        if (list != null && !list.isEmpty()) {
            list.clear();
            notifyDataSetChanged();
        }
    }
    public void addDataList(List<OBDSer> mlist) {
        if (list != null) {
            list.addAll(mlist);
            notifyDataSetChanged();
        }
    }
    public void setDataOBD(OBDSer item){
        if(list.size()==0){
            list.add(item);
        }else{
            boolean isChange=false;
            for (int i=0;i<list.size();i++){
                if(list.get(i).getTitle().equals(item.getTitle())){
                    list.get(i).setValue(item.getValue());
                    isChange=true;
                    break;
                }
            }
            if(!isChange){
                list.add(item);
            }
        }
        notifyDataSetChanged();
    }
    public void setBaseList(List<PIDData.DataBean> baseList){
        for (PIDData.DataBean item:baseList){
            for (int i=0;i<list.size();i++){
                if(item.getPid().equals(list.get(i).getCmd())){
                    list.get(i).setData(item.getData());
                }
            }

        }
        notifyDataSetChanged();
    }
    public List<OBDSer> getList(){
        return list;
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
        ObdPidDataListBinding binding=null;

        if(convertView==null){
            binding=DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false);
        }else{
            binding=DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(variableId, list.get(position));

        return binding.getRoot();
    }
}
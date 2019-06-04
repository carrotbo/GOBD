package com.gwkj.qixiubaodian.obd.item;

import java.io.Serializable;
import java.util.List;

/**
 * Created by carrot on 2018/8/8.
 */

public class OBDPid {

    /**
     * type : CMD
     * data : [{"pid":"00","title":"可用PID（0x01-0x20）","pid_d":"0","exp":""},{"pid":"02","title":"产生冻结帧的故障码","pid_d":"2","exp":""},{"pid":"06","title":"短期燃油修正(缸组1)","pid_d":"6","exp":"B3*100/128-100"},{"pid":"07","title":"长期燃油修正(缸组1)","pid_d":"7","exp":"B3*100/128-100"},{"pid":"08","title":"短期燃油修正(缸组2)","pid_d":"8","exp":"B3*100/128-100"},{"pid":"0f","title":"进气温度","pid_d":"15","exp":"B3-40"},{"pid":"10","title":"空气流量","pid_d":"16","exp":"(B3*256+B4)/100"},{"pid":"12","title":"二次空气请求","pid_d":"18","exp":"B3"},{"pid":"14","title":"氧传感器输出电压(缸组 1,传感器 1)","pid_d":"20","exp":"B3*5/1000"},{"pid":"14","title":"短期燃油修正(缸组 1,传感器 1)","pid_d":"20","exp":"B4*100/128-100"},{"pid":"19","title":"氧传感器输出电压(缸组 2,传感器 2)","pid_d":"25","exp":"B3*5/1000"},{"pid":"19","title":"短期燃油修正(缸组 2,传感器 2)","pid_d":"25","exp":"B4*100/128-100"},{"pid":"1a","title":"氧传感器输出电压(缸组 2,传感器 3)","pid_d":"26","exp":"B3*5/1000"},{"pid":"1a","title":"短期燃油修正(缸组 2,传感器 3)","pid_d":"26","exp":"B4*100/128-100"},{"pid":"1b","title":"氧传感器输出电压(缸组 2,传感器 4)","pid_d":"27","exp":"B3*5/1000"},{"pid":"1b","title":"短期燃油修正(缸组 2,传感器 4)","pid_d":"27","exp":"B4*100/128-100"}]
     */

    private String type;
    private List<DataBean> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * pid : 00
         * title : 可用PID（0x01-0x20）
         * pid_d : 0
         * exp :
         */

        private String sid;
        private String pid;
        private String title;
        private String pid_d;
        private String exp;
        private String unit;
        private String data;
        private String dtc;
        private boolean ischeck;

        public String getDtc() {
            return dtc;
        }

        public void setDtc(String dtc) {
            this.dtc = dtc;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public boolean ischeck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPid_d() {
            return pid_d;
        }

        public void setPid_d(String pid_d) {
            this.pid_d = pid_d;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }
    }
}

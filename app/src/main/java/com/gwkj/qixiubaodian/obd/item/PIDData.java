package com.gwkj.qixiubaodian.obd.item;

import java.util.List;

/**
 * Created by carrot on 2018/8/14.
 */

public class PIDData {

    /**
     * type : PIDDATA
     * data : [{"pid":"010c","data":">41 0C DF 00 "},{"pid":"010d","data":">41 0D D6 "}]
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

    public static class DataBean {
        /**
         * pid : 010c
         * data : >41 0C DF 00
         */

        private String pid;
        private String data;
        private String dtc;

        public String getDtc() {
            return dtc;
        }

        public void setDtc(String dtc) {
            this.dtc = dtc;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}

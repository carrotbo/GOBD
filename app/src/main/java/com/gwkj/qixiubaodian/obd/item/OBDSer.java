package com.gwkj.qixiubaodian.obd.item;

import java.util.List;

/**
 * Created by carrot on 2018/8/10.
 */

public class OBDSer {


    /**
     * cmd : 03
     * type : DTC
     * title : 已确认故障码
     * varname :
     * dtc : [{"code":"P0100","c1":"0","c2":"1","c3":"0","c4":"0","desc":"此处为故障码描述"},{"code":"P0101","c1":"0","c2":"1","c3":"0","c4":"1","desc":"此处为故障码描述"},{"code":"P0102","c1":"0","c2":"1","c3":"0","c4":"2","desc":"此处为故障码描述"}]
     */

    private String cmd;
    private String type;
    private String varname;
    private String data;
    private String vin;
    private String did;
    private String DPN;
    private String E0;
    private String E1;
    private String E2;
    private String unit;
    private String title;
    private String value;
    private String time;
    private List<DtcBean> dtc;
    private List<OBDPid.DataBean> table;

    public List<OBDPid.DataBean> getTable() {
        return table;
    }

    public void setTable(List<OBDPid.DataBean> table) {
        this.table = table;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDPN() {
        return DPN;
    }

    public void setDPN(String DPN) {
        this.DPN = DPN;
    }

    public String getE0() {
        return E0;
    }

    public void setE0(String e0) {
        E0 = e0;
    }

    public String getE1() {
        return E1;
    }

    public void setE1(String e1) {
        E1 = e1;
    }

    public String getE2() {
        return E2;
    }

    public void setE2(String e2) {
        E2 = e2;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    public List<DtcBean> getDtc() {
        return dtc;
    }

    public void setDtc(List<DtcBean> dtc) {
        this.dtc = dtc;
    }

    public static class DtcBean {
        /**
         * code : P0100
         * c1 : 0
         * c2 : 1
         * c3 : 0
         * c4 : 0
         * desc : 此处为故障码描述
         */

        private String code;
        private String c1;
        private String c2;
        private String c3;
        private String c4;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getC1() {
            return c1;
        }

        public void setC1(String c1) {
            this.c1 = c1;
        }

        public String getC2() {
            return c2;
        }

        public void setC2(String c2) {
            this.c2 = c2;
        }

        public String getC3() {
            return c3;
        }

        public void setC3(String c3) {
            this.c3 = c3;
        }

        public String getC4() {
            return c4;
        }

        public void setC4(String c4) {
            this.c4 = c4;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}

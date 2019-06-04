package com.gwkj.qixiubaodian.obd.item;

import java.util.List;

/**
 * Created by carrot on 2018/8/4.
 */

public class OBDItem {


    /**
     * device_name : KONNWEI
     * device_type : ELM327
     * init_cmd : [{"cmd":"ATE0","end_wait":"100","is_sendtoserver":"0"},{"cmd":"0100","end_wait":"100","is_sendtoserver":"1"},{"cmd":"0120","end_wait":"100","is_sendtoserver":"1"},{"cmd":"0140","end_wait":"100","is_sendtoserver":"1"},{"cmd":"0902","end_wait":"100","is_sendtoserver":"1"}]
     */

    private String device_name;
    private String device_type;
    private List<InitCmdBean> init_cmd;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public List<InitCmdBean> getInit_cmd() {
        return init_cmd;
    }

    public void setInit_cmd(List<InitCmdBean> init_cmd) {
        this.init_cmd = init_cmd;
    }

    public static class InitCmdBean {
        /**
         * cmd : ATE0
         * end_wait : 100
         * is_sendtoserver : 0
         */

        private String cmd;
        private String end_wait;
        private String is_sendtoserver;
        private String sendparam;

        public String getSendparam() {
            return sendparam;
        }

        public void setSendparam(String sendparam) {
            this.sendparam = sendparam;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public String getEnd_wait() {
            return end_wait;
        }

        public void setEnd_wait(String end_wait) {
            this.end_wait = end_wait;
        }

        public String getIs_sendtoserver() {
            return is_sendtoserver;
        }

        public void setIs_sendtoserver(String is_sendtoserver) {
            this.is_sendtoserver = is_sendtoserver;
        }
    }
}

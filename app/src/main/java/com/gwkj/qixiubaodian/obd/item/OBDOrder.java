package com.gwkj.qixiubaodian.obd.item;

import java.io.Serializable;
import java.util.List;

/**
 * Created by carrot on 2018/8/6.
 */

public class OBDOrder {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 丰田子系统
         * submenu : [{"title":"发动机系统","type":"menu","data":"0102","submenu":[{"title":"读取故障码","type":"cmd","data":"02"},{"title":"清除故障码","type":"cmd","data":"02"},{"title":"数据流","type":"PID","data":"02"},{"title":"动作测试","type":"TEST","data":"02"}]},{"title":"变速箱系统","type":"menu","data":"04","submenu":[{"title":"故障码","type":"cmd","data":"02"},{"title":"数据流","type":"PID","data":"02"}]},{"title":"ABS系统","type":"menu","pinpaiid":"","data":"","submenu":[{"title":"故障码","type":"cmd","data":"02"},{"title":"数据流","type":"PID","data":"02"}]}]
         */

        private String title;
        private List<SubmenuBeanX> submenu;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SubmenuBeanX> getSubmenu() {
            return submenu;
        }

        public void setSubmenu(List<SubmenuBeanX> submenu) {
            this.submenu = submenu;
        }

        public static class SubmenuBeanX implements Serializable {
            /**
             * title : 发动机系统
             * type : menu
             * data : 0102
             * submenu : [{"title":"读取故障码","type":"cmd","data":"02"},{"title":"清除故障码","type":"cmd","data":"02"},{"title":"数据流","type":"PID","data":"02"},{"title":"动作测试","type":"TEST","data":"02"}]
             * pinpaiid :
             */

            private String title;
            private String type;
            private String data;
            private String pinpaiid;
            private String sendparam;
            private List<InputparamBean> inputparam;

            public String getSendparam() {
                return sendparam;
            }

            public void setSendparam(String sendparam) {
                this.sendparam = sendparam;
            }

            private List<SubmenuBean> submenu;

            public List<InputparamBean> getInputparam() {
                return inputparam;
            }

            public void setInputparam(List<InputparamBean> inputparam) {
                this.inputparam = inputparam;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getPinpaiid() {
                return pinpaiid;
            }

            public void setPinpaiid(String pinpaiid) {
                this.pinpaiid = pinpaiid;
            }

            public List<SubmenuBean> getSubmenu() {
                return submenu;
            }

            public void setSubmenu(List<SubmenuBean> submenu) {
                this.submenu = submenu;
            }

            public static class SubmenuBean implements Serializable{
                /**
                 * title : 读取故障码
                 * type : cmd
                 * data : 02
                 */

                private String title;
                private String type;
                private String data;
                private String pinpaiid;
                private String sendparam;
                private List<InputparamBean> inputparam;

                public List<InputparamBean> getInputparam() {
                    return inputparam;
                }

                public void setInputparam(List<InputparamBean> inputparam) {
                    this.inputparam = inputparam;
                }

                public String getPinpaiid() {
                    return pinpaiid;
                }

                public void setPinpaiid(String pinpaiid) {
                    this.pinpaiid = pinpaiid;
                }

                public String getSendparam() {
                    return sendparam;
                }

                public void setSendparam(String sendparam) {
                    this.sendparam = sendparam;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }

            }

        }
    }
}

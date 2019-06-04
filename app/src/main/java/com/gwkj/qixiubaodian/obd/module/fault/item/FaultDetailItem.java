package com.gwkj.qixiubaodian.obd.module.fault.item;

/**
 * Created by carrot on 2019/3/11.
 */

public class FaultDetailItem {

    /**
     * ret : ok
     * id : 10487
     * code :
     P0010
     * codedesc :
     P0010:凸轮轴调节阀控制线路开路故障
     * yuanyin :
     01 可能存在以下问题：
     01-01 OCV 电磁阀（凸轮轴调节阀）信号电路开路，用万用表测量ECU 到调节阀信号线2号脚之间的电阻。
     * yingxiang :
     * fangan :
     01 使用诊断仪读取故障码
     02 P0010 凸轮轴调节阀控制线路开路故障
     * beizhu :
     * html :
     * about_text :
     * type : 1
     */

    private String ret;
    private String id;
    private String code;
    private String codedesc;
    private String yuanyin;
    private String yingxiang;
    private String fangan;
    private String beizhu;
    private String html;
    private String about_text;
    private int type;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodedesc() {
        if(codedesc.startsWith("\n")){
            codedesc=codedesc.substring(2,codedesc.length());
        }
        return codedesc;
    }

    public void setCodedesc(String codedesc) {
        this.codedesc = codedesc;
    }

    public String getYuanyin() {
        if(yuanyin.startsWith("\n")){
            yuanyin=yuanyin.substring(2,yuanyin.length());
        }
        return yuanyin;
    }

    public void setYuanyin(String yuanyin) {
        this.yuanyin = yuanyin;
    }

    public String getYingxiang() {
        return yingxiang;
    }

    public void setYingxiang(String yingxiang) {
        this.yingxiang = yingxiang;
    }

    public String getFangan() {
        if(fangan.startsWith("\n")){
            fangan=fangan.substring(2,fangan.length());
        }
        return fangan;
    }

    public void setFangan(String fangan) {
        this.fangan = fangan;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getAbout_text() {
        return about_text;
    }

    public void setAbout_text(String about_text) {
        this.about_text = about_text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

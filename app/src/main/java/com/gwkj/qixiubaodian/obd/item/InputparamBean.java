package com.gwkj.qixiubaodian.obd.item;

import java.io.Serializable;

/**
 * Created by carrot on 2018/9/4.
 */

public class InputparamBean implements Serializable {
    private String type;
    private String key;
    private String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
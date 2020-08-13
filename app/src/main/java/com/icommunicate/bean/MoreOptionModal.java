package com.icommunicate.bean;

public class MoreOptionModal {

    int module_id;
    String module_name;

    public MoreOptionModal(int module_id, String module_name) {
        this.module_id = module_id;
        this.module_name = module_name;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }
}

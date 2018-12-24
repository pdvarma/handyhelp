package com.handyhelp.handlyhelp.pojo;

/**
 * Created by tanmay on 05/08/18.
 */

public class Service {
    String service_id;
    String service_name;

    public Service(String service_id, String service_name) {
        this.service_id = service_id;
        this.service_name = service_name;
    }

    public Service() {
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }
}

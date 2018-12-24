package com.handyhelp.handlyhelp.pojo;

import java.io.Serializable;

/**
 * Created by tanmay on 05/08/18.
 */

public class Helper implements Serializable {
    String helper_id;
    String full_name;
    String email;
    String contact;
    String rating;
    String latitude;
    String longitude;
    String services;
    String service_name;

    public Helper() {
    }

    public Helper(String helper_id, String full_name, String email, String contact, String rating, String latitude, String longitude, String services, String service_name) {
        this.helper_id = helper_id;
        this.full_name = full_name;
        this.email = email;
        this.contact = contact;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.services = services;
        this.service_name = service_name;
    }

    public String getHelper_id() {
        return helper_id;
    }

    public void setHelper_id(String helper_id) {
        this.helper_id = helper_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }
}

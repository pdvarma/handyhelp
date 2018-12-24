package com.handyhelp.handlyhelp.pojo;

/**
 * Created by tanmay on 06/08/18.
 */

public class Booked {
    String user_id;
    String heler_id;
    String service_id;
    String full_name_user;
    String email_user;
    String contact_user;
    String full_name_helper;
    String email_helper;
    String contact_helper;
    String rating;
    String service_name;
    String date;

    public Booked() {
    }

    public Booked(String user_id, String heler_id, String service_id, String full_name_user, String email_user, String contact_user, String full_name_helper, String email_helper, String contact_helper, String rating, String service_name, String date) {
        this.user_id = user_id;
        this.heler_id = heler_id;
        this.service_id = service_id;
        this.full_name_user = full_name_user;
        this.email_user = email_user;
        this.contact_user = contact_user;
        this.full_name_helper = full_name_helper;
        this.email_helper = email_helper;
        this.contact_helper = contact_helper;
        this.rating = rating;
        this.service_name = service_name;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHeler_id() {
        return heler_id;
    }

    public void setHeler_id(String heler_id) {
        this.heler_id = heler_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getFull_name_user() {
        return full_name_user;
    }

    public void setFull_name_user(String full_name_user) {
        this.full_name_user = full_name_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getContact_user() {
        return contact_user;
    }

    public void setContact_user(String contact_user) {
        this.contact_user = contact_user;
    }

    public String getFull_name_helper() {
        return full_name_helper;
    }

    public void setFull_name_helper(String full_name_helper) {
        this.full_name_helper = full_name_helper;
    }

    public String getEmail_helper() {
        return email_helper;
    }

    public void setEmail_helper(String email_helper) {
        this.email_helper = email_helper;
    }

    public String getContact_helper() {
        return contact_helper;
    }

    public void setContact_helper(String contact_helper) {
        this.contact_helper = contact_helper;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

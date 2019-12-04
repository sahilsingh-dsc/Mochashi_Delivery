package com.tetraval.mochashidelivery.model;

public class CustomerModel {

    String p_uid;
    String p_photo;
    String p_name;
    String p_address;
    String p_lat;
    String p_long;
    String p_unit;
    String p_category;
    String p_qty;
    String p_rate;
    String p_total_amount;
    String p_status;
    String p_delivered;
    String o_customer_uid;

    public CustomerModel() {
    }

    public CustomerModel(String p_uid, String p_photo, String p_name, String p_address, String p_lat, String p_long, String p_unit, String p_category, String p_qty, String p_rate, String p_total_amount, String p_status, String p_delivered, String o_customer_uid) {
        this.p_uid = p_uid;
        this.p_photo = p_photo;
        this.p_name = p_name;
        this.p_address = p_address;
        this.p_lat = p_lat;
        this.p_long = p_long;
        this.p_unit = p_unit;
        this.p_category = p_category;
        this.p_qty = p_qty;
        this.p_rate = p_rate;
        this.p_total_amount = p_total_amount;
        this.p_status = p_status;
        this.p_delivered = p_delivered;
        this.o_customer_uid = o_customer_uid;
    }

    public String getP_uid() {
        return p_uid;
    }

    public void setP_uid(String p_uid) {
        this.p_uid = p_uid;
    }

    public String getP_photo() {
        return p_photo;
    }

    public void setP_photo(String p_photo) {
        this.p_photo = p_photo;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_address() {
        return p_address;
    }

    public void setP_address(String p_address) {
        this.p_address = p_address;
    }

    public String getP_lat() {
        return p_lat;
    }

    public void setP_lat(String p_lat) {
        this.p_lat = p_lat;
    }

    public String getP_long() {
        return p_long;
    }

    public void setP_long(String p_long) {
        this.p_long = p_long;
    }

    public String getP_unit() {
        return p_unit;
    }

    public void setP_unit(String p_unit) {
        this.p_unit = p_unit;
    }

    public String getP_category() {
        return p_category;
    }

    public void setP_category(String p_category) {
        this.p_category = p_category;
    }

    public String getP_qty() {
        return p_qty;
    }

    public void setP_qty(String p_qty) {
        this.p_qty = p_qty;
    }

    public String getP_rate() {
        return p_rate;
    }

    public void setP_rate(String p_rate) {
        this.p_rate = p_rate;
    }

    public String getP_total_amount() {
        return p_total_amount;
    }

    public void setP_total_amount(String p_total_amount) {
        this.p_total_amount = p_total_amount;
    }

    public String getP_status() {
        return p_status;
    }

    public void setP_status(String p_status) {
        this.p_status = p_status;
    }

    public String getP_delivered() {
        return p_delivered;
    }

    public void setP_delivered(String p_delivered) {
        this.p_delivered = p_delivered;
    }

    public String getO_customer_uid() {
        return o_customer_uid;
    }

    public void setO_customer_uid(String o_customer_uid) {
        this.o_customer_uid = o_customer_uid;
    }
}

package com.tetraval.mochashidelivery.model;

public class ChashiModel {

    String c_uid;
    String c_photo;
    String c_name;
    String c_address;
    String c_lat;
    String c_long;
    String c_unit;
    String c_category;
    String c_total_qty;
    String c_rate;
    String c_total_amount;
    String c_status;
    String c_received;

    public ChashiModel() {
    }

    public ChashiModel(String c_uid, String c_photo, String c_name, String c_address, String c_lat, String c_long, String c_unit, String c_category, String c_total_qty, String c_rate, String c_total_amount, String c_status, String c_received) {
        this.c_uid = c_uid;
        this.c_photo = c_photo;
        this.c_name = c_name;
        this.c_address = c_address;
        this.c_lat = c_lat;
        this.c_long = c_long;
        this.c_unit = c_unit;
        this.c_category = c_category;
        this.c_total_qty = c_total_qty;
        this.c_rate = c_rate;
        this.c_total_amount = c_total_amount;
        this.c_status = c_status;
        this.c_received = c_received;
    }

    public String getC_uid() {
        return c_uid;
    }

    public void setC_uid(String c_uid) {
        this.c_uid = c_uid;
    }

    public String getC_photo() {
        return c_photo;
    }

    public void setC_photo(String c_photo) {
        this.c_photo = c_photo;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public String getC_lat() {
        return c_lat;
    }

    public void setC_lat(String c_lat) {
        this.c_lat = c_lat;
    }

    public String getC_long() {
        return c_long;
    }

    public void setC_long(String c_long) {
        this.c_long = c_long;
    }

    public String getC_unit() {
        return c_unit;
    }

    public void setC_unit(String c_unit) {
        this.c_unit = c_unit;
    }

    public String getC_category() {
        return c_category;
    }

    public void setC_category(String c_category) {
        this.c_category = c_category;
    }

    public String getC_total_qty() {
        return c_total_qty;
    }

    public void setC_total_qty(String c_total_qty) {
        this.c_total_qty = c_total_qty;
    }

    public String getC_rate() {
        return c_rate;
    }

    public void setC_rate(String c_rate) {
        this.c_rate = c_rate;
    }

    public String getC_total_amount() {
        return c_total_amount;
    }

    public void setC_total_amount(String c_total_amount) {
        this.c_total_amount = c_total_amount;
    }

    public String getC_status() {
        return c_status;
    }

    public void setC_status(String c_status) {
        this.c_status = c_status;
    }

    public String getC_received() {
        return c_received;
    }

    public void setC_received(String c_received) {
        this.c_received = c_received;
    }
}

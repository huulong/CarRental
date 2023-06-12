package com.greenhuecity.data.model;

import java.io.Serializable;

public class UserOrder implements Serializable {
    int car_id;
    String car_img, car_name, license_plates;
    int distributor_id;
    String distributor_name, distributor_photo, address;
    double latitude, longitude;
    int order_id;
    String code, status, from_time, end_time;
    double price;

    public UserOrder(int car_id, String car_img, String car_name, String license_plates, int distributor_id, String distributor_name, String distributor_photo, String address, double latitude, double longitude, int order_id, String code, String status, String from_time, String end_time, double price) {
        this.car_id = car_id;
        this.car_img = car_img;
        this.car_name = car_name;
        this.license_plates = license_plates;
        this.distributor_id = distributor_id;
        this.distributor_name = distributor_name;
        this.distributor_photo = distributor_photo;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.order_id = order_id;
        this.code = code;
        this.status = status;
        this.from_time = from_time;
        this.end_time = end_time;
        this.price = price;
    }

    public int getCar_id() {
        return car_id;
    }

    public String getCar_img() {
        return car_img;
    }

    public String getCar_name() {
        return car_name;
    }

    public String getLicense_plates() {
        return license_plates;
    }

    public int getDistributor_id() {
        return distributor_id;
    }

    public String getDistributor_name() {
        return distributor_name;
    }

    public String getDistributor_photo() {
        return distributor_photo;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getFrom_time() {
        return from_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public double getPrice() {
        return price;
    }
}

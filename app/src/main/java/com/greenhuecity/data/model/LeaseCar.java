package com.greenhuecity.data.model;

import java.io.Serializable;

public class LeaseCar implements Serializable {

    int car_id;
    String car_img, car_name;
    double price;
    String description, license_plates, from_time, end_time, approve, status,power_name, power_value;
    double spec_top_speed, spec_horse_power, spec_mileage;
    String brand_name;
    int distributor_id;
    String name, distributor_photo;

    public LeaseCar(int car_id, String car_img, String car_name, double price, String description, String license_plates, String from_time, String end_time, String approve, String status, String power_name, String power_value, double spec_top_speed, double spec_horse_power, double spec_mileage, String brand_name, int distributor_id, String name, String distributor_photo) {
        this.car_id = car_id;
        this.car_img = car_img;
        this.car_name = car_name;
        this.price = price;
        this.description = description;
        this.license_plates = license_plates;
        this.from_time = from_time;
        this.end_time = end_time;
        this.approve = approve;
        this.status = status;
        this.power_name = power_name;
        this.power_value = power_value;
        this.spec_top_speed = spec_top_speed;
        this.spec_horse_power = spec_horse_power;
        this.spec_mileage = spec_mileage;
        this.brand_name = brand_name;
        this.distributor_id = distributor_id;
        this.name = name;
        this.distributor_photo = distributor_photo;
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

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getLicense_plates() {
        return license_plates;
    }

    public String getFrom_time() {
        return from_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getApprove() {
        return approve;
    }

    public String getStatus() {
        return status;
    }

    public String getPower_name() {
        return power_name;
    }

    public String getPower_value() {
        return power_value;
    }

    public double getSpec_top_speed() {
        return spec_top_speed;
    }

    public double getSpec_horse_power() {
        return spec_horse_power;
    }

    public double getSpec_mileage() {
        return spec_mileage;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public int getDistributor_id() {
        return distributor_id;
    }

    public String getName() {
        return name;
    }

    public String getDistributor_photo() {
        return distributor_photo;
    }
}

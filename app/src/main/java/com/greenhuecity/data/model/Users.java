package com.greenhuecity.data.model;

public class Users {
    int id;
    String email,password,photo,address;
    int age;
    String fullname,phone, cccd;

    public Users(int id, String email, String password, String photo, String address, int age, String fullname, String phone, String cccd) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.address = address;
        this.age = age;
        this.fullname = fullname;
        this.phone = phone;
        this.cccd = cccd;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone() {
        return phone;
    }

    public String getCccd() {
        return cccd;
    }
}

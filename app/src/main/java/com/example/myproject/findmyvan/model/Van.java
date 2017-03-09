package com.example.myproject.findmyvan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by TheeranaiAsipong on 30/9/2559.
 */

public class Van {

    public Van(){}


    public class listVan implements Serializable {

        public listVan() {}

        @SerializedName("id_car")
        private int id_car;
        @SerializedName("address")
        private String address;
        @SerializedName("lattitude")
        private String lattitude;
        @SerializedName("logtitude")
        private String logtitude;
        @SerializedName("img_car")
        private String img_car;
        @SerializedName("tel")
        private String tel;
        @SerializedName("Timeservie")
        private String Timeservie;

        public int getId_car() {
            return id_car;
        }

        public void setId_car(int id_car) {
            this.id_car = id_car;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLattitude() {
            return lattitude;
        }

        public void setLattitude(String lattitude) {
            this.lattitude = lattitude;
        }

        public String getLogtitude() {
            return logtitude;
        }

        public void setLogtitude(String logtitude) {
            this.logtitude = logtitude;
        }

        public String getImg_car() {
            return img_car;
        }

        public void setImg_car(String img_car) {
            this.img_car = img_car;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTimeservie() {
            return Timeservie;
        }

        public void setTimeservie(String timeservie) {
            Timeservie = timeservie;
        }
    }

    public class ListSubVan implements Serializable {

        public ListSubVan(){}

        @SerializedName("carD_id")
        private int carD_id;
        @SerializedName("fk_id_car")
        private int fk_id_car;
        @SerializedName("carD_destination")
        private String carD_destination;
        @SerializedName("carD_name")
        private String carD_name;
        @SerializedName("carD_license")
        private String carD_license;
        @SerializedName("carD_price")
        private String carD_price;
        @SerializedName("carD_tel")
        private String carD_tel;
        @SerializedName("carD_img")
        private String carD_img;

        public int getCarD_id() {
            return carD_id;
        }

        public void setCarD_id(int carD_id) {
            this.carD_id = carD_id;
        }

        public int getFk_id_car() {
            return fk_id_car;
        }

        public void setFk_id_car(int fk_id_car) {
            this.fk_id_car = fk_id_car;
        }

        public String getCarD_destination() {
            return carD_destination;
        }

        public void setCarD_destination(String carD_destination) {
            this.carD_destination = carD_destination;
        }

        public String getCarD_name() {
            return carD_name;
        }

        public void setCarD_name(String carD_name) {
            this.carD_name = carD_name;
        }

        public String getCarD_license() {
            return carD_license;
        }

        public void setCarD_license(String carD_license) {
            this.carD_license = carD_license;
        }

        public String getCarD_price() {
            return carD_price;
        }

        public void setCarD_price(String carD_price) {
            this.carD_price = carD_price;
        }

        public String getCarD_tel() {
            return carD_tel;
        }

        public void setCarD_tel(String carD_tel) {
            this.carD_tel = carD_tel;
        }

        public String getCarD_img() {
            return carD_img;
        }

        public void setCarD_img(String carD_img) {
            this.carD_img = carD_img;
        }
    }
}

package com.example.myproject.findmyvan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by TheeranaiAsipong on 1/10/2559.
 */

public class DetailCar {
    public static String nameaddress;
    public DetailCar(String nameaddress) {
        this.nameaddress = nameaddress;
    }



    public class Listdetailcar implements Serializable {

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


        public Listdetailcar() {
        }

    }
}

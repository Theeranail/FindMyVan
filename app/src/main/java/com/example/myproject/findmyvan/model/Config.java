package com.example.myproject.findmyvan.model;

/**
 * Created by TheeranaiAsipong on 4/6/2559.
 */
public class Config {


    //Url ติดต่อกับ server http://localhost/VanBackendServiceMobile/
    public static final String Baseurl =  "http://follow.16mb.com/FindVan/VanBackendServiceMobile/";
  //  public static final String Baseurl =  "http://10.0.2.2/VanBackendServiceMobile/"; //สำหรับ offline
   // public static final String Baseurl =  "http://192.168.1.162/VanBackendServiceMobile/";
    public static final String url_regist = Baseurl+"controller/Member.php";
    public static final String url_login = Baseurl+"controller/Member.php";
    public static final String url_getVan = Baseurl+"controller/Van.php";
    public static final String url_review = Baseurl+"controller/Review.php";

}

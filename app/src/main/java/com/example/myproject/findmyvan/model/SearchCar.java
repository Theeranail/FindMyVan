package com.example.myproject.findmyvan.model;

import com.example.myproject.findmyvan.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TheeranaiAsipong on 30/9/2559.
 */

public class SearchCar {
    public static final String[] lismenu = {"บริเวณใกล้ตัว"};
    public static final int[] iconlistmenu = {R.drawable.ic_place_black_24dp};
    public double distace = 0;
    public SearchCar() {

    }

    public ArrayList<HashMap<String, Object>> AddrayyObj() {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;

        for (int i = 0; i < lismenu.length; i++) {
            map = new HashMap<String, Object>();
            map.put("name", lismenu[i]);
            map.put("img", iconlistmenu[i]);
            list.add(map);

        }
        return list;
    }
}

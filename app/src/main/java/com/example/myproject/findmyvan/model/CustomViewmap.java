package com.example.myproject.findmyvan.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.myproject.findmyvan.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by TheeranaiAsipong on 30/9/2559.
 */

public class CustomViewmap {

    public CustomViewmap(){}

    public static class setViewTitle implements GoogleMap.InfoWindowAdapter {
        private Context context;

        public setViewTitle(Context context) {
            this.context = context;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.content_listview_main,null);
            TextView textName = (TextView) view.findViewById(R.id.car_name);
            textName.setText(marker.getTitle());

            TextView text_distane = (TextView) view.findViewById(R.id.content_Timeservie);
            text_distane.setText(marker.getSnippet());

            return view;
        }
    }
}

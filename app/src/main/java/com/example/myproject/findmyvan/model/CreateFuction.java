package com.example.myproject.findmyvan.model;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.myproject.findmyvan.R;

/**
 * Created by TheeranaiAsipong on 6/6/2559.
 */
public class CreateFuction {

    Context context;

    public CreateFuction(Context context) {
        this.context = context;
    }

    public void AlertDialogOK(String msg) {

        AlertDialog.Builder dbuilder = new AlertDialog.Builder(context);
        dbuilder.setIcon(R.drawable.ic_error_outline_black_24dp);
        dbuilder.setMessage(msg);
        dbuilder.setPositiveButton("ตกลง", null);
        dbuilder.show();

    }

    public static int splitpositionmarkger(String position) {
        int i = 0;
        String[] str = position.split("m");
        i = Integer.valueOf(str[1]).intValue();
        return i;
    }

    public static int splitsnippet(String snippet){
        int i = 0;

        String[] str = snippet.split(",");
        i = Integer.valueOf(splitSTRtoInt(str[1])).intValue();
        return i;
    }

    public static int splitSTRtoInt(String str){
        int i = 0;
        String[] s = str.split("M");
        i = Integer.valueOf(s[1]).intValue();
        return i;
    }
}

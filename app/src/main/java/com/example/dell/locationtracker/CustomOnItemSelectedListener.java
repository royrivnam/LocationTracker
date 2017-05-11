package com.example.dell.locationtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by DELL on 09-Mar-17.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public static SharedPreferences s;
    public final static String ss="fname";
    public final static String k1="a";

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Constants.no_of_rec=Integer.parseInt(parent.getItemAtPosition(pos).toString());
        s=view.getContext().getSharedPreferences(ss, Context.MODE_PRIVATE);
        SharedPreferences.Editor e=s.edit();
        e.putString(k1,String.valueOf(parent.getSelectedItemPosition()));
        e.commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}

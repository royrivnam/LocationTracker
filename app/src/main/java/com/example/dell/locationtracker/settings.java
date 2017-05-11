package com.example.dell.locationtracker;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by DELL on 13-Mar-17.
 */
public class settings extends Fragment implements View.OnClickListener{

    Button b;
    Spinner sp;
    public static SharedPreferences s;
    public final static String ss="fname";
    public final static String k1="a";
    int index;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.settings, container, false);


        sp = (Spinner)view.findViewById(R.id.spinner);
        b=(Button)view.findViewById(R.id.set);
        s=this.getActivity().getSharedPreferences(ss,Context.MODE_PRIVATE);
        index=Integer.parseInt(s.getString(k1,"3"));
        sp.setSelection(index);

        sp.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        b.setOnClickListener(this);
        return view;

    }


    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.set:

            {
                final Dialog d = new Dialog(this.getActivity());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.details);
                d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView t = (TextView) d.findViewById(R.id.t1);
                t.setText("Application to find your current location. Also helps to fetch the adress at particular longitude and latitude. Store your fetched location" +
                        ". Can save the fetched location you want to save.");



              d.show();
            }

                break;
            default:

                break;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("SETTINGS");
    }
}

package com.example.dell.locationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by DELL on 13-Mar-17.
 */

public class Database extends Fragment{

    List<product_pojo> proList;
    ListView lView;

    ProductAdapter ad;
    ProductHandler db;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View view = inflater.inflate(R.layout.database, container, false);

        lView=(ListView)view.findViewById(R.id.pList);
        lView.setOnTouchListener(new swipe(getContext())
        {
            public void onSwipeLeft()
            {
                Constants.index=1;
                displaySelectedScreen2(0);

            }
        });
        db=new ProductHandler(this.getActivity());
        proList=db.getAllProducts();
        ad=new ProductAdapter(this.getActivity(),proList);
        lView.setAdapter(ad);

        return view;

    }
    public void displaySelectedScreen2(int item) {
        //creating fragment object
        Fragment fragment2 = null;
        switch(item)
        {
            case 0:
                fragment2=new tabbed2();
                break;
        }
        //initializing the fragment object which is selected
        //replacing the fragment
        if (fragment2!=null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager()
                    .beginTransaction();
            ft.replace(R.id.content_frame2, fragment2);
            try{ft.commit();}
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("DATABASE");
    }

}
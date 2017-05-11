package com.example.dell.locationtracker;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

public class tabbed extends Fragment {


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.activity_tabbed, container, false);
        displaySelectedScreen2(0);
        displaySelectedScreen(0);
        Constants.index=0;

        FrameLayout fl=(FrameLayout)view.findViewById(R.id.content_frame1);
        fl.setOnTouchListener(new swipe(view.getContext()){
            public void onSwipeLeft()
            {
                Constants.index=1;
                displaySelectedScreen2(0);
            }
            public void onSwipeRight()
            {
                Constants.index=0;
                displaySelectedScreen2(0);
            }
        });

        return view;
    }

    public void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case 0:
                fragment = new Database();
                break;

            case 1:
                fragment=new savedval();
                break;


        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame1, fragment);
            ft.commit();
        }

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

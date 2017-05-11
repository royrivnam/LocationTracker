package com.example.dell.locationtracker;

import android.content.SharedPreferences;

/**
 * Created by DELL on 03-Mar-17.
 */
public final class Constants {
    public static final int SUCCESS_RESULT=0;
    public static final int FAILURE_RESULT=1;


    public static final int USE_ADDRESS_NAME=1;
    public static final int USE_ADDRESS_LOCATION=2;

    public static final String PACKAGE_NAME=
            "com.example.dell.geocoding";
    public static final String RECEIVER=PACKAGE_NAME+".RECEIVER";
    public static final String RESULT_DATA_KEY=PACKAGE_NAME+".RESULT_DATA_KEY";
    public static final String RESULT_ADDRESS=PACKAGE_NAME+".RESULT_ADDRESS";
    public static final String LOCATION_LATITUDE_DATA_EXTRA=PACKAGE_NAME+".LOCATION_LATITUDE_DATA_EXTRA";
    public static final String LOCATION_LONGITUDE_DATA_EXTRA=PACKAGE_NAME+".LOCATION_LONGITUDE_DATA_EXTRA";
    public static final String LOCATION_NAME_DATA_EXTRA=PACKAGE_NAME+".LOCATION_NAME_DATA_EXTRA";
    public static final String FETCH_TYPE_EXTRA=PACKAGE_NAME+".FETCH_TYE_EXTRA";
    public static int val=0;
    public static int no_of_rec=100;
    public static String str="";
    public static int index=0;

}

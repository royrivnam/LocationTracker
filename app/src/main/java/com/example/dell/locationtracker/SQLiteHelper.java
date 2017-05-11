package com.example.dell.locationtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;

    public static final String DATABASE_NAME="MARKETING";

    public static final String TABLE="STOCK";
    public static final String TABLE2="STOCK2";
    public static final String date="DATE";
    public static final String latlon="PROLATLON";
    public static final String time="TIME";
    public static final String address="ADDRESS";

     public SQLiteHelper(Context context){

         super(context, DATABASE_NAME,null,DATABASE_VERSION);
     }

    public void onCreate(SQLiteDatabase db)
    {

        String p="CREATE TABLE "+TABLE+" ("+date+" DATE,"+latlon+" TEXT,"+time+" TEXT,"+address+" TEXT, PRIMARY KEY ("+date+","
        +time+"))";
        db.execSQL(p);
        String pp="CREATE TABLE "+TABLE2+" ("+date+" DATE,"+latlon+" TEXT,"+time+" TEXT,"+address+" TEXT, PRIMARY KEY ("+date+","
                +time+"))";
        db.execSQL(pp);

    }

    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);


        onCreate(db);


    }


}

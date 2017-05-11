package com.example.dell.locationtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;


public class ProductHandler {

    private SQLiteHelper dbHelper;

    public ProductHandler(Context context){dbHelper=new SQLiteHelper(context);}

    int addProduct(product_pojo pj)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues v=new ContentValues();
        v.put(dbHelper.date,pj.getDate());
        v.put(dbHelper.latlon,pj.getLatlon());
        v.put(dbHelper.time,pj.getTime());
        v.put(dbHelper.address,pj.getAddress());
        long idE=0;
        try{idE=db.insert(dbHelper.TABLE,null,v);}
        catch(Exception e)
        {
            e.printStackTrace();
        }

        db.close();

        return (int)idE;
    }
    int addProductval(product_pojo pj)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues v=new ContentValues();
        v.put(dbHelper.date,pj.getDate());
        v.put(dbHelper.latlon,pj.getLatlon());
        v.put(dbHelper.time,pj.getTime());
        v.put(dbHelper.address,pj.getAddress());
        long idE=0;
        try{idE=db.insert(dbHelper.TABLE2,null,v);}
        catch(Exception e)
        {
            e.printStackTrace();
        }
        db.close();

        return (int)idE;
    }
    product_pojo getProduct(int id)
    {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.query(dbHelper.TABLE,new String[]{dbHelper.date,dbHelper.latlon,dbHelper.time,
                           dbHelper.address},dbHelper.date+"=?",new String[]{String.valueOf(id)},null,null,null,null);

        if(c!=null)
            c.moveToFirst();

        product_pojo pj=new product_pojo(c.getString(0),c.getString(1),
               c.getString(2),c.getString(3));

        return pj;
    }

    public int updateProduct(product_pojo pj)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues v=new ContentValues();
        v.put(dbHelper.date,pj.getDate());
        v.put(dbHelper.latlon,pj.getLatlon());
        v.put(dbHelper.time,pj.getTime());
        v.put(dbHelper.address,pj.getAddress());

        return db.update(dbHelper.TABLE,v,dbHelper.date+" = ?",new String[]{pj.getDate()});

    }

    public void deleteProduct(product_pojo pj)
    {

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE,dbHelper.date+"= ? and "+dbHelper.time+"= ?",new String[]{pj.getDate(),pj.getTime()});
        db.close();
    }
    public void deleteProductval(product_pojo pj)
    {

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE2,dbHelper.date+"= ? and "+dbHelper.time+"= ?",new String[]{pj.getDate(),pj.getTime()});
        db.close();
    }


    public List<product_pojo> getAllProducts(){

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        List<product_pojo> pjList=new ArrayList<product_pojo>();
        String selque="SELECT  * FROM " + dbHelper.TABLE;

        Cursor c=db.rawQuery(selque,null);
        int k=0;
        if(c.moveToLast())
        {
            do{
               product_pojo pj=new product_pojo();
                pj.setDate(c.getString(0));
                pj.setLatlon(c.getString(1));
                pj.setTime(c.getString(2));
                pj.setAddress(c.getString(3));
                k++;


                pjList.add(pj);
            }while(c.moveToPrevious() && k<Constants.no_of_rec);
            while(c.moveToPrevious())
            {
                db.delete(dbHelper.TABLE,dbHelper.date+"= ? and "+dbHelper.time+"= ?",new String[]{c.getString(0),c.getString(2)});
            }
        }

        return pjList;

    }
    public List<product_pojo> getAllProductsval(){

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        List<product_pojo> pjList=new ArrayList<product_pojo>();
        String selque="SELECT  * FROM " + dbHelper.TABLE2+" ORDER BY "+dbHelper.date+","+dbHelper.time;

        Cursor c=db.rawQuery(selque,null);
        int k=0;
        if(c.moveToLast())
        {
            do{
                product_pojo pj=new product_pojo();
                pj.setDate(c.getString(0));
                pj.setLatlon(c.getString(1));
                pj.setTime(c.getString(2));
                pj.setAddress(c.getString(3));
                k++;


                pjList.add(pj);
            }while(c.moveToPrevious());

        }

        return pjList;

    }

    public int getProductsCount() {

        String countQuery = "SELECT  * FROM " + dbHelper.TABLE;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }




}

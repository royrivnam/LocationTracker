package com.example.dell.locationtracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ProductAdapter extends BaseAdapter {


    private Activity act;
    private LayoutInflater inflater;
    private List<product_pojo> proList;
    private Context context;

    public ProductAdapter(Activity act, List<product_pojo> proList)
    {
        this.act=act;
        this.proList=proList;

    }

    @Override
    public int getCount(){return proList.size();}

    @Override
    public Object getItem(int position){return proList.get(position);}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(final int position, View v, ViewGroup parent)
    {
    if(inflater==null)
        inflater=(LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     if(v==null)
         v=inflater.inflate(R.layout.pro_view_format,null);

        TextView p1= (TextView) v.findViewById(R.id.t1);
        TextView p2= (TextView) v.findViewById(R.id.t2);
        TextView p3= (TextView)v.findViewById(R.id.t3);
        Button b=(Button)v.findViewById(R.id.b1);

        final product_pojo list=proList.get(position);

        p1.setText(list.getDate());
        p2.setText(list.getLatlon());
        p3.setText(list.getTime());

        context=v.getContext();

        final View finalV = v;
        final View finalV1 = v;
        b.setOnTouchListener(new swipe2(finalV.getContext())
        {
            public void onSwipeLeft()
            {
                Constants.index=1;
                displaySelectedScreen2(0);

            }
            public void onClick()
            {
                final Dialog d=new Dialog(finalV1.getRootView().getContext());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.options);
                d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Button p1=(Button)d.findViewById(R.id.b1);
                Button p2=(Button)d.findViewById(R.id.b2);
                Button p3=(Button)d.findViewById(R.id.b3);
                Button p4=(Button)d.findViewById(R.id.b4);
                p1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dd=new Dialog(finalV1.getRootView().getContext());
                        dd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dd.setContentView(R.layout.details);
                        dd.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView t=(TextView)dd.findViewById(R.id.t1);

                        product_pojo list=proList.get(position);
                        String n=""+ list.getAddress();
                        t.setText(n);

                        dd.show();
                        d.dismiss();
                    }
                });


                p2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dd=new AlertDialog.Builder(finalV1.getRootView().getContext());
                        dd.setMessage("Are you sure you want to save this entry??");
                        dd.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                product_pojo list=proList.get(position);
                                String n=list.getAddress();
                                String dat=list.getDate();
                                String tim=list.getTime();
                                String m=list.getLatlon();

                                List<product_pojo> proList2;

                                ProductAdapter2 ad2;
                                ProductHandler db2;
                                db2=new ProductHandler(finalV1.getRootView().getContext());

                                proList2=db2.getAllProductsval();

                                ad2=new ProductAdapter2((Activity) finalV1.getRootView().getContext(),proList2);

                                int p = db2.addProductval(new product_pojo(dat, m, tim, n));
                                proList2.add(new product_pojo(dat, m, tim, n));
                                ad2.notifyDataSetChanged();

                                ProductHandler db=new ProductHandler(act);
                                db.deleteProduct(list);
                                proList.remove(position);
                                notifyDataSetChanged();
                                d.dismiss();
                                Toast.makeText(finalV1.getRootView().getContext(),"ENTRY SAVED!!", Toast.LENGTH_LONG).show();
                            }
                        });
                        dd.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                d.dismiss();
                            }
                        });
                        dd.show();
                    }
                });


                p3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        AlertDialog.Builder dd=new AlertDialog.Builder(finalV1.getRootView().getContext());
                        dd.setMessage("Are you sure you want to delete this entry from the list??");
                        dd.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ProductHandler db=new ProductHandler(act);
                                db.deleteProduct(list);
                                proList.remove(position);
                                notifyDataSetChanged();
                                d.dismiss();
                                Toast.makeText(finalV1.getRootView().getContext(),"ENTRY DELETED FROM THE LIST!!", Toast.LENGTH_LONG).show();
                            }
                        });
                        dd.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                d.dismiss();
                            }
                        });
                        dd.show();

                    }
                });

                p4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        product_pojo list=proList.get(position);

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBodyText = "Location on:\nDate: "+list.getDate()+
                                "\nTime: "+list.getTime()+
                                "\nLat/Lon: "+list.latlon+
                                "\n"+list.getAddress()+
                                "\nProvided by Location Tracker.";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                        v.getRootView().getContext().startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

                    }
                });


                d.show();
            }

        });

        //b.setOnLongClickListener(new ListItemClickListener(position,list));


        return v;

    }

   /* private class ListItemClickListener implements View.OnClickListener{

        int position;
        product_pojo list;



        public ListItemClickListener(int position,product_pojo list)
        {
            this.position=position;
            this.list=list;
        }

        @Override
        public void onClick(final View v)
        {
            final Dialog d=new Dialog(v.getRootView().getContext());
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.options);
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button p1=(Button)d.findViewById(R.id.b1);
            Button p2=(Button)d.findViewById(R.id.b2);
            Button p3=(Button)d.findViewById(R.id.b3);
            Button p4=(Button)d.findViewById(R.id.b4);
            p1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dd=new Dialog(v.getRootView().getContext());
                    dd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dd.setContentView(R.layout.details);
                    dd.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    TextView t=(TextView)dd.findViewById(R.id.t1);

                    product_pojo list=proList.get(position);
                    String n=""+ list.getAddress();
                    t.setText(n);

                    dd.show();
                    d.dismiss();
                }
            });


            p2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dd=new AlertDialog.Builder(v.getRootView().getContext());
                    dd.setMessage("Are you sure you want to save this entry??");
                    dd.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            product_pojo list=proList.get(position);
                            String n=list.getAddress();
                            String dat=list.getDate();
                            String tim=list.getTime();
                            String m=list.getLatlon();

                            List<product_pojo> proList2;

                            ProductAdapter2 ad2;
                            ProductHandler db2;
                            db2=new ProductHandler(v.getRootView().getContext());

                            proList2=db2.getAllProductsval();

                            ad2=new ProductAdapter2((Activity) v.getRootView().getContext(),proList2);

                            int p = db2.addProductval(new product_pojo(dat, m, tim, n));
                            proList2.add(new product_pojo(dat, m, tim, n));
                            ad2.notifyDataSetChanged();

                            ProductHandler db=new ProductHandler(act);
                            db.deleteProduct(list);
                            proList.remove(position);
                            notifyDataSetChanged();
                            d.dismiss();
                            Toast.makeText(v.getRootView().getContext(),"ENTRY SAVED!!", Toast.LENGTH_LONG).show();
                        }
                    });
                    dd.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            d.dismiss();
                        }
                    });
                    dd.show();
                }
            });


            p3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AlertDialog.Builder dd=new AlertDialog.Builder(v.getRootView().getContext());
                    dd.setMessage("Are you sure you want to delete this entry from the list??");
                    dd.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ProductHandler db=new ProductHandler(act);
                            db.deleteProduct(list);
                            proList.remove(position);
                            notifyDataSetChanged();
                            d.dismiss();
                            Toast.makeText(v.getRootView().getContext(),"ENTRY DELETED FROM THE LIST!!", Toast.LENGTH_LONG).show();
                        }
                    });
                    dd.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            d.dismiss();
                        }
                    });
                    dd.show();

                }
            });

            p4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    product_pojo list=proList.get(position);

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBodyText = "Location on:\nDate: "+list.getDate()+
                            "\nTime: "+list.getTime()+
                            "\nLat/Lon: "+list.latlon+
                            "\n"+list.getAddress()+
                            "\nProvided by Location Tracker.";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                    v.getRootView().getContext().startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

                }
            });


            d.show();

            //return  false;
        }

    }*/
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

            FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager()
                    .beginTransaction();
            ft.replace(R.id.content_frame2, fragment2);
            try{ft.commit();}
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

}
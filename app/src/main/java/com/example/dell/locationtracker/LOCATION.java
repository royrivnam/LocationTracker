package com.example.dell.locationtracker;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;



/**
 * Created by DELL on 04-Mar-17.
 */
public class LOCATION extends Fragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    AddressResultReceiver mResultReceiver;
    EditText e,ee,a;
    Button b,bb;
    ProgressBar p;
    GPSTracker gps;
    RadioGroup RR;
    RadioButton RR1,RR2;
    TextView t;

    boolean fetchAddress;
    int fetchType=Constants.USE_ADDRESS_LOCATION;
    public static final int USE_ADDRESS_NAME=1;
    public static final int USE_ADDRESS_LOCATION=2;
    private static final String TAG="MAIN ACTIVITY";
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
       View view= inflater.inflate(R.layout.ltmain, container, false);
        e=(EditText)view.findViewById(R.id.e1);
        ee=(EditText)view.findViewById(R.id.e2);
        a=(EditText)view.findViewById(R.id.e3);
        b=(Button)view.findViewById(R.id.b1);
        bb=(Button)view.findViewById(R.id.b3);
        p=(ProgressBar)view.findViewById(R.id.p1);
        RR=(RadioGroup)view.findViewById(R.id.radioGroup);
        RR1=(RadioButton)view.findViewById(R.id.r1);
        RR2=(RadioButton)view.findViewById(R.id.r2);
        t=(TextView)view.findViewById(R.id.t1);
        mResultReceiver=new AddressResultReceiver(null);
        b.setOnClickListener(this);
        bb.setOnClickListener(this);
        RR.setOnCheckedChangeListener(this);


        return  view;

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId)
        {
            case R.id.r1:

                e.setText("");
                ee.setText("");
                a.setText("");
                t.setText("");
                fetchAddress = true;
                fetchType = Constants.USE_ADDRESS_LOCATION;
                e.setEnabled(true);
                e.requestFocus();
                ee.setEnabled(true);
                a.setEnabled(false);

                break;
            case R.id.r2:

                e.setText("");
                ee.setText("");
                a.setText("");
                t.setText("");
                fetchAddress = false;
                fetchType = Constants.USE_ADDRESS_NAME;
                e.setEnabled(false);
                ee.setEnabled(false);
                a.setEnabled(true);
                a.requestFocus();

                break;
            default:
                break;
        }

    }
    public void onClick(View view)
    {
       switch(view.getId())
       {
           case R.id.b1:

           {
               Constants.val=1;
               t.setText("");
               Intent intent=new Intent(this.getActivity(),GeocodeAddressIntentService.class);
               intent.putExtra(Constants.RECEIVER,mResultReceiver);
               intent.putExtra(Constants.FETCH_TYPE_EXTRA,fetchType);
               if(fetchType==Constants.USE_ADDRESS_NAME)
               {
                   if(a.getText().length()==0)
                   {
                       Toast.makeText(this.getActivity(),"Please enter an address!!",Toast.LENGTH_LONG).show();
                       return;
                   }
                   intent.putExtra(Constants.LOCATION_NAME_DATA_EXTRA,a.getText().toString());
               }
               else
               {
                   if(e.getText().length()==0 || ee.getText().length()==0)
                   {
                       Toast.makeText(this.getActivity(),"Please enter both latitude and longitude",Toast.LENGTH_LONG).show();
                       return;
                   }
                   intent.putExtra(Constants.LOCATION_LATITUDE_DATA_EXTRA,Double.parseDouble(e.getText().toString()));
                   intent.putExtra(Constants.LOCATION_LONGITUDE_DATA_EXTRA,Double.parseDouble(ee.getText().toString()));
               }
               p.setVisibility(View.VISIBLE);
               Log.e(TAG,"Starting Service");
               getActivity().startService(intent);

           }


               break;
           case R.id.b3:
           {
               Constants.val=2;
               RR1.setChecked(false);
               RR2.setChecked(false);
               e.setEnabled(false);
               ee.setEnabled(false);
               a.setEnabled(false);
               RR.setEnabled(false);
               RR.setClickable(false);
               e.setText("Fetching...");
               ee.setText("Fetching...");
               a.setText("Fetching...");

               fetchType = Constants.USE_ADDRESS_LOCATION;
               gps = new GPSTracker(LOCATION.this.getActivity());
               Intent intent = new Intent(this.getActivity(), GeocodeAddressIntentService.class);
               intent.putExtra(Constants.RECEIVER, mResultReceiver);
               intent.putExtra(Constants.FETCH_TYPE_EXTRA, fetchType);
               if (fetchType == Constants.USE_ADDRESS_NAME) {
                   if (a.getText().length() == 0) {
                       Toast.makeText(this.getActivity(), "Please enter an address!!", Toast.LENGTH_LONG).show();
                       return;
                   }
                   intent.putExtra(Constants.LOCATION_NAME_DATA_EXTRA, a.getText().toString());
               }
               else {
                   if (gps.canGetLocation()) {
                       double lat = gps.getLatitude();
                       double lng = gps.getLongitude();
                       e.setText(String.valueOf(lat));
                       ee.setText(String.valueOf(lng));
                       intent.putExtra(Constants.LOCATION_LATITUDE_DATA_EXTRA, Double.parseDouble(e.getText().toString()));
                       intent.putExtra(Constants.LOCATION_LONGITUDE_DATA_EXTRA, Double.parseDouble(ee.getText().toString()));
                   }
                   p.setVisibility(View.VISIBLE);
                   Log.e(TAG, "Starting Service");
                   getActivity().startService(intent);

               }
           }

               break;
           default:
               break;
       }
    }





    class AddressResultReceiver extends ResultReceiver {
        private List<product_pojo> proList;
        private ListView lView;

        private ProductAdapter ad;
        ProductHandler db;
        public  AddressResultReceiver(Handler handler)
        {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode,final Bundle resultData)
        {
            db=new ProductHandler(getActivity());

            proList=db.getAllProducts();

            ad=new ProductAdapter(LOCATION.this.getActivity(),proList);

            if(resultCode==Constants.SUCCESS_RESULT)
            {
                final Address address=resultData.getParcelable(Constants.RESULT_ADDRESS);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        p.setVisibility(View.GONE);
                        e.setText("Lat:"+address.getLatitude());
                        ee.setText("Lon:"+address.getLongitude());
                        Constants.str=resultData.getString(Constants.RESULT_DATA_KEY);
                        a.setText("Address:"+Constants.str);
                        if(!(e.getText().toString().equals("")) && Constants.val==2) {
                            Calendar cal = Calendar.getInstance();
                            String dat = String.valueOf(cal.get(Calendar.DATE)) + "/" + String.valueOf(cal.get(Calendar.MONTH)) + "/" + String.valueOf(cal.get(Calendar.YEAR));
                            String tim = String.valueOf((cal.get(Calendar.HOUR))) + ":" + String.valueOf((cal.get(Calendar.MINUTE))) + ":" + String.valueOf((cal.get(Calendar.SECOND)));
                            t.setText("My location on " + dat + " at " + tim);
                            String m = address.getLatitude() + "/" + address.getLongitude();
                            String n = a.getText().toString();
                            int p = db.addProduct(new product_pojo(dat, m, tim, n));
                            proList.add(new product_pojo(dat, m, tim, n));
                            ad.notifyDataSetChanged();

                            RR.setEnabled(true);
                            RR.setClickable(true);
                            RR1.setChecked(false);
                            RR2.setChecked(false);
                        }
                    }
                });
            }
            else
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        p.setVisibility(View.GONE);
                        a.setText(resultData.getString(Constants.RESULT_DATA_KEY));
                    }
                });
            }


        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("LOCATION");
    }


}

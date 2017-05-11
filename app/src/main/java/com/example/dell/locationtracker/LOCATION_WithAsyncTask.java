package com.example.dell.locationtracker;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by DELL on 13-Mar-17.
 */
public class LOCATION_WithAsyncTask extends Fragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    EditText e,ee,a;
    Button b,bb;
    ProgressBar p;
    RadioGroup RR;
    RadioButton RR1,RR2;
    TextView t;

    public static final int USE_ADDRESS_NAME=1;
    public static final int USE_ADDRESS_LOCATION=2;

    int fetchType=USE_ADDRESS_LOCATION;
    private static final String TAG="MAIN_ACTIVITY_ASYNC";
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
              new GeocodeAsyncTask().execute();
            }

            break;
            case R.id.b3:
            {
               new GeocodeAsyncTask().execute();
            }
            break;
            default:
                break;
        }
    }

    class GeocodeAsyncTask extends AsyncTask<Void, Void, Address>
    {
        String errorMessage="";
        @Override
        protected void onPreExecute()
        {
            p.setVisibility(View.VISIBLE);
        }
        @Override
        protected Address doInBackground(Void ... none) {
            Geocoder geocoder = new Geocoder(LOCATION_WithAsyncTask.this.getActivity(), Locale.getDefault());
            List<Address> addresses = null;
            if (fetchType == USE_ADDRESS_NAME) {
                String name = a.getText().toString();
                try {
                    addresses = geocoder.getFromLocationName(name, 1);
                } catch (IOException e) {
                    errorMessage = "Service not available";
                    Log.e(TAG, errorMessage, e);
                }

            } else if (fetchType == USE_ADDRESS_LOCATION) {
                double latitude = Double.parseDouble(e.getText().toString());
                double longitude = Double.parseDouble(ee.getText().toString());
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException ioException) {
                    errorMessage = "Service not available";
                    Log.e(TAG, errorMessage, ioException);
                } catch (IllegalArgumentException illegalArgumentException) {
                    errorMessage = "Invalid Latitude or Longitude Used";
                    Log.e(TAG, errorMessage + "." + "Latitude=" + latitude + ",Longitude =" + longitude, illegalArgumentException);
                }
            } else {
                errorMessage = "Unknown Type";
                Log.e(TAG, errorMessage);
            }
            if (addresses != null && addresses.size() > 0) {
                return addresses.get(0);
            }
            return null;
        }

        protected void onPostExecute(Address address)
        {
            if(address==null)
            {
                p.setVisibility((View.INVISIBLE));
                a.setText(errorMessage);
            }
            else
            {
                String addressName="";
                for(int i=0;i<address.getMaxAddressLineIndex();i++)
                {
                    addressName+="---"+address.getAddressLine(i);
                }
                p.setVisibility((View.INVISIBLE));
                e.setText("Latitude: "+address.getLatitude());
                ee.setText("Longitude: "+address.getLongitude());
                a.setText("Address: "+addressName);
            }
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("LOCATION_WithAsyncTask");
    }


}

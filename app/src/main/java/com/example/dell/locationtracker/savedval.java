package com.example.dell.locationtracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

public class savedval extends Fragment {
    private List<product_pojo> proList2;
    private ListView lView2;

    private ProductAdapter2 ad2;
    ProductHandler db2;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View view = inflater.inflate(R.layout.activity_savedval, container, false);

        lView2=(ListView)view.findViewById(R.id.pList1);
        lView2.setOnTouchListener(new swipe(getContext())
        {
            public void onSwipeRight()
            {
                Constants.index=0;
                displaySelectedScreen2(0);

            }
        });
        db2=new ProductHandler(this.getActivity());
        proList2=db2.getAllProductsval();
        ad2=new ProductAdapter2(this.getActivity(),proList2);
        lView2.setAdapter(ad2);

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

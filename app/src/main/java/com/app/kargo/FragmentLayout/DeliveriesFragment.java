package com.app.kargo.FragmentLayout;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.kargo.Adapter.DeliveriesViewpagerAdapter;
import com.app.kargo.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class DeliveriesFragment extends Fragment {

    ViewPager viewpager;
    DeliveriesViewpagerAdapter viewpagerAdapter;
    TabLayout tabLayout;


    public DeliveriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_deliveries, container, false);

        //Setup viewpager and TabLayout
        viewpager  = rootview.findViewById(R.id.deliveries_viewpager);
        viewpagerAdapter = new DeliveriesViewpagerAdapter(getContext(), getChildFragmentManager());
        viewpager.setAdapter(viewpagerAdapter);
        tabLayout = rootview.findViewById(R.id.deliveries_tablayout);
        tabLayout.setupWithViewPager(viewpager);


        return rootview;
    }

}

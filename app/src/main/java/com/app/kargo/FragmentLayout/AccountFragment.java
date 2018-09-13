package com.app.kargo.FragmentLayout;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.kargo.Adapter.AccountViewpagerAdapter;
import com.app.kargo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {


    ViewPager viewpager;
    AccountViewpagerAdapter viewpagerAdapter;
    TabLayout tabLayout;



    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_account, container, false);

        //Setup viewpager and TabLayout
        viewpager  = rootview.findViewById(R.id.account_viewpager);
        viewpagerAdapter = new AccountViewpagerAdapter( getChildFragmentManager());
        viewpager.setAdapter(viewpagerAdapter);
        tabLayout = rootview.findViewById(R.id.account_tablayout);
        tabLayout.setupWithViewPager(viewpager);


        return rootview;
    }

}

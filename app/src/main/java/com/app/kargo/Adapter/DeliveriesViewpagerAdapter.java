package com.app.kargo.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.kargo.FragmentLayout.ComingDeliveriesFragment;
import com.app.kargo.FragmentLayout.CurrentDeliveriesFragment;

/**
 * Created by pbric on 04/08/2017.
 */

public class DeliveriesViewpagerAdapter extends FragmentPagerAdapter {

    Context context;


    public DeliveriesViewpagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    public String tabTitles[] = new String[] {
            "En Cours",
            "A venir"
    };

    @Override
    public Fragment getItem(int position) {
        if (position == 0 ) {
            return new CurrentDeliveriesFragment();
        } else {
            return new ComingDeliveriesFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}

package com.app.kargo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.kargo.FragmentLayout.AccountActivityFragment;
import com.app.kargo.FragmentLayout.AccountInfoFragment;

/**
 * Created by pbric on 31/07/2017.
 */

public class AccountViewpagerAdapter extends FragmentPagerAdapter {

    public String tabTitles[] = new String[] {
            "Mes Infos",
            "Mon Activite"
    };


    public AccountViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0 ) {
            return new AccountInfoFragment();
        } else {
            return new AccountActivityFragment();
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

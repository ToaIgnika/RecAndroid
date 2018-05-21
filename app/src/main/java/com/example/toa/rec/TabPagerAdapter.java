package com.example.toa.rec;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.toa.rec.Fragments.BrowseFragment;
import com.example.toa.rec.Fragments.SurveyFragment;
import com.example.toa.rec.Fragments.UserListFragment;

/**
 * This adapter handles switching between fragments
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BrowseFragment tab1 = new BrowseFragment();

                return tab1;
            case 1:
                UserListFragment tab2 = new UserListFragment();
                return tab2;
            case 2:
                SurveyFragment tab3 = new SurveyFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
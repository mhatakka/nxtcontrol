package com.example.nxtcontrol;



import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles = {
            "Files",
            "Settings",
            "Monitor",
            "Control",
            "Misc"
    };

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new FilesFragment();
            case 1: return new SettingsFragment();
            case 2: return new MonitorFragment();
            case 3: return new ControlFragment();
            case 4: return new MiscFragment();
            default: return new FilesFragment();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

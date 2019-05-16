package com.communityx.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.communityx.fragments.*;

import java.util.List;

public class SignUpPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public SignUpPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public boolean isButtonEnabled(int pos) {
        return buttonEnabledPos[pos];
    }

    public int getTotalItems(){
        return fragments.size();
    }

    private boolean[] buttonEnabledPos = {
            true, false, false, true, true
    };
}

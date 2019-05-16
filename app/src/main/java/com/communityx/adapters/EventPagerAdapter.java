package com.communityx.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.communityx.fragments.MyHostedEventFragment;

import java.util.List;

public class EventPagerAdapter extends FragmentPagerAdapter {

    private List<String> mListTitles;
    private Fragment[] fragments = {
            new MyHostedEventFragment(),
            new MyHostedEventFragment()
    };

    public EventPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setTitles(List<String> mListTitles) {
        this.mListTitles = mListTitles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mListTitles.get(position);
    }
}

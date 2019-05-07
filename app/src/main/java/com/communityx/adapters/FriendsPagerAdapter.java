package com.communityx.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.communityx.fragments.FriendsFragment;
import com.communityx.fragments.InvitationFragment;
import com.communityx.fragments.SuggestionFragment;

public class FriendsPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments = {
            new FriendsFragment(),
            new InvitationFragment(),
            new SuggestionFragment()
    };

    //TODO: Hard Coded String
    private String titles[] = {
            "All Friends",
            "Invitations",
            "Suggestions"
    };

    public FriendsPagerAdapter(FragmentManager fm) {
        super(fm);
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
        return titles[position];
    }
}

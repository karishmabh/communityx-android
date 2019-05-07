package com.communityx.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.communityx.fragments.*;

public class SignUpPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments = {
            new SignUpStudentInfoFragment(),
            new SignUpSchoolCollegeFragment(),
            new SignUpRoleFragment(),
            new SignUpMemberOfClub(),
            new SignUpSelectInterest()
    };

    private boolean[] buttonEnabledPos = {
            true, false, false, true, true
    };

    public SignUpPagerAdapter(FragmentManager fm) {
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

    public boolean isButtonEnabled(int pos) {
        return buttonEnabledPos[pos];
    }

    public int getTotalItems(){
        return fragments.length;
    }
}

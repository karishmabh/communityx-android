package com.communityx.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.communityx.fragments.SignUpRoleFragment;
import com.communityx.fragments.SignUpSchoolCollegeFragment;
import com.communityx.fragments.SignUpStudentInfoFragment;

public class SignUpPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments = {
           new SignUpStudentInfoFragment()  ,
           new SignUpSchoolCollegeFragment(),
           new SignUpRoleFragment()
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
}

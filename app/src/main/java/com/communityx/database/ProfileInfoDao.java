package com.communityx.database;

import com.communityx.R;
import com.communityx.database.fakemodels.ProfileAboutModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileInfoDao {

    public List<ProfileAboutModel> getProfileInfo(){
        return createFakeInfo();
    }

    private List<ProfileAboutModel> createFakeInfo(){
        List<ProfileAboutModel> list = new ArrayList<>();
        list.add(new ProfileAboutModel(
                "Work Experience",
                "Sr. iOS Developer",
                "IBM",
                "Oct 2017 - Jan 2018 | 1 yrs 4 mons",
                -1));
        list.add(new ProfileAboutModel(
                "Volunteering",
                "Volunteer",
                "Blood Bank",
                "Oct 2017 - Jan 2017 / Social Services",
                -1));
        list.add(new ProfileAboutModel(
                "Education",
                "Senior",
                "ABC International School",
                null,R.drawable.ic_profile_education));
        list.add(new ProfileAboutModel(
                "Club & Organizations",
                "President",
                "Amnesty International Club",
                null,
                R.drawable.ic_profile_club_organization));

        return list;
    }
}

package com.communityx.database;

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
                "Oct 2017 - Jan 2018 | 1 yrs 4 mons"));
        list.add(new ProfileAboutModel(
                "Volunteering",
                "Volunteer",
                "Blood Bank",
                "Oct 2017 - Jan 2017 / Social Services"));
        list.add(new ProfileAboutModel(
                "Education",
                "Senior",
                "ABC International School",
                null));
        list.add(new ProfileAboutModel(
                "Club & Organizations",
                "President",
                "Amnesty International Club",
                null));

        return list;
    }
}

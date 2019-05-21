package com.communityx.database.fakemodels;

import android.support.annotation.DrawableRes;

public class ProfileAboutModel {

    private String heading;
    private String title;
    private String subtitle;
    private String duration;
    @DrawableRes
    private int logo;

    public ProfileAboutModel(String heading, String title, String subtitle, String duration, int logo) {
        this.heading = heading;
        this.title = title;
        this.subtitle = subtitle;
        this.duration = duration;
        this.logo = logo;
    }

    public String getHeading() {
        return heading;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDuration() {
        return duration;
    }

    public int getLogo() {
        return logo;
    }
}

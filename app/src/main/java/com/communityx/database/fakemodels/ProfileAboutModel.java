package com.communityx.database.fakemodels;

public class ProfileAboutModel {

    private String heading;
    private String title;
    private String subtitle;
    private String duration;

    public ProfileAboutModel(String heading, String title, String subtitle, String duration) {
        this.heading = heading;
        this.title = title;
        this.subtitle = subtitle;
        this.duration = duration;
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
}

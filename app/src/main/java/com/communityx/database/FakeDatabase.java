package com.communityx.database;

public class FakeDatabase {

    private static final FakeDatabase INSTANCE = new FakeDatabase();
    private FakeMyAllFriendsDao fakeMyAllFriendsDao;
    private ProfileInfoDao profileInfoDao;

    private FakeDatabase(){
        fakeMyAllFriendsDao = new FakeMyAllFriendsDao();
        profileInfoDao = new ProfileInfoDao();
    }

    public static FakeDatabase get(){
        return INSTANCE;
    }

    public FakeMyAllFriendsDao getFakeMyAllFriendsDao() {
        return fakeMyAllFriendsDao;
    }

    public ProfileInfoDao getProfileInfoDao() {
        return profileInfoDao;
    }
}

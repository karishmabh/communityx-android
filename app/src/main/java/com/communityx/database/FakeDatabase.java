package com.communityx.database;

public class FakeDatabase {

    private static final FakeDatabase INSTANCE = new FakeDatabase();
    private FakeMyAllFriendsDao fakeMyAllFriendsDao;

    private FakeDatabase(){
        fakeMyAllFriendsDao = new FakeMyAllFriendsDao();
    }

    public static FakeDatabase get(){
        return INSTANCE;
    }

    public FakeMyAllFriendsDao getFakeMyAllFriendsDao() {
        return fakeMyAllFriendsDao;
    }
}

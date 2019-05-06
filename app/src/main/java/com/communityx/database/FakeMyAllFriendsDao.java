package com.communityx.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class FakeMyAllFriendsDao {

    private MutableLiveData<List<String>> liveData = new MutableLiveData<>();

    public LiveData<List<String>> getMyAllFriends() {
        liveData.setValue(createFriend());
        return liveData;
    }

    private List<String> createFriend() {
        List<String> list = new ArrayList<>();
        list.add("Allan Jennings");
        list.add("Amelia Burke ");
        list.add("Anthony Graham");
        list.add("Arnold");
        list.add("Bella Oliver");
        list.add("Bonnie Baker");
        list.add("Britney Spears");

        return list;
    }
}

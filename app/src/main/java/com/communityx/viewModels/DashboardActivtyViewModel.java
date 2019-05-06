package com.communityx.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.communityx.database.FakeDatabase;

import java.util.List;

public class DashboardActivtyViewModel extends ViewModel {

    public LiveData<List<String>> getFakeAllMyFriends(){
        FakeDatabase fakeDatabase = FakeDatabase.get();
        return fakeDatabase.getFakeMyAllFriendsDao().getMyAllFriends();
    }
}

package com.communityx.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.ProfileInfoAdapter;
import com.communityx.database.FakeDatabase;
import com.communityx.utils.AppConstant;

public class SeeAllAboutActivity extends BaseActivity implements AppConstant {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    private boolean isOtherProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_about);
        ButterKnife.bind(this);

        isOtherProfile = getIntent().getBooleanExtra(IS_OTHER_PROFILE, false);
        setAllInfo();
        setFloatingButtonVisibility();
    }

    private void setFloatingButtonVisibility() {
        if (isOtherProfile) {
            fabAdd.hide();
        }
    }

    private void setAllInfo() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        ProfileInfoAdapter adapter = new ProfileInfoAdapter(this, FakeDatabase.get().getProfileInfoDao().getProfileInfo());
        adapter.setOtherProfile(isOtherProfile);
        adapter.setFromDetialActivity(true);
        recyclerView.setAdapter(adapter);
    }
}

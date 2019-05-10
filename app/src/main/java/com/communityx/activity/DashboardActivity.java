package com.communityx.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.fragments.CommunityFeedFragment;
import com.communityx.fragments.MyAllFriendsFragment;
import com.communityx.utils.DialogHelper;
import com.communityx.utils.Utils;

public class DashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_community);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogHelper.showEventDialog(DashboardActivity.this,"");
            }
        },4000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navigation_friends:
                replceFragment(new MyAllFriendsFragment(), "fragment_myfriends");
                break;
            case R.id.navigation_community:
                replceFragment(new CommunityFeedFragment(), "fragment_community");
                break;
        }
        return true;
    }

    @OnClick(R.id.view_community_btn)
    void tappedCommunityFeed() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_community);
    }

    private void replceFragment(Fragment fragment, String tag){
        Utils.replaceFragment(this,fragment,false,tag);
    }
}

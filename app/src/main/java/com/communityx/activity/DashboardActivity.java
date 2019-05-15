package com.communityx.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.fragments.CommunityFeedFragment;
import com.communityx.fragments.MyAllFriendsFragment;
import com.communityx.fragments.ProfileFragment;
import com.communityx.utils.AnimationUtils;
import com.communityx.utils.DialogHelper;
import com.communityx.utils.Utils;

public class DashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    public BottomNavigationView bottomNavigationView;
    @BindView(R.id.image_feed)
    ImageView imageFeed;
    @BindString(R.string.earthquake_reported)
    String stringReporting;

    public boolean hasGoneToProfileViewImage = false;
    private boolean isBackPressClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_community);

        new Handler().postDelayed(() -> {
            if(!DashboardActivity.this.isFinishing()){
                DialogHelper.showReportingDialog(DashboardActivity.this,stringReporting);
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
                if(isBackPressClick){
                    isBackPressClick = false;
                    hasGoneToProfileViewImage = false;
                    return true;
                }
                else if(hasGoneToProfileViewImage){
                    onBackPressed();
                    return true;
                }
                AnimationUtils.rotateView(imageFeed,0,800);
                replceFragment(new CommunityFeedFragment(), "fragment_community");
                break;
            case R.id.navigation_profile:
                if(hasGoneToProfileViewImage){
                    return true;
                }
                replceFragment(new ProfileFragment(), "fragment_profile");
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

    @Override
    public void onBackPressed() {
        if(hasGoneToProfileViewImage){
            isBackPressClick = true;
            bottomNavigationView.setSelectedItemId(R.id.navigation_community);
        }
        super.onBackPressed();
    }
}

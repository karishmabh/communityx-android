package com.communityx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.adapters.CommunityFeedAdapter;
import com.communityx.adapters.ProfileInfoAdapter;
import com.communityx.database.FakeDatabase;
import com.communityx.utils.AppConstant;
import com.communityx.utils.Utils;

public class ProfileActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.recycler_post)
    RecyclerView recyclerPost;
    @BindView(R.id.view_add_headline)
    View viewAddHeadLine;
    @BindView(R.id.view_add_msg_other)
    View viewAddAndMsg;
    @BindView(R.id.edit_profile)
    ImageView editProfile;
    @BindView(R.id.text_headline)
    TextView textHeadline;
    @BindView(R.id.text_my_post)
    TextView textMyPost;
    @BindView(R.id.edit_search)
    EditText editPost;
    @BindView(R.id.recycler_about)
    RecyclerView recyclerAbout;

    @BindString(R.string.my_posts)
    String myPost;
    @BindString(R.string.posts)
    String post;

    private boolean isOtherProfile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        editPost.setHint(R.string.write_something_here);
        editPost.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

        setAboutInfo();
        setMyPost();
        showEditIcon(!isOtherProfile);
        showAddHeadlines(false && !isOtherProfile);
        setPostLabel(isOtherProfile);
        showAddAndMessageButton(isOtherProfile);
    }

    @OnClick(R.id.text_see_all)
    void tappedSeeAll() {
        Intent intent = new Intent(this,SeeAllAboutActivity.class);
        intent.putExtra(IS_OTHER_PROFILE, isOtherProfile);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_next_slide_in,R.anim.anim_next_slide_out);
    }

    private void setAboutInfo() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerAbout.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerAbout.getContext(),linearLayoutManager.getOrientation());
        recyclerAbout.addItemDecoration(dividerItemDecoration);
        ProfileInfoAdapter adapter = new ProfileInfoAdapter(this, FakeDatabase.get().getProfileInfoDao().getProfileInfo());
        adapter.setOtherProfile(isOtherProfile);
        recyclerAbout.setAdapter(adapter);
    }

    private void setMyPost() {
        recyclerPost.setLayoutManager(new LinearLayoutManager(this));
        CommunityFeedAdapter communityFeedAdapter = new CommunityFeedAdapter(this);
        communityFeedAdapter.setFromProfile(true);
        communityFeedAdapter.setOtherProfile(isOtherProfile);
        recyclerPost.setAdapter(communityFeedAdapter);
    }

    private void showEditIcon(boolean shouldShow){
        Utils.showHideView(editProfile,shouldShow);
    }

    private void showAddHeadlines(boolean shoulShow){
        Utils.showHideView(viewAddHeadLine,shoulShow);
        Utils.showHideView(textHeadline, !shoulShow);
    }

    private void showAddAndMessageButton(boolean shouldShow){
        Utils.showHideView(viewAddAndMsg,shouldShow);
    }

    private void setPostLabel(boolean isOtherProfile){
        textMyPost.setText(isOtherProfile ? post : myPost);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}

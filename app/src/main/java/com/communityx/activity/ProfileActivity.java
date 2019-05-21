package com.communityx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.CommunityFeedAdapter;
import com.communityx.adapters.ProfileInfoAdapter;
import com.communityx.database.FakeDatabase;
import com.communityx.utils.Utils;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.recycler_post)
    RecyclerView recyclerPost;
    @BindView(R.id.view_add_headline)
    View viewAddHeadLine;
   /* @BindView(R.id.view_add_about)
    View viewAddExperience;*/
    @BindView(R.id.view_add_msg_other)
    View viewAddAndMsg;
    @BindView(R.id.edit_profile)
    ImageView editProfile;
   /* @BindView(R.id.edit_education)
    ImageView editEducation;*/
  /*  @BindView(R.id.edit_club)
    ImageView editClub;
    @BindView(R.id.edit_about)
    ImageView editAbout;*/
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
        showAddExperience(true && !isOtherProfile);
        setPostLabel(isOtherProfile);
        showAddAndMessageButton(isOtherProfile);
    }

    private void setAboutInfo() {
        recyclerAbout.setLayoutManager(new LinearLayoutManager(this));
        recyclerAbout.setAdapter(new ProfileInfoAdapter(this, FakeDatabase.get().getProfileInfoDao().getProfileInfo()));
    }

    private void setMyPost() {
        recyclerPost.setLayoutManager(new LinearLayoutManager(this));
        CommunityFeedAdapter communityFeedAdapter = new CommunityFeedAdapter(this);
        communityFeedAdapter.setFromProfile(true);
        recyclerPost.setAdapter(communityFeedAdapter);
    }

    private void showEditIcon(boolean shouldShow){
        Utils.showHideView(editProfile,shouldShow);
       // Utils.showHideView(editEducation,shouldShow);
        //Utils.showHideView(editClub,shouldShow);
        //Utils.showHideView(editAbout,shouldShow);
    }

    private void showAddExperience(boolean shoulShow){
       // Utils.showHideView(viewAddExperience,shoulShow);
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

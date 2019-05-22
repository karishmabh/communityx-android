package com.communityx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.R.id;
import com.communityx.adapters.SignUpPagerAdapter;
import com.communityx.custom_views.CustomViewPager;
import com.communityx.fragments.*;
import com.communityx.utils.AppConstant;
import com.communityx.utils.Utils;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SignUpStudentInfoActivity extends AppCompatActivity implements AppConstant {

    @BindView(id.text_subtitle)
    TextView textSubtitle;
    @BindView(id.view_pager)
    CustomViewPager viewPager;
    @BindView(id.dots_indicator)
    DotsIndicator dotsIndicator;
    @BindView(id.button_continue)
    public Button buttonContinue;

    private SignUpPagerAdapter pagerAdapter;
    private String selectedCategory;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student_info);
        ButterKnife.bind(this);

        initActivity();
        setUpViewPager();
    }

    private void initActivity() {
        getIntentData();
        textSubtitle.setText("Build your social impact identity on CommunityX.");
        buttonContinue.setTag(true);
        buttonContinue.setBackgroundResource(R.drawable.button_active);
    }

    private void getIntentData() {
        selectedCategory = getIntent().getAction();
    }

    private void setUpViewPager() {
        pagerAdapter = new SignUpPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setFragments(getFragments(selectedCategory));
        viewPager.setAdapter(pagerAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.left);
        viewPager.setmSwipeDirectionListener(new CustomViewPager.SwipeDirectionListener() {
            @Override
            public void onSwipe(CustomViewPager.SwipeDirection direction) {

            }

            @Override
            public void onPageChange(int position) {
                if(!selectedCategory.equals(ACTION_SIGN_UP_STUDENT)) {
                    return;
                }
                enableButton(pagerAdapter.isButtonEnabled(position));
                buttonContinue.setText(position == pagerAdapter.getTotalItems()-1 ? R.string.submit : R.string.continue_button);
            }
        });
        dotsIndicator.setViewPager(viewPager);
        dotsIndicator.setDotsClickable(false);
    }

    @OnClick(id.text_login)
    void goToLogin() {
        SignUpStudentInfoActivity.this.startActivity((new Intent(SignUpStudentInfoActivity.this, LoginActivity.class))
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        SignUpStudentInfoActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
        SignUpStudentInfoActivity.this.finish();
    }

    @OnClick(id.button_continue)
    void tappedContinue() {
        if(viewPager.getCurrentItem() == pagerAdapter.getTotalItems()-1){
            sendToActivity();
            return;
        }

        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,true);
        if(!selectedCategory.equals(ACTION_SIGN_UP_STUDENT)){
            return;
        }
        boolean isEnabled = pagerAdapter.isButtonEnabled(viewPager.getCurrentItem());
        enableButton(isEnabled);
    }

    public void enableButton(boolean enable){
        Utils.enableButton(buttonContinue,enable);
    }

    private void sendToActivity() {
        startActivity(new Intent(this, ConnectAlliesActivity.class));
    }

    private List<Fragment> getFragments(String selectedCategory) {
        List<Fragment> fragments = new ArrayList<>();
        if (selectedCategory.equals(ACTION_SIGN_UP_STUDENT)) {
            fragments.add(new SignUpStudentInfoFragment());
            fragments.add(new SignUpSchoolCollegeFragment());
            fragments.add(new SignUpRoleFragment());
            fragments.add(new SignUpMemberOfClub());
        } else if (selectedCategory.equals(ACTION_SIGN_UP_PROFESSIONAL)) {
            fragments.add(new SignUpStudentInfoFragment());
            fragments.add(new SignUpProfessional());
            fragments.add(new SignUpMemberOfClub());
        } else if(selectedCategory.equals(ACTION_SIGN_UP_ORGANIZATION)) {
            fragments.add(new SignUpOrganizationFragment());
        }

        fragments.add(new SignUpSelectInterest());
        return fragments;
    }
}
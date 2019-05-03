package com.communityx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import org.jetbrains.annotations.Nullable;

public class SignUpStudentInfoActivity extends AppCompatActivity{

    @BindView(id.text_subtitle)
    TextView textSubtitle;
    @BindView(id.view_pager)
    CustomViewPager viewPager;
    @BindView(id.dots_indicator)
    DotsIndicator dotsIndicator;
    @BindView(id.button_continue)
    Button buttonContinue;

    private SignUpPagerAdapter pagerAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student_info);
        ButterKnife.bind(this);

        textSubtitle.setText("Build your social impact identity on CommunityX.");
        buttonContinue.setTag(true);
        buttonContinue.setBackgroundResource(R.drawable.button_active);
        setUpViewPager();
    }

    private void setUpViewPager() {
        pagerAdapter = new SignUpPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.left);
        viewPager.setmSwipeDirectionListener(new CustomViewPager.SwipeDirectionListener() {
            @Override
            public void onSwipe(CustomViewPager.SwipeDirection direction) {

            }

            @Override
            public void onPageChange(int position) {
                enableButton(pagerAdapter.isButtonEnabled(position));
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
    void tappedContinue(){
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,true);
        boolean isEnabled = pagerAdapter.isButtonEnabled(viewPager.getCurrentItem());
        enableButton(isEnabled);
    }

    public void enableButton(boolean enable){
        buttonContinue.setAlpha(enable ? 1.0f : 0.5f);
        buttonContinue.setClickable(enable);
    }


}
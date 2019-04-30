package com.communityx.activity;

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
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import org.jetbrains.annotations.Nullable;

public class SignUpStudentInfoActivity extends AppCompatActivity {

    @BindView(id.text_subtitle)
    TextView textSubtitle;
    @BindView(id.view_pager)
    ViewPager viewPager;
    @BindView(id.dots_indicator)
    DotsIndicator dotsIndicator;
    @BindView(id.button_continue)
    public Button buttonContinue;

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
        viewPager.setOnTouchListener((v, event) -> true);
        dotsIndicator.setViewPager(viewPager);
    }

    @OnClick(id.button_continue)
    void tappedContinue(){
        boolean isActive = (boolean) buttonContinue.getTag();
        if (!isActive){
            return;
        }
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,true);
        boolean isEnabled = pagerAdapter.isButtonEnabled(viewPager.getCurrentItem());
        buttonContinue.setTag(isEnabled);
        buttonContinue.setBackgroundResource(isEnabled ? R.drawable.button_active : R.drawable.button_inactive);

    }


}

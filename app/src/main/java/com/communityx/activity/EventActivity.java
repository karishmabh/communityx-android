package com.communityx.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.EventPagerAdapter;
import com.communityx.custom_views.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    private EventPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);

        setToolBar(this,getString(R.string.events),true);
        setUpViewPager();
    }

    private void setUpViewPager() {
        pagerAdapter = new EventPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setTitles(getTitles());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        updateTabText();
    }

    private List<String> getTitles(){
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.my_hosted_events));
        list.add(getString(R.string.i_am_interested_in));
        return list;
    }

    private void updateTabText(){
        for(int i=0;i<tabLayout.getTabCount();i++){
            TextView tv = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(i)).getChildAt(1));
            String title = pagerAdapter.getPageTitle(i).toString();
            String updatedText = "";
            switch (i){
                case 0 :
                    updatedText = title + " 03";
                    break;
                case 1 :
                    updatedText = title + " 08";
                    break;
            }
            Spannable spannable = createSpannable(updatedText,title);
            if(spannable != null) tv.setText(spannable, TextView.BufferType.SPANNABLE);
        }
    }

    private Spannable createSpannable(String updatedText, String title){
        Spannable spannable = new SpannableString(updatedText);
        spannable.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.colorPrimary)),
                title.length(),updatedText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}

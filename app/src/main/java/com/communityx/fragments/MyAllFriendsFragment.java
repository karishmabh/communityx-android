package com.communityx.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.FriendsPagerAdapter;
import com.communityx.viewModels.DashboardActivtyViewModel;

import java.util.Objects;

public class MyAllFriendsFragment extends Fragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private DashboardActivtyViewModel viewModel;
    private FriendsPagerAdapter pagerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_my_all_friends, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DashboardActivtyViewModel.class);

        pagerAdapter = new FriendsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
        updateTabText(0);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        updateTabText(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    private void updateTabText(int currentPos){
        for(int i=0;i<tabLayout.getTabCount();i++){
            TextView tv = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(i)).getChildAt(1));
            String title = pagerAdapter.getPageTitle(i).toString();
            String updatedText = "";
            switch (i){
                case 0 :
                    updatedText = title + " " + viewModel.getFakeAllMyFriends().getValue().size();
                    break;
                case 1 :
                    updatedText = title + " " + 12;
                    break;
                case 2 :
                    updatedText = title + " " + 54;
                    break;
            }
            Spannable spannable = createSpannable(updatedText,title, i == currentPos);
            if(spannable != null) tv.setText(spannable, TextView.BufferType.SPANNABLE);
        }
    }

    private Spannable createSpannable(String updatedText, String title, boolean isActive){
        Context context = getContext();
        if (context == null) return null;
        Spannable spannable = new SpannableString(updatedText);
        spannable.setSpan(new ForegroundColorSpan(
                        isActive ? context.getResources().getColor(R.color.colorLightGrey): context.getResources().getColor(R.color.colorPrimary)) ,
                title.length(),updatedText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}

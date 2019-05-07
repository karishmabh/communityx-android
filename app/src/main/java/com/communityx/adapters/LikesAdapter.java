package com.communityx.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.EventHolder> {
    private ArrayList<String> mLikesList;
    private Activity mActvity;
    private LayoutInflater mLayoutInflater;

    public LikesAdapter(ArrayList<String> likesList, Activity activity) {
        this.mLikesList = likesList;
        this.mActvity = activity;
        this.mLayoutInflater = LayoutInflater.from(activity);
    }
    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_connect_allies, viewGroup, false);
        return new LikesAdapter.EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder eventHolder, int i) {
        List<String> list = Arrays.asList(new String[] {"School Safety", "Immigration", "LGBTQ+", "Mental Health", "Prisom Reform"});
        setFLexLayout(eventHolder.flexboxLayout, list);
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.flex_layout_allies)
        FlexboxLayout flexboxLayout;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setFLexLayout(FlexboxLayout fLexLayout, List<String> interest) {
        fLexLayout.removeAllViews();
        for (String civilRight : interest) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(mActvity).inflate(R.layout.item_interest, null);
            checkBox.setText(civilRight);
            checkBox.performClick();
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                checkBox.setBackgroundResource(isChecked ? R.drawable.bg_interest_active : R.drawable.bg_interest_inactive);
            });

            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            fLexLayout.addView(checkBox, lp);
        }
    }
}

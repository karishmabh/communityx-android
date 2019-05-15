package com.communityx.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MessageHolder> {
    private ArrayList<String> mUsersList;
    private Activity mActvity;
    private LayoutInflater mLayoutInflater;
    private String mLetter;

    public GroupAdapter(ArrayList<String> mUsersList, Activity activity) {
        this.mUsersList = mUsersList;
        this.mActvity = activity;
        this.mLayoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_user_group, viewGroup, false);
        return new GroupAdapter.MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder messageHolder, int i) {
        String name = mUsersList.get(i);
        if (TextUtils.isEmpty(mLetter)) {
            mLetter = String.valueOf(name.charAt(0));
            messageHolder.textHeader.setVisibility(View.VISIBLE);
            messageHolder.textHeader.setText(mLetter);
        } else {
            String letter = String.valueOf(name.charAt(0));
            if (!mLetter.equalsIgnoreCase(letter)) {
                mLetter = String.valueOf(name.charAt(0));
                messageHolder.textHeader.setVisibility(View.VISIBLE);
                messageHolder.textHeader.setText(mLetter);
            } else {
                messageHolder.textHeader.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_header)
        TextView textHeader;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
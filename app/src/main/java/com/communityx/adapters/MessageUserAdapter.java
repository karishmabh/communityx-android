package com.communityx.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.activity.MessageActivity;

import java.util.ArrayList;

public class MessageUserAdapter extends RecyclerView.Adapter<MessageUserAdapter.MessageHolder> {
    private ArrayList<String> mUsersList;
    private Activity mActvity;
    private LayoutInflater mLayoutInflater;

    public MessageUserAdapter(ArrayList<String> mUsersList, Activity activity) {
        this.mUsersList = mUsersList;
        this.mActvity = activity;
        this.mLayoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_message_recipient, viewGroup, false);
        return new MessageUserAdapter.MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder messageHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActvity.startActivity(new Intent(mActvity, MessageActivity.class));
                }
            });
        }
    }
}
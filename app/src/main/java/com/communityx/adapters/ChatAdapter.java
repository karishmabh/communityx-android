package com.communityx.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.communityx.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_DATE = 0;
    private final int VIEW_TYPE_SENDER = 1;
    private final int VIEW_TYPE_RECEIVER = 2;

    private Activity activity;
    private ArrayList<String> mChatList;

    public ChatAdapter(Activity activity, ArrayList<String> mChatList) {
        this.activity = activity;
        this.mChatList = mChatList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATE) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_chat_date, parent, false);
            return new ChatAdapter.DateViewHolder(view);
        } else if (viewType == VIEW_TYPE_SENDER) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_sender, parent, false);
            return new ChatAdapter.SenderViewHolder(view);
        } else if (viewType == VIEW_TYPE_RECEIVER) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_receiver, parent, false);
            return new ChatAdapter.ReceiverViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 5) {
            return VIEW_TYPE_RECEIVER;
        } else if (position == 5) {
            return VIEW_TYPE_DATE;
        } else {
            return VIEW_TYPE_SENDER;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ReceiverViewHolder extends RecyclerView.ViewHolder {

        public ReceiverViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder {

        public SenderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DateViewHolder extends RecyclerView.ViewHolder {

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
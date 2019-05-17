package com.communityx.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.utils.Utils;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_DATE = 0;
    private final int VIEW_TYPE_SENDER = 1;
    private final int VIEW_TYPE_RECEIVER = 2;
    private final int VIEW_TYPE_MAP = 3;

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
        } else if (viewType == VIEW_TYPE_MAP) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_map_added, parent, false);
            return new ChatAdapter.MapViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MapViewHolder) {
            ((MapViewHolder) viewHolder).bindData();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mChatList.get(position).equalsIgnoreCase("r")) {
            return VIEW_TYPE_RECEIVER;
        } else if (mChatList.get(position).equalsIgnoreCase("d")) {
            return VIEW_TYPE_DATE;
        } else if (mChatList.get(position).equalsIgnoreCase("s")) {
            return VIEW_TYPE_SENDER;
        } else {
            return VIEW_TYPE_MAP;
        }
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
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

    class MapViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_map)
        ImageView imageMap;

        public MapViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData() {
            imageMap.setImageBitmap(Utils.convertToBitmap(activity, mChatList.get(getAdapterPosition())));
        }
    }
}
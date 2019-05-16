package com.communityx.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.communityx.R;
import com.communityx.activity.EventActivity;
import com.communityx.activity.EventDetailActivity;

import java.util.Objects;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;

    public EventAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EventViewHolder(mInflater.inflate(R.layout.item_event, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                mContext.startActivity(new Intent(mContext, EventDetailActivity.class));
                Activity activity = (Activity) mContext;
                activity.overridePendingTransition(R.anim.anim_next_slide_in,R.anim.anim_next_slide_out);
            });
        }
    }
}

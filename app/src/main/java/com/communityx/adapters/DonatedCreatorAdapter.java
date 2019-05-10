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

public class DonatedCreatorAdapter extends RecyclerView.Adapter<DonatedCreatorAdapter.EventViewHolder> {
    private ArrayList<String> mDonatedList;
    private Activity mActivity;
    private LayoutInflater mLayoutInflator;

    public DonatedCreatorAdapter(ArrayList<String> donatedList, Activity activity) {
        this.mDonatedList = donatedList;
        this.mActivity = activity;
        this.mLayoutInflator = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflator.inflate(R.layout.item_donated, viewGroup, false);
        return new DonatedCreatorAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.communityx.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;

public class NotificationAlertAdapter extends RecyclerView.Adapter {

    private final int ALERT_NORMAL = 0;
    private final int ALERT_BIG = 1;

    private Context mContext;
    private LayoutInflater inflater;

    public NotificationAlertAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == ALERT_BIG){
            return new ExpandedAlertViewHoleder(inflater.inflate(R.layout.item_notification_expanded,viewGroup,false));
        }
        return new NormalAlertViewHolder(inflater.inflate(R.layout.item_message_recipient,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        NormalAlertViewHolder holder = (NormalAlertViewHolder) viewHolder;
        if(i == 0){
            holder.setBadgeCount("2");
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2){
            return ALERT_BIG;
        }
        return ALERT_NORMAL;
    }

    class NormalAlertViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.badge)
        TextView textBadge;
        public NormalAlertViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            textBadge.setVisibility(View.GONE);
        }

        public void setBadgeCount(String count){
            textBadge.setVisibility(View.VISIBLE);
            textBadge.setText(count);
        }
    }

    class ExpandedAlertViewHoleder extends NormalAlertViewHolder{

        @BindView(R.id.view_divider_one)
        View divider;

        public ExpandedAlertViewHoleder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            divider.setVisibility(View.GONE);
        }
    }
}

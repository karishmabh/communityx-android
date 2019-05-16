package com.communityx.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.activity.CreateGroupActivity;

import java.util.ArrayList;

public class SelectedMembersAdapter extends RecyclerView.Adapter<SelectedMembersAdapter.MessageHolder> {
    private ArrayList<String> mUsersList;
    private Activity mActvity;
    private LayoutInflater mLayoutInflater;
    private IUserRemoved iUserRemoved;

    public SelectedMembersAdapter(ArrayList<String> mUsersList, Activity activity, IUserRemoved iUserRemoved) {
        this.mUsersList = mUsersList;
        this.mActvity = activity;
        this.iUserRemoved = iUserRemoved;
        this.mLayoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_added_user, viewGroup, false);
        return new SelectedMembersAdapter.MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder messageHolder, int i) {
        messageHolder.setData(i);
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_remove)
        ImageView imageRemove;
        @BindView(R.id.text_name)
        TextView textName;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            if (mActvity instanceof CreateGroupActivity) {
                imageRemove.setVisibility(View.GONE);
            } else {
                imageRemove.setVisibility(View.VISIBLE);
            }

            textName.setText(mUsersList.get(position));
            imageRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iUserRemoved.onUserRemoved(mUsersList.get(position));
                    mUsersList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface IUserRemoved {
        void onUserRemoved(String userName);
    }
}


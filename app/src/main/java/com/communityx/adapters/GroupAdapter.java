package com.communityx.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MessageHolder> {
    private ArrayList<String> mUsersList;
    private Activity mActvity;
    private LayoutInflater mLayoutInflater;
    private String mLetter;
    private Set<String> mSelectedUsers = new HashSet<>();
    private IUsersSelected iUsersSelected;

    public GroupAdapter(ArrayList<String> mUsersList, Activity activity, IUsersSelected iUsersSelected) {
        this.mUsersList = mUsersList;
        this.mActvity = activity;
        this.iUsersSelected = iUsersSelected;
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

        messageHolder.textUserName.setText(name);
        messageHolder.bindData();
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_header)
        TextView textHeader;
        @BindView(R.id.text_user_name)
        TextView textUserName;
        @BindView(R.id.image_tick)
        ImageView imageTick;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isSelected = checkSelectedUsers(mUsersList.get(getAdapterPosition()));
                    if (isSelected) {
                        mSelectedUsers.remove(mUsersList.get(getAdapterPosition()));
                        imageTick.setVisibility(View.GONE);
                    } else {
                        mSelectedUsers.add(mUsersList.get(getAdapterPosition()));
                        imageTick.setVisibility(View.VISIBLE);
                    }

                    iUsersSelected.updatedUsersList(mSelectedUsers);
                }
            });
        }


        public void bindData() {
            if (checkSelectedUsers(mUsersList.get(getAdapterPosition()))) {
                imageTick.setVisibility(View.VISIBLE);
            } else {
                imageTick.setVisibility(View.GONE);
            }
        }
    }

    public void uncheckUser(String name, ImageView imageNext) {
        if (checkSelectedUsers(name)) {
            mSelectedUsers.remove(name);
        }

        if (mSelectedUsers.size() == 0) imageNext.setVisibility(View.GONE);

        notifyDataSetChanged();
    }

    public Set<String> getSelectedUsersList() {
        return mSelectedUsers;
    }

    private boolean checkSelectedUsers(String givenName) {
        for (String name: mSelectedUsers) {
            if (name.equals(givenName)) {
                return true;
            }
        }
        return false;
    }

    public interface IUsersSelected {
        void updatedUsersList(Set<String> mSelectedUsers);
    }
}
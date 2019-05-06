package com.communityx.adapters;

import android.content.Context;
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

import java.util.List;

public class MyAllFriendsAdapter extends RecyclerView.Adapter<MyAllFriendsAdapter.ViewHolder> {

    private Context mContext;
    private List<String> friendList;
    private LayoutInflater inflater;

    public MyAllFriendsAdapter(Context context, List<String> friendList) {
        this.mContext = context;
        this.friendList = friendList;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.item_my_all_friend, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(friendList.get(i));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    private String getFirstLetter(String name) {
        return name.substring(0, 1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_user)
        ImageView imageUser;
        @BindView(R.id.text_name)
        TextView textName;
        @BindView(R.id.text_location)
        TextView textLocation;
        @BindView(R.id.image_message)
        ImageView imageMesage;
        @BindView(R.id.image_option)
        ImageView imageOption;
        @BindView(R.id.text_letter)
        TextView textLetter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(String name) {
            textName.setText(name);

            setDividerText(name);
        }

        private void setDividerText(String name) {
            textLetter.setText(getFirstLetter(name));

            String prevStr = null;
            if (getAdapterPosition() > 0 && friendList.size() > getAdapterPosition()) {
                prevStr = getFirstLetter(friendList.get(getAdapterPosition() - 1));
            }
            if (prevStr == null) {
                textLetter.setVisibility(View.VISIBLE);
            } else if (prevStr.equals(getFirstLetter(name)) && getAdapterPosition() != 0) {
                textLetter.setVisibility(View.GONE);
            } else {
                textLetter.setVisibility(View.VISIBLE);
            }
        }
    }
}

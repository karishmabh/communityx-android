package com.communityx.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.communityx.R;
import com.communityx.database.fakemodels.ProfileAboutModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileInfoAdapter extends RecyclerView.Adapter<ProfileInfoAdapter.ViewHolder> {

    private Context mContext;
    private List<ProfileAboutModel> list;
    private LayoutInflater mInflater;
    private boolean isOtherProfile = false;

    public ProfileInfoAdapter(Context mContext, List<ProfileAboutModel> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOtherProfile(boolean otherProfile) {
        isOtherProfile = otherProfile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.item_profile_info,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProfileAboutModel model = list.get(i);
        viewHolder.bindData(model);
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_heading)
        TextView textHeading;
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_subtitle)
        TextView textSibTitle;
        @BindView(R.id.text_duration)
        TextView textDuration;
        @BindView(R.id.image_logo)
        ImageView imageLogo;
        @BindView(R.id.image_edit)
        ImageView imageEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            imageEdit.setVisibility(isOtherProfile ? View.GONE : View.VISIBLE);
        }

        public void bindData(ProfileAboutModel model){
            textHeading.setText(model.getHeading());
            textTitle.setText(model.getTitle());
            textSibTitle.setText(model.getSubtitle());
            if(model.getDuration() != null){
                textDuration.setText(model.getDuration());
            }
            textDuration.setVisibility(model.getDuration() != null ? View.VISIBLE : View.GONE);
            imageLogo.setImageResource(model.getLogo() != -1 ? model.getLogo() : R.drawable.profile_placeholder);
        }
    }
}

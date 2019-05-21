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
import com.communityx.database.fakemodels.ProfileAboutModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileInfoAdapter extends RecyclerView.Adapter<ProfileInfoAdapter.ViewHolder> {

    final int NORMAL_ITEM = 0;
    final int EXPANDED_ITEM = 1;

    private Context mContext;
    private List<ProfileAboutModel> list;
    private LayoutInflater mInflater;
    private boolean isOtherProfile = false;
    private boolean fromDetialActivity =false;

    public ProfileInfoAdapter(Context mContext, List<ProfileAboutModel> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOtherProfile(boolean otherProfile) {
        isOtherProfile = otherProfile;
    }

    public void setFromDetialActivity(boolean fromDetialActivity) {
        this.fromDetialActivity = fromDetialActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == EXPANDED_ITEM){
            return new ExpandedViewHolder(mInflater.inflate(R.layout.item_profile_info_expanded,viewGroup,false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.item_profile_info,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProfileAboutModel model = list.get(i);
        viewHolder.bindData(model);
    }

    @Override
    public int getItemCount() {
        return fromDetialActivity ? list.size() : 4;
    }

    @Override
    public int getItemViewType(int position) {
       return position == 0 && fromDetialActivity ? EXPANDED_ITEM : NORMAL_ITEM;
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

        private List<String> addedAboutHeading = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            validateEditIconVisibility();
        }

        private void validateEditIconVisibility() {
            imageEdit.setVisibility(isOtherProfile ? View.GONE : View.VISIBLE);
        }

        public void bindData(ProfileAboutModel model){
            if (!addedAboutHeading.contains(model.getHeading())) {
                textHeading.setVisibility(View.VISIBLE);
                textHeading.setText(model.getHeading());
                addedAboutHeading.add(model.getHeading());
                validateEditIconVisibility();
            } else {
                textHeading.setVisibility(View.GONE);
                imageEdit.setVisibility(View.GONE);
            }
            textTitle.setText(model.getTitle());
            textSibTitle.setText(model.getSubtitle());
            if(model.getDuration() != null){
                textDuration.setText(model.getDuration());
            }
            textDuration.setVisibility(model.getDuration() != null ? View.VISIBLE : View.GONE);
            imageLogo.setImageResource(model.getLogo() != -1 ? model.getLogo() : R.drawable.profile_placeholder);
        }
    }

    class ExpandedViewHolder extends ViewHolder {

        public ExpandedViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(ProfileAboutModel model) {
            super.bindData(model);
        }
    }
}

package com.communityx.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.*;
import com.communityx.R;
import com.communityx.activity.CrowdfundingDetailActivity;
import com.communityx.activity.EventDetailActivity;
import com.communityx.activity.LikesActivity;
import com.communityx.activity.PraiseActivity;
import com.google.android.flexbox.FlexboxLayout;

public class CommunityFeedAdapter extends RecyclerView.Adapter {

    private final int POST_FEED = 0;
    private final int POST_EVENT_FEED = 1;
    private final int POST_CROWFUNDING_FEED = 2;
    private final int POST_SHARE_FEED = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private boolean fromProfile;

    public CommunityFeedAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setFromProfile(boolean fromProfile) {
        this.fromProfile = fromProfile;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == POST_EVENT_FEED) {
            return new ViewHolderEventFeed(inflater.inflate(R.layout.item_event_feed, viewGroup, false));
        } else if (i == POST_CROWFUNDING_FEED) {
            return new ViewHolderCrowdfundingFeed(inflater.inflate(R.layout.item_crowdfunding_feed, viewGroup, false));
        } else if (i == POST_SHARE_FEED) {
            return new ViewHolderShareFeed(inflater.inflate(R.layout.item_share_post_feed, viewGroup, false));
        }
        return new ViewHolderPostFeed(inflater.inflate(R.layout.item_community_feeds, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolderPostFeed) {
            ViewHolderPostFeed holder = (ViewHolderPostFeed) viewHolder;
            holder.viewPostMedia.setVisibility(i % 3 == 0 && i != 0 ? View.VISIBLE : View.GONE);
        }
        else if (viewHolder instanceof ViewHolderShareFeed){
            ViewHolderShareFeed holder = (ViewHolderShareFeed) viewHolder;
            holder.addDummyShare(i);
        }
    }

    @Override
    public int getItemCount() {
        return 14;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 4) return POST_EVENT_FEED;
        else if (position == 5) return POST_CROWFUNDING_FEED;
        else if (fromProfile && (position == 2 || position == 6)) return POST_SHARE_FEED;
        else return POST_FEED;
    }

    class BaseFeedViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.flexboxLayout2)
        FlexboxLayout flexboxLayout;

        public BaseFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.image_comment, R.id.text_comment})
        void praisedClicked() {
            mContext.startActivity(new Intent(mContext, PraiseActivity.class));
        }

        @OnClick({R.id.image_like, R.id.text_like})
        void tappedLike() {
            mContext.startActivity(new Intent(mContext, LikesActivity.class));
        }
    }

    class ViewHolderPostFeed extends BaseFeedViewHolder {

        @BindView(R.id.view_post_media)
        View viewPostMedia;
        @BindView(R.id.text_post)
        TextView textPost;

        public ViewHolderPostFeed(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderEventFeed extends BaseFeedViewHolder {

        @BindView(R.id.button_interested)
        Button buttonInterested;

        public ViewHolderEventFeed(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            buttonInterested.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, EventDetailActivity.class)));
        }
    }

    class ViewHolderCrowdfundingFeed extends BaseFeedViewHolder {

        @BindView((R.id.edit_amount))
        TextInputEditText editAmount;
        @BindView(R.id.radioGroup)
        RadioGroup radioGroupDonation;
        @BindView(R.id.textinput_amount)
        TextInputLayout inputLayoutAmount;
        @BindView(R.id.text_other_amount)
        RadioButton radioOtherAmount;
        @BindView(R.id.text_dollor_one)
        RadioButton radioDollorOne;
        @BindView(R.id.text_dollor_two)
        RadioButton radioDollorTwo;
        @BindView(R.id.button_pay)
        Button buttonPay;

        public ViewHolderCrowdfundingFeed(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            radioListener();
        }

        @OnTextChanged(R.id.edit_amount)
        void amountTypng(CharSequence s) {
            if (s.length() < 1) {
                editAmount.setText("$");
                editAmount.setSelection(1);
            }
        }

        private void radioListener() {
            itemView.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, CrowdfundingDetailActivity.class)));

            radioDollorOne.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) buttonPay.setBackgroundResource(R.drawable.button_active);
                radioDollorOne.setBackgroundResource(isChecked ? R.drawable.bg_stroke_active : R.drawable.bg_stroke_grey);
            });

            radioDollorTwo.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) buttonPay.setBackgroundResource(R.drawable.button_active);
                radioDollorTwo.setBackgroundResource(isChecked ? R.drawable.bg_stroke_active : R.drawable.bg_stroke_grey);
            });

            radioOtherAmount.setOnCheckedChangeListener((buttonView, isChecked) -> {
                inputLayoutAmount.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                if (isChecked) buttonPay.setBackgroundResource(R.drawable.button_active);
                radioOtherAmount.setBackgroundResource(isChecked ? R.drawable.bg_stroke_active : R.drawable.bg_stroke_grey);
            });
        }
    }

    class ViewHolderShareFeed extends BaseFeedViewHolder {

        @BindView(R.id.container_share_post)
        FrameLayout containerSharePost;

        public ViewHolderShareFeed(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void addDummyShare(int position) {
            View view;
            if (position == 2) {
                view = inflater.inflate(R.layout.item_event_feed, null);
                view.findViewById(R.id.text_post).setVisibility(View.GONE);
                view.findViewById(R.id.button_interested_share).setVisibility(View.VISIBLE);
                view.findViewById(R.id.view_like_share_comment).setVisibility(View.GONE);
                view.findViewById(R.id.text_miles).setVisibility(View.GONE);
                containerSharePost.addView(view);
            }
        }
    }
}
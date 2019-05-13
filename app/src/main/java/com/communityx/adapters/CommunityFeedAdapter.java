package com.communityx.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.communityx.R;
import com.communityx.activity.CrowdfundingDetailActivity;
import com.communityx.activity.LikesActivity;
import com.communityx.activity.PraiseActivity;
import com.google.android.flexbox.FlexboxLayout;

public class CommunityFeedAdapter extends RecyclerView.Adapter {

    private final int POST_FEED = 0;
    private final int POST_EVENT_FEED = 1;
    private final int POST_CROWFUNDING_FEED = 2;

    private Context mContext;
    private LayoutInflater inflater;

    public CommunityFeedAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == POST_EVENT_FEED) {
            return new ViewHolderEventFeed(inflater.inflate(R.layout.item_event_feed, viewGroup, false));
        }
        return new ViewHolderPostFeed(inflater.inflate(R.layout.item_community_feeds, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolderPostFeed) {
            ViewHolderPostFeed holder = (ViewHolderPostFeed) viewHolder;
            holder.viewPostMedia.setVisibility(i % 3 == 0 && i != 0 ? View.VISIBLE : View.GONE);
            if (i == 5) {
                holder.viewDontation.setVisibility(View.VISIBLE);
                holder.textPost.setVisibility(View.GONE);
                holder.itemView.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, CrowdfundingDetailActivity.class)));
            }
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 4) {
            return POST_EVENT_FEED;
        }
        return POST_FEED;
    }

    class ViewHolderPostFeed extends RecyclerView.ViewHolder {
        @BindView(R.id.view_post_media)
        View viewPostMedia;
        @BindView(R.id.view_donation)
        View viewDontation;
        @BindView(R.id.text_post)
        TextView textPost;
        @BindView(R.id.flexboxLayout2)
        FlexboxLayout flexboxLayout;
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

        public ViewHolderPostFeed(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            radioListener();
        }

        @OnClick({R.id.image_comment,R.id.text_comment})
        void praisedClicked(){
            mContext.startActivity(new Intent(mContext, PraiseActivity.class));
        }

        @OnClick({R.id.image_like,R.id.text_like})
        void tappedLike(){
            mContext.startActivity(new Intent(mContext, LikesActivity.class));
        }

        @OnTextChanged(R.id.edit_amount)
        void amountTypng(CharSequence s){
            if(s.length() < 1){
                editAmount.setText("$");
                editAmount.setSelection(1);
            }
        }

        private void radioListener() {
            radioDollorOne.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked) buttonPay.setBackgroundResource(R.drawable.button_active);
                radioDollorOne.setBackgroundResource(isChecked ? R.drawable.bg_stroke_active : R.drawable.bg_stroke_grey);
            });

            radioDollorTwo.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked) buttonPay.setBackgroundResource(R.drawable.button_active);
                radioDollorTwo.setBackgroundResource(isChecked ? R.drawable.bg_stroke_active : R.drawable.bg_stroke_grey);
            });

            radioOtherAmount.setOnCheckedChangeListener((buttonView, isChecked) -> {
                inputLayoutAmount.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                if(isChecked) buttonPay.setBackgroundResource(R.drawable.button_active);
                radioOtherAmount.setBackgroundResource(isChecked ? R.drawable.bg_stroke_active : R.drawable.bg_stroke_grey);
            });
        }
    }

    class ViewHolderEventFeed extends RecyclerView.ViewHolder {

        public ViewHolderEventFeed(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

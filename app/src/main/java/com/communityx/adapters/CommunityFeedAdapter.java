package com.communityx.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.activity.PraiseActivity;

public class CommunityFeedAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater inflater;

    public CommunityFeedAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.item_community_feeds,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.viewPostMedia.setVisibility(i % 3 == 0 && i != 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.view_post_media)
        View viewPostMedia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick({R.id.image_comment,R.id.text_comment})
        void praisedClicked(){
            mContext.startActivity(new Intent(mContext, PraiseActivity.class));
        }

        @OnClick({R.id.image_like,R.id.text_like})
        void tappedLike(){
            mContext.startActivity(new Intent(mContext,LikesActivity.class));
        }
    }
}

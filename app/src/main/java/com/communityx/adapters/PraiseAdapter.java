package com.communityx.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.utils.AnimationUtils;
import com.communityx.utils.Utils;

public class PraiseAdapter extends RecyclerView.Adapter<PraiseAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;

    public PraiseAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.item_praise,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i % 2 == 0 && i != 0) viewHolder.showViewAllReplies(String.valueOf(i +1), true);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.underline)
        View viewUnderline;
        @BindView(R.id.text_all_replies)
        TextView textAllReplies;
        @BindView(R.id.image_like)
        ImageView imageLike;

        @BindString(R.string.view_replies)
        String viewReplies;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            showViewAllReplies("",false);
        }

        void showViewAllReplies(@NonNull String repliesCount, boolean shouldShow) {
            Utils.showHideView(viewUnderline, shouldShow);
            Utils.showHideView(textAllReplies, shouldShow);
            if (shouldShow) textAllReplies.setText(viewReplies.concat(" (").concat(repliesCount).concat(")"));
        }

        boolean isLike = true;
        @OnClick(R.id.image_like)
        void likeTapped(){
            imageLike.setImageResource(isLike ? R.drawable.ic_my_community_like_deselect : R.drawable.ic_my_community_like_select);
            isLike = !isLike;
            AnimationUtils.pulse(imageLike,1,300);
        }
    }
}

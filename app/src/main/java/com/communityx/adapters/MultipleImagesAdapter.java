package com.communityx.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.communityx.R;

import java.util.ArrayList;

public class MultipleImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_TEXT = 1;
    private Activity activity;
    private ArrayList<String> mImagesList;

    public MultipleImagesAdapter(Activity activity, ArrayList<String> mImagesList) {
        this.activity = activity;
        this.mImagesList = mImagesList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_circular_image, parent, false);
            return new MultipleImagesAdapter.ImageViewHolder(view);
        } else if (viewType == VIEW_TYPE_TEXT) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_text_center, parent, false);
            return new MultipleImagesAdapter.TextViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mImagesList.size() - 1) {
            return VIEW_TYPE_TEXT;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mImagesList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class TextViewHolder extends RecyclerView.ViewHolder {

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.communityx.R
import java.util.*

class MultipleImagesAdapter(private val activity: Activity, private val mImagesList: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_TEXT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_circular_image, parent, false)
            return ImageViewHolder(view)
        } else {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_text_center, parent, false)
            return TextViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {}

    override fun getItemViewType(position: Int): Int {
        return if (position == mImagesList.size - 1) {
            VIEW_TYPE_TEXT
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return mImagesList.size
    }

    internal inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            ButterKnife.bind(this, view)
        }
    }

    internal inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}

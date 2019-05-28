package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.utils.Utils

import java.util.ArrayList

class ChatAdapter(val activity: Activity, val mChatList: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_DATE = 0
    private val VIEW_TYPE_SENDER = 1
    private val VIEW_TYPE_RECEIVER = 2
    private val VIEW_TYPE_MAP = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_DATE) {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_chat_date, parent, false)
            return DateViewHolder(view)
        } else if (viewType == VIEW_TYPE_SENDER) {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_sender, parent, false)
            return SenderViewHolder(view)
        } else if (viewType == VIEW_TYPE_RECEIVER) {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_receiver, parent, false)
            return ReceiverViewHolder(view)
        } else {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_map_added, parent, false)
            return MapViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (viewHolder is MapViewHolder) {
            viewHolder.bindData(activity, mChatList)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mChatList[position].equals("r", ignoreCase = true)) {
            VIEW_TYPE_RECEIVER
        } else if (mChatList[position].equals("d", ignoreCase = true)) {
            VIEW_TYPE_DATE
        } else if (mChatList[position].equals("s", ignoreCase = true)) {
            VIEW_TYPE_SENDER
        } else {
            VIEW_TYPE_MAP
        }
    }

    override fun getItemCount(): Int {
        return mChatList.size
    }

    class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            ButterKnife.bind(this, view)
        }
    }

    class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    class MapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.image_map)
        lateinit var imageMap: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(activity: Activity, list: ArrayList<String>) {
            imageMap.setImageBitmap(Utils.convertToBitmap(activity, list[adapterPosition]))
        }
    }
}
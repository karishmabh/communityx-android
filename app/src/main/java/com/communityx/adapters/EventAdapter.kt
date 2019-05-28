package com.communityx.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.communityx.R
import com.communityx.activity.EventActivity
import com.communityx.activity.EventDetailActivity

import java.util.Objects

class EventAdapter(private val mContext: Context) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventViewHolder {
        return EventViewHolder(mInflater.inflate(R.layout.item_event, viewGroup, false))
    }

    override fun onBindViewHolder(eventViewHolder: EventViewHolder, i: Int) {

    }

    override fun getItemCount(): Int {
        return 8
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { v ->
                mContext.startActivity(Intent(mContext, EventDetailActivity::class.java))
                val activity = mContext as Activity
                activity.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
            }
        }
    }
}

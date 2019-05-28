package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.communityx.R

import java.util.ArrayList

class DonatedCreatorAdapter(private val mDonatedList: ArrayList<String>, private val mActivity: Activity) :
    RecyclerView.Adapter<DonatedCreatorAdapter.EventViewHolder>() {

    private val mLayoutInflator: LayoutInflater = LayoutInflater.from(mActivity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventViewHolder {
        val view = mLayoutInflator.inflate(R.layout.item_donated, viewGroup, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(eventViewHolder: EventViewHolder, i: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

     class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}

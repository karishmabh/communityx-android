package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.communityx.R
import java.util.*

class SearchAdapter(private val mSuggestionList: ArrayList<String>, private val mActvity: Activity) : RecyclerView.Adapter<SearchAdapter.EventHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_search_suggestion, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, i: Int) {
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
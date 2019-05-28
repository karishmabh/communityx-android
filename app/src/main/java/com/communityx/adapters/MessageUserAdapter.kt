package com.communityx.adapters

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.activity.MessageActivity

import java.util.ArrayList

class MessageUserAdapter(private val mUsersList: ArrayList<String>, private val mActvity: Activity) : RecyclerView.Adapter<MessageUserAdapter.MessageHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MessageHolder {
        val view = mLayoutInflater.inflate(R.layout.item_message_recipient, viewGroup, false)
        return MessageHolder(view)
    }

    override fun onBindViewHolder(messageHolder: MessageHolder, i: Int) {}

    override fun getItemCount(): Int {
        return 10
    }

    inner class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)

            itemView.setOnClickListener { mActvity.startActivity(Intent(mActvity, MessageActivity::class.java)) }
        }
    }
}
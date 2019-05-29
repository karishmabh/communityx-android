package com.communityx.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R

class NotificationAlertAdapter(private val mContext: Context) : RecyclerView.Adapter<*>() {

    private val ALERT_NORMAL = 0
    private val ALERT_BIG = 1
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return if (i == ALERT_BIG) {
            ExpandedAlertViewHoleder(inflater.inflate(R.layout.item_notification_expanded, viewGroup, false))
        } else NormalAlertViewHolder(
            inflater.inflate(
                R.layout.item_message_recipient,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val holder = viewHolder as NormalAlertViewHolder
        if (i == 0) {
            holder.setBadgeCount("2")
        }
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 2) {
            ALERT_BIG
        } else ALERT_NORMAL
    }

    internal open inner class NormalAlertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.badge)
        var textBadge: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            textBadge!!.visibility = View.GONE
        }

        fun setBadgeCount(count: String) {
            textBadge!!.visibility = View.VISIBLE
            textBadge!!.text = count
        }
    }

    internal inner class ExpandedAlertViewHoleder(itemView: View) : NormalAlertViewHolder(itemView) {

        @BindView(R.id.view_divider_one)
        var divider: View? = null

        init {
            ButterKnife.bind(this, itemView)
            divider!!.visibility = View.GONE
        }
    }
}

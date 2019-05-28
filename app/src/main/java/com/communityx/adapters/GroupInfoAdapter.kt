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
import com.communityx.utils.Utils

class GroupInfoAdapter(private val mContext: Context) : RecyclerView.Adapter<GroupInfoAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(mInflater.inflate(R.layout.item_group_info, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        if (i == 0) viewHolder.showAdmin(true)
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.text_admin)
        lateinit var textAdmin: TextView

        init {
            ButterKnife.bind(this, itemView)
            textAdmin!!.visibility = View.GONE
        }

        fun showAdmin(show: Boolean) {
            Utils.showHideView(textAdmin!!, show)
        }
    }
}

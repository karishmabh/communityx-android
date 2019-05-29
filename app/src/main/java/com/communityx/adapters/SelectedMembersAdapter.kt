package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.activity.CreateGroupActivity

import java.util.ArrayList

class SelectedMembersAdapter(private val mUsersList: ArrayList<String>, private val mActvity: Activity, private val iUserRemoved: IUserRemoved) : RecyclerView.Adapter<SelectedMembersAdapter.MessageHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MessageHolder {
        val view = mLayoutInflater.inflate(R.layout.item_added_user, viewGroup, false)
        return MessageHolder(view)
    }

    override fun onBindViewHolder(messageHolder: MessageHolder, i: Int) {
        messageHolder.setData(i)
    }

    override fun getItemCount(): Int {
        return mUsersList.size
    }

    inner class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.image_remove)
        lateinit var imageRemove: ImageView
        @BindView(R.id.text_name)
        lateinit  var textName: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun setData(position: Int) {
            if (mActvity is CreateGroupActivity) {
                imageRemove.visibility = View.GONE
            } else {
                imageRemove.visibility = View.VISIBLE
            }

            textName.text = mUsersList[position]
            imageRemove.setOnClickListener {
                iUserRemoved.onUserRemoved(mUsersList[position])
                mUsersList.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }

    interface IUserRemoved {
        fun onUserRemoved(userName: String)
    }
}


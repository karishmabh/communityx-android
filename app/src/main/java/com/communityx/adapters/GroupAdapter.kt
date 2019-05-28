package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import java.util.*

class GroupAdapter(private val mUsersList: ArrayList<String>, private val mActivity: Activity, private val iUsersSelected: IUsersSelected) : RecyclerView.Adapter<GroupAdapter.MessageHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActivity)
    private var mLetter: String? = null
    private val mSelectedUsers = HashSet<String>()

    val selectedUsersList: Set<String>
        get() = mSelectedUsers

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MessageHolder {
        val view = mLayoutInflater.inflate(R.layout.item_user_group, viewGroup, false)
        return MessageHolder(view)
    }

    override fun onBindViewHolder(messageHolder: MessageHolder, i: Int) {
        val name = mUsersList[i]
        if (TextUtils.isEmpty(mLetter)) {
            mLetter = name[0].toString()
            messageHolder.textHeader!!.visibility = View.VISIBLE
            messageHolder.textHeader!!.text = mLetter
        } else {
            val letter = name[0].toString()
            if (!mLetter!!.equals(letter, ignoreCase = true)) {
                mLetter = name[0].toString()
                messageHolder.textHeader!!.visibility = View.VISIBLE
                messageHolder.textHeader!!.text = mLetter
            } else {
                messageHolder.textHeader!!.visibility = View.GONE
            }
        }

        messageHolder.textUserName!!.text = name
        messageHolder.bindData()
    }

    override fun getItemCount(): Int {
        return mUsersList.size
    }

    inner class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.text_header)
        lateinit internal var textHeader: TextView
        @BindView(R.id.text_user_name)
        lateinit internal var textUserName: TextView
        @BindView(R.id.image_tick)
        lateinit internal var imageTick: ImageView

        init {
            ButterKnife.bind(this, itemView)

            itemView.setOnClickListener {
                val isSelected = checkSelectedUsers(mUsersList[adapterPosition])
                if (isSelected) {
                    mSelectedUsers.remove(mUsersList[adapterPosition])
                    imageTick!!.visibility = View.GONE
                } else {
                    mSelectedUsers.add(mUsersList[adapterPosition])
                    imageTick!!.visibility = View.VISIBLE
                }

                iUsersSelected.updatedUsersList(mSelectedUsers)
            }
        }


        fun bindData() {
            if (checkSelectedUsers(mUsersList[adapterPosition])) {
                imageTick!!.visibility = View.VISIBLE
            } else {
                imageTick!!.visibility = View.GONE
            }
        }
    }

    fun uncheckUser(name: String, imageNext: ImageView) {
        if (checkSelectedUsers(name)) {
            mSelectedUsers.remove(name)
        }

        if (mSelectedUsers.size == 0) imageNext.visibility = View.GONE

        notifyDataSetChanged()
    }

    private fun checkSelectedUsers(givenName: String): Boolean {
        for (name in mSelectedUsers) {
            if (name == givenName) {
                return true
            }
        }
        return false
    }

    interface IUsersSelected {
        fun updatedUsersList(mSelectedUsers: Set<String>)
    }
}
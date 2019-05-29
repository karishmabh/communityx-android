package com.communityx.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R

class MyAllFriendsAdapter(private val mContext: Context, private val friendList: List<String>) : RecyclerView.Adapter<MyAllFriendsAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_my_all_friend, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bindData(friendList[i])
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    private fun getFirstLetter(name: String): String {
        return name.substring(0, 1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.image_user)
        lateinit var imageUser: ImageView
        @BindView(R.id.text_name)
        lateinit var textName: TextView
        @BindView(R.id.text_location)
        lateinit var textLocation: TextView
        @BindView(R.id.image_message)
        lateinit var imageMesage: ImageView
        @BindView(R.id.image_option)
        lateinit var imageOption: ImageView
        @BindView(R.id.text_letter)
        lateinit var textLetter: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(name: String) {
            textName.text = name

            setDividerText(name)
        }

        private fun setDividerText(name: String) {
            textLetter.text = getFirstLetter(name)

            var prevStr: String? = null
            if (adapterPosition > 0 && friendList.size > adapterPosition) {
                prevStr = getFirstLetter(friendList[adapterPosition - 1])
            }
            if (prevStr == null) {
                textLetter.visibility = View.VISIBLE
            } else if (prevStr == getFirstLetter(name) && adapterPosition != 0) {
                textLetter.visibility = View.GONE
            } else {
                textLetter.visibility = View.VISIBLE
            }
        }
    }
}

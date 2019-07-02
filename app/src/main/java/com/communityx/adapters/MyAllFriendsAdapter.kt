package com.communityx.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.models.myallies.all_allies.DataX
import com.communityx.utils.AppConstant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_my_all_friend.view.*

class MyAllFriendsAdapter(private val mContext: Context, private val friendList: List<DataX>) :
    RecyclerView.Adapter<MyAllFriendsAdapter.ViewHolder>(), AppConstant {

    private var prevStr: String? = null
    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_my_all_friend, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bindData()
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    private fun getFirstLetter(name: String): String {
        return name.substring(0, 1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData() {
            val item = friendList.get(adapterPosition)
            var name = item.profile?.first_name + " " + item.profile?.last_name
            itemView.text_name.text = name

            if (!TextUtils.isEmpty(item.profile.profile_image)) {
                Picasso.get().load(item.profile.profile_image)
                    .error(R.drawable.profile_placeholder).into(itemView.image_user)
            }

            setProfessionType(item)
            setDividerText(name)
        }

        private fun setProfessionType(dataX: DataX) {
            val type = friendList.get(adapterPosition).type
            var profession: String ? = null
            when (type) {
                AppConstant.STUDENT -> {
                    profession  = "Student" + ", "+ dataX.city
                }
                AppConstant.PROFESSIONAL -> {
                    profession  = dataX.work_experience.get(0).role + ", "+ dataX.city
                }
                AppConstant.ORGANIZATION -> {
                    profession  = "Organization" +", "+ dataX.city
                }
            }
            itemView.text_profession.text = profession
        }

        private fun setDividerText(name: String) {
            itemView.text_letter.text = getFirstLetter(name)

            if (prevStr == null) {
                itemView.text_letter.visibility = View.VISIBLE
            } else if (prevStr == getFirstLetter(name)) {
                itemView.text_letter.visibility = View.GONE
            } else {
                itemView.text_letter.visibility = View.VISIBLE
            }

            prevStr = getFirstLetter(name)
        }
    }
}

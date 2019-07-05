package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.models.myallies.all_allies.DataX
import com.communityx.models.myallies.invitation.Interest
import com.communityx.utils.AppConstant
import com.communityx.utils.Utils
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_connect_allies.view.*

class SuggestionAdapter(private val mSuggestionList: ArrayList<DataX>, private val mActvity: Activity, private val iOnAddFriendClicked: IOnAddFriendClicked) : RecyclerView.Adapter<SuggestionAdapter.EventHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_connect_allies, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, i: Int) {
        eventHolder.bindData()
    }

    override fun getItemCount(): Int {
        return mSuggestionList.size
    }

    public fun updateItem(position: Int) {
        mSuggestionList.removeAt(position)
        notifyDataSetChanged()
    }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.flex_layout_allies)
        lateinit var flexboxLayout: FlexboxLayout

        init {
            ButterKnife.bind(this, itemView)
        }

        @OnClick(R.id.linear_add_friend)
        fun onAddFriendClicked() {
            iOnAddFriendClicked.sendInvitationTapped(mSuggestionList.get(adapterPosition).id, adapterPosition)
        }

        fun bindData() {
            itemView.linear_message.visibility = View.GONE

            val item = mSuggestionList.get(adapterPosition)
            itemView.text_title_name.text = Utils.capitalizeFirstLetter(item.name)

            if (!TextUtils.isEmpty(item.profile.profile_image)) {
                Picasso.get().load(item.profile.profile_image)
                    .error(R.drawable.profile_placeholder).into(itemView.circle_profile_image)
            }
            setProfessionType(item)
            setFLexLayout(flexboxLayout, mSuggestionList.get(adapterPosition).interests)
        }

        private fun setProfessionType(dataX: DataX) {
            val type = mSuggestionList.get(adapterPosition).type
            var profession: String ? = null
            when (type) {
                AppConstant.STUDENT -> {
                    profession  = "Student" + getCity(dataX)
                }
                AppConstant.PROFESSIONAL -> {
                    profession  =
                        if (!dataX.work_experience.isNullOrEmpty() && dataX.work_experience.size > 0)
                            dataX.work_experience.get(0).role + getCity(dataX)
                        else "Professional" + getCity(dataX)
                }
                AppConstant.ORGANIZATION -> {
                    profession  = "Organization" +getCity(dataX)
                }
            }
            itemView.text_description.text = profession
        }
    }

    private fun getCity(dataX: DataX): String {
        if (dataX.city.isNullOrEmpty()) {
            return ""
        } else {
          return ", " + dataX.city
        }
    }

    fun setFLexLayout(fLexLayout: FlexboxLayout?, interest: List<Interest>) {
        fLexLayout!!.removeAllViews()
        for (item in interest) {
            val checkBox = LayoutInflater.from(mActvity).inflate(R.layout.friend_item_interest, null) as CheckBox
            checkBox.text = item.name

            val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            fLexLayout.addView(checkBox, lp)
        }
    }

    public interface IOnAddFriendClicked {
        fun sendInvitationTapped(id: String, position: Int)
    }
}

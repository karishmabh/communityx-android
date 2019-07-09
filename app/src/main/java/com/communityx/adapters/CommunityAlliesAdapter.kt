package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.models.myallies.invitation.Interest
import com.communityx.models.myallies.all_allies.DataX
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.*
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_connect_allies.view.*

class CommunityAlliesAdapter(
    private val mArrayList: List<DataX>,
    private val mActvity: Activity,
    private val iOnAddFriendClicked: IOnAddFriendClicked) : AppConstant,
    RecyclerView.Adapter<CommunityAlliesAdapter.EventHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_connect_allies, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, i: Int) {
        eventHolder.bindData()
        if (mArrayList[i].profile != null) {
            val interesttList = mArrayList[i].interests
            setFLexLayout(eventHolder.flexboxLayout, interesttList)
        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.flex_layout_allies)
        lateinit var flexboxLayout: FlexboxLayout

        init {
            ButterKnife.bind(this, itemView)
        }

        @OnClick(R.id.linear_add_friend)
        fun onAddFriendClicked() {
               iOnAddFriendClicked.sendInvitationTapped(mArrayList[adapterPosition].id, adapterPosition, itemView.image_request)
        }

        fun bindData() {
            val profileData = mArrayList.get(adapterPosition)

            itemView.linear_message.visibility = View.GONE

            if (profileData.profile == null) return
            try {
                if (!TextUtils.isEmpty(profileData.profile.profile_image)) {
                    Picasso.get().load(profileData.profile.profile_image)
                        .noPlaceholder()
                        .fit()
                        .error(R.drawable.profile_placeholder).fit()
                        .into(itemView.circle_profile_image)
                }
            } catch (e: Exception) {

            }

            itemView.text_description.text = setSubTitle(profileData.type)
        }

        fun setSubTitle(category: String): String {
            when (category) {
                STUDENT -> {
                    itemView.text_title_name.text = mArrayList.get(adapterPosition).name
                    if (mArrayList[adapterPosition].education.isNotEmpty()) {
                        return "Student" + ", " + mArrayList[adapterPosition].education[0].name
                    }
                }

                ORGANIZATION -> {
                    itemView.text_title_name.text = mArrayList[adapterPosition].name
                    return "Organization" + ", " + mArrayList[adapterPosition].name
                }

                PROFESSIONAL -> {
                    itemView.text_title_name.text = mArrayList[adapterPosition].name
                    if (mArrayList[adapterPosition].work_experience !=null && mArrayList[adapterPosition].work_experience.isNotEmpty()) {
                        return mArrayList[adapterPosition].work_experience[0].role+ ", " + mArrayList[adapterPosition].work_experience[0].name
                    }
                }
            }
            return ""
        }
    }


    fun setFLexLayout(fLexLayout: FlexboxLayout?, interest: List<Interest>) {
        fLexLayout!!.removeAllViews()
        for (i in interest.indices) {
            val checkBox = LayoutInflater.from(mActvity).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = interest.get(i).name
            checkBox.performClick()

            val lp =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            fLexLayout.addView(checkBox, lp)
        }
    }

    interface IOnAddFriendClicked {
        fun sendInvitationTapped(id: String, position: Int, imageView: ImageView)
    }
}

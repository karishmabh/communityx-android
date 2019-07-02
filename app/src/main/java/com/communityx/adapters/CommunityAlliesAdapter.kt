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
import com.communityx.models.connect_allies.Interest
import com.communityx.models.connect_allies.Minors
import com.communityx.models.connect_allies.ProfileData
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.*
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_connect_allies.view.*

class CommunityAlliesAdapter(private val mArrayList: List<ProfileData>, private val mActvity: Activity, private val iOnAddFriendClicked: IOnAddFriendClicked) : AppConstant,
        RecyclerView.Adapter<CommunityAlliesAdapter.EventHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_connect_allies, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, i: Int) {
        eventHolder.bindData()
        if (mArrayList.get(i).profile != null) {
            val interesttList = mArrayList.get(i).minors
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
            if (mArrayList.get(adapterPosition).status.isNullOrEmpty()) {
                iOnAddFriendClicked.sendInvitationTapped(mArrayList.get(adapterPosition).id, adapterPosition, itemView.image_request)
            }
        }

        fun bindData() {
            val profileData = mArrayList.get(adapterPosition)

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

            itemView.text_description.setText(setSubTitle(profileData.category))
        }

        fun setSubTitle(category: String) : String {
            when (category) {
                STUDENT -> {
                    itemView.text_title_name.setText(mArrayList.get(adapterPosition).profile.full_name)
                    return "Student"+ ", "+ mArrayList.get(adapterPosition).profile.standard.name
                }

                ORGANIZATION ->  {
                    itemView.text_title_name.setText(mArrayList.get(adapterPosition).profile.name)
                    return "Organization" + ", " + mArrayList.get(adapterPosition).profile.name
                }

                PROFESSIONAL ->  {
                    itemView.text_title_name.setText(mArrayList.get(adapterPosition).profile.full_name)
                    return mArrayList.get(adapterPosition).profile.job_title.name +", "+ mArrayList.get(adapterPosition).profile.company.name
                }
            }
            return ""
        }
    }


    fun setFLexLayout(fLexLayout: FlexboxLayout?, interest: List<Minors>) {
        fLexLayout!!.removeAllViews()
        for (i in interest.indices) {
            val checkBox = LayoutInflater.from(mActvity).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = interest.get(i).name
            checkBox.performClick()

            val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            fLexLayout.addView(checkBox, lp)
        }
    }

    public interface IOnAddFriendClicked {
        fun sendInvitationTapped(id: String, position: Int, imageView: ImageView)
    }
}

package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ScrollView
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.models.editinfo.Data
import com.communityx.models.editinfo.Subinterest
import com.communityx.models.signup.Minor
import com.communityx.models.signup.MinorsData
import com.communityx.utils.AppConstant
import com.communityx.utils.SnackBarFactory
import kotlinx.android.synthetic.main.item_select_interest.view.*

class SelectedInfoInterestAdapter(val mInterestList: List<Data>, val mActvity: Activity, val scrollView: ScrollView) :
    RecyclerView.Adapter<SelectedInfoInterestAdapter.EventHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)
    private val mSelectedIds : ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_select_interest, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, i: Int) {
        eventHolder.bindData()
    }

    override fun getItemCount(): Int {
        return mInterestList.size
    }

    fun getSelectedIds() : ArrayList<String> {
       return mSelectedIds
    }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData() {
            val minorsData = mInterestList[adapterPosition]

            itemView.text_heading.text = minorsData.name
            initFlexLayout(minorsData.subinterests)
        }

        private fun initFlexLayout(civilRights: List<Subinterest>) {
            itemView.flex_layout.removeAllViews()

            for (civilRight in civilRights) {
                val checkBox = LayoutInflater.from(mActvity).inflate(R.layout.item_interest, null) as CheckBox
                checkBox.text = civilRight.name
                checkBox.performClick()

                for (selectedid in mSelectedIds) {
                    if (civilRight.id == selectedid) {
                        checkBox.setBackgroundResource(R.drawable.bg_interest_active)
                        checkBox.setTextColor(mActvity.resources.getColor(R.color.colorBlackTitle))
                    }
                }

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (!mSelectedIds.contains(civilRight.id)) {
                        if (mSelectedIds.size == 5) {
                            showMaximumReached()
                            return@setOnCheckedChangeListener
                        }
                        checkBox.setBackgroundResource(com.communityx.R.drawable.bg_interest_active)
                        checkBox.setTextColor(mActvity.resources.getColor(R.color.colorBlackTitle))
                        mSelectedIds.add(civilRight.id)
                    } else {
                        checkBox.setBackgroundResource(com.communityx.R.drawable.bg_interest_inactive)
                        checkBox.setTextColor(mActvity.resources.getColor(R.color.colorLightGrey))
                        mSelectedIds.remove(civilRight.id)
                    }

                    /*var category = (mActvity as SignUpStudentInfoActivity).selectedCategory

                    if (mActvity == null) return@setOnCheckedChangeListener

                    if (mSelectedIds.size > 0) {
                        mActvity.enableButton(true)

                        if (category.equals(AppConstant.ORGANIZATION)) {
                            mActvity.changeButtonStatus(1, true)
                        } else {
                            mActvity.changeButtonStatus(4, true)
                        }
                    } else {
                        mActvity.enableButton(false)

                        if (category.equals(AppConstant.ORGANIZATION)) {
                            mActvity.changeButtonStatus(1, false)
                        } else {
                            mActvity.changeButtonStatus(4, false)
                        }
                    }*/
                }

                val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                lp.setMargins(10, 10, 10, 10)

                itemView.flex_layout.addView(checkBox, lp)
            }
        }

        private fun showMaximumReached() {
            SnackBarFactory.createSnackBar(mActvity, scrollView, mActvity.resources.getString(com.communityx.R.string.limit_interest))
        }
    }
}
package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.models.myallies.invitation.DataX
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.item_invitation.view.*

class InvitationAdapter(private val mInvitationList: List<DataX>, private val mActivity: Activity) : RecyclerView.Adapter<InvitationAdapter.EventHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActivity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_invitation, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, i: Int) {
       eventHolder.bindData()
    }

    override fun getItemCount(): Int {
        return mInvitationList.size
    }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.flex_layout_allies)
        lateinit var flexboxLayout: FlexboxLayout

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData() {
            var name = mInvitationList.get(adapterPosition).first_name + " "+mInvitationList.get(adapterPosition).last_name
            itemView.text_title_name.text = name

            var profession = mInvitationList.get(adapterPosition).headline + " "+mInvitationList.get(adapterPosition).city
            itemView.text_description.text = profession

            setFLexLayout(flexboxLayout,  mInvitationList.get(adapterPosition).interests)
        }
    }

    fun setFLexLayout(fLexLayout: FlexboxLayout?, interest: List<String>) {
        fLexLayout!!.removeAllViews()

        for (civilRight in interest) {
            val checkBox = LayoutInflater.from(mActivity).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = civilRight
            checkBox.performClick()
            checkBox.setOnCheckedChangeListener { buttonView, isChecked -> checkBox.setBackgroundResource(if (isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive) }

            val lp =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            fLexLayout.addView(checkBox, lp)
        }
    }
}

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
import com.communityx.R
import com.communityx.models.myallies.invitation.DataX
import com.communityx.models.myallies.invitation.Interest
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_connect_allies.view.*

class SuggestionAdapter(private val mSuggestionList: List<DataX>, private val mActvity: Activity) : RecyclerView.Adapter<SuggestionAdapter.EventHolder>() {

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

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.flex_layout_allies)
        lateinit var flexboxLayout: FlexboxLayout

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData() {
            var name =
                mSuggestionList.get(adapterPosition).first_name + " " + mSuggestionList.get(adapterPosition).last_name
            itemView.text_title_name.text = name

            var profession =
                mSuggestionList.get(adapterPosition).headline + " " + mSuggestionList.get(adapterPosition).city
            itemView.text_description.text = profession

            if (!TextUtils.isEmpty(mSuggestionList.get(adapterPosition).profile_image)) {
                Picasso.get().load(mSuggestionList.get(adapterPosition).profile_image)
                    .error(R.drawable.profile_placeholder).into(itemView.circle_profile_image)
            }
            setFLexLayout(flexboxLayout, mSuggestionList.get(adapterPosition).interests)
        }
    }

    fun setFLexLayout(fLexLayout: FlexboxLayout?, interest: List<Interest>) {
        fLexLayout!!.removeAllViews()
        for (item in interest) {
            val checkBox = LayoutInflater.from(mActvity).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = item.name

            val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            fLexLayout.addView(checkBox, lp)
        }
    }
}

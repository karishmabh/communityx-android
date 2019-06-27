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
import com.google.android.flexbox.FlexboxLayout
import java.util.*

class SuggestionAdapter(private val mSuggestionList: ArrayList<String>, private val mActvity: Activity) : RecyclerView.Adapter<SuggestionAdapter.EventHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_connect_allies, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, i: Int) {
        val list = Arrays.asList("School Safety", "Immigration", "LGBTQ+", "Mental Health", "Prisom Reform")
        setFLexLayout(eventHolder.flexboxLayout, list)
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.flex_layout_allies)
        lateinit var flexboxLayout: FlexboxLayout

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    fun setFLexLayout(fLexLayout: FlexboxLayout?, interest: List<String>) {
        fLexLayout!!.removeAllViews()
        for (civilRight in interest) {
            val checkBox = LayoutInflater.from(mActvity).inflate(R.layout.friend_item_interest, null) as CheckBox
            checkBox.text = civilRight
            checkBox.performClick()
            checkBox.setOnCheckedChangeListener { buttonView, isChecked -> checkBox.setBackgroundResource(if (isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive) }

            val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            fLexLayout.addView(checkBox, lp)
        }
    }
}

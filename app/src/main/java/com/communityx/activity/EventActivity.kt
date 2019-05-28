package com.communityx.activity

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.EventPagerAdapter
import kotlinx.android.synthetic.main.activity_event.*
import java.util.*

class EventActivity : BaseActivity() {

    private var pagerAdapter: EventPagerAdapter? = null

    private val titles: List<String>
        get() {
            val list = ArrayList<String>()
            list.add(getString(R.string.my_hosted_events))
            list.add(getString(R.string.i_am_interested_in))
            return list
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        ButterKnife.bind(this)

        setToolBar(this, getString(R.string.events), true)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        pagerAdapter = EventPagerAdapter(supportFragmentManager)
        pagerAdapter!!.setTitles(titles)
        view_pager!!.adapter = pagerAdapter
        tab_layout!!.setupWithViewPager(view_pager)
        updateTabText()
    }

    private fun updateTabText() {
        for (i in 0 until tab_layout!!.tabCount) {
            val tv = ((tab_layout!!.getChildAt(0) as LinearLayout).getChildAt(i) as LinearLayout).getChildAt(1) as TextView
            val title = pagerAdapter!!.getPageTitle(i)!!.toString()
            var updatedText = ""
            when (i) {
                0 -> updatedText = "$title 03"
                1 -> updatedText = "$title 08"
            }
            val spannable = createSpannable(updatedText, title)
            if (spannable != null) tv.setText(spannable, TextView.BufferType.SPANNABLE)
        }
    }

    private fun createSpannable(updatedText: String, title: String): Spannable {
        val spannable = SpannableString(updatedText)
        spannable.setSpan(ForegroundColorSpan(this.resources.getColor(R.color.colorPrimary)),
                title.length, updatedText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}

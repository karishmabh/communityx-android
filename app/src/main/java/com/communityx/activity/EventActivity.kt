package com.communityx.activity

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.EventPagerAdapter
import com.communityx.custom_views.CustomViewPager

import java.util.ArrayList

class EventActivity : BaseActivity() {

    @BindView(R.id.tab_layout)
    internal var tabLayout: TabLayout? = null
    @BindView(R.id.view_pager)
    internal var viewPager: CustomViewPager? = null
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
        viewPager!!.adapter = pagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)
        updateTabText()
    }

    private fun updateTabText() {
        for (i in 0 until tabLayout!!.tabCount) {
            val tv = ((tabLayout!!.getChildAt(0) as LinearLayout).getChildAt(i) as LinearLayout).getChildAt(1) as TextView
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

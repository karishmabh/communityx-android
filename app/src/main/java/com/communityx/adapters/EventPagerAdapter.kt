package com.communityx.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.communityx.fragments.MyHostedEventFragment

class EventPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var mListTitles: List<String>? = null
    private val fragments = arrayOf<Fragment>(MyHostedEventFragment(), MyHostedEventFragment())

    fun setTitles(mListTitles: List<String>) {
        this.mListTitles = mListTitles
    }

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mListTitles!![position]
    }
}

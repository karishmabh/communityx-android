package com.communityx.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.communityx.fragments.*

class SignUpPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var fragments: List<Fragment>? = null

    val totalItems: Int
        get() = fragments!!.size

    private val buttonEnabledPos = booleanArrayOf(true, false, false, true, true)

    fun setFragments(fragments: List<Fragment>) {
        this.fragments = fragments
    }

    override fun getItem(i: Int): Fragment {
        return fragments!![i]
    }

    override fun getCount(): Int {
        return fragments!!.size
    }

    fun isButtonEnabled(pos: Int): Boolean {
        return buttonEnabledPos[pos]
    }
}

package com.communityx.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.communityx.base.BaseSignUpFragment
import com.communityx.fragments.*

class SignUpPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var fragments: List<Fragment>? = null

    val totalItems: Int
        get() = fragments!!.size

    private val buttonEnabledPos = booleanArrayOf(false, false, false, false, false)

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

    fun setButtonEnabled(pos: Int, value: Boolean) {
        buttonEnabledPos[pos] = value
    }

    public fun getCurrentFragment(position : Int) : BaseSignUpFragment{
        return fragments?.get(position) as BaseSignUpFragment
    }
}

package com.communityx.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.communityx.fragments.FriendsFragment
import com.communityx.fragments.InvitationFragment
import com.communityx.fragments.SuggestionFragment

class FriendsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = arrayOf(FriendsFragment(), InvitationFragment(), SuggestionFragment())

    //TODO: Hard Coded String
    private val titles = arrayOf("All Allies", "Invitations", "Suggestions")

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}

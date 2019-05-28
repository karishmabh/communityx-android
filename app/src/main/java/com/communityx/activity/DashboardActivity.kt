package com.communityx.activity

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.fragments.*
import com.communityx.utils.AnimationUtils
import com.communityx.utils.DialogHelper
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var hasGoneToProfileViewImage = false
    private var isBackPressClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        ButterKnife.bind(this)

        nav_view!!.setOnNavigationItemSelectedListener(this)
        nav_view!!.selectedItemId = R.id.navigation_community
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.navigation_friends -> replceFragment(MyAllFriendsFragment(), "fragment_myfriends")
            R.id.navigation_community -> {
                if (isBackPressClick) {
                    isBackPressClick = false
                    hasGoneToProfileViewImage = false
                    return true
                } else if (hasGoneToProfileViewImage) {
                    onBackPressed()
                    return true
                }
                AnimationUtils.rotateView(image_feed!!, 0, 800)
                replceFragment(CommunityFeedFragment(), "fragment_community")
            }
            R.id.navigation_message -> replceFragment(MessageFragment(), "fragment_message")
            R.id.navigation_profile -> {
                if (hasGoneToProfileViewImage) {
                    return true
                }
                replceFragment(ProfileFragment(), "fragment_profile")
            }
            R.id.navigation_notifications -> replceFragment(NotificationFragment(), "fragment_notification")
        }
        return true
    }

    @OnClick(R.id.view_community_btn)
    internal fun tappedCommunityFeed() {
        nav_view!!.selectedItemId = R.id.navigation_community
    }

    private fun replceFragment(fragment: Fragment, tag: String) {
        Utils.replaceFragment(this, fragment, false, tag)
    }

    override fun onBackPressed() {
        if (hasGoneToProfileViewImage) {
            isBackPressClick = true
            nav_view!!.selectedItemId = R.id.navigation_community
        }
        super.onBackPressed()
    }
}

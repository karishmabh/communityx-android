package com.communityx.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.TransitionInflater
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTouch
import com.communityx.R
import com.communityx.activity.CreatePostActivity
import com.communityx.activity.DashboardActivity
import com.communityx.adapters.CommunityFeedAdapter
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.FRAGMENT_PROFILE
import com.communityx.utils.CustomToolBarHelper
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_community_feed.*
import kotlinx.android.synthetic.main.view_search.*

import java.util.Objects

class CommunityFeedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_community_feed, container, false)
        ButterKnife.bind(this, view)
        setUpToolbar(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edit_search!!.setHint(R.string.write_something_here)
        edit_search!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        recycler_view_feed!!.layoutManager = LinearLayoutManager(context)
        val communityFeedAdapter = CommunityFeedAdapter(context)
        recycler_view_feed!!.adapter = communityFeedAdapter
    }

    @OnTouch(R.id.edit_search)
    internal fun tappedCreatePost(view: View, event: MotionEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            startActivity(Intent(context, CreatePostActivity::class.java))
            activity!!.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            return true
        }
        return false
    }

    @OnClick(R.id.image_user_profile)
    internal fun userImageTapped() {
        val profileFragment = ProfileFragment()
        profileFragment.sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.fragment_transition)
        assert(fragmentManager != null)
        fragmentManager!!
            .beginTransaction()
            .addSharedElement(image_user_profile!!, Objects.requireNonNull<String>(ViewCompat.getTransitionName(image_user_profile!!)))
            .addSharedElement(card!!, Objects.requireNonNull<String>(ViewCompat.getTransitionName(card!!)))
            .replace(R.id.frame_root, profileFragment)
            .addToBackStack(FRAGMENT_PROFILE)
            .commit()
        val activity = activity as DashboardActivity?
        activity!!.hasGoneToProfileViewImage = true
        activity.nav_view.setSelectedItemId(R.id.navigation_profile)
    }

    private fun setUpToolbar(view: View) {
        val toolbarHelper = CustomToolBarHelper(view)
        toolbarHelper.setTitle(R.string.my_community)
        toolbarHelper.setImageTail(R.drawable.ic_my_community_nav_filter)
    }
}

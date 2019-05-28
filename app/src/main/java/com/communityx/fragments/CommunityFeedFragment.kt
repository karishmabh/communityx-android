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
import com.communityx.utils.CustomToolBarHelper
import de.hdodenhof.circleimageview.CircleImageView

import java.util.Objects

class CommunityFeedFragment : Fragment() {

    @BindView(R.id.recycler_view_feed)
    internal var recyclerView: RecyclerView? = null
    @BindView(R.id.edit_search)
    internal var editPost: EditText? = null
    @BindView(R.id.image_user_profile)
    internal var imageUser: CircleImageView? = null
    @BindView(R.id.card)
    internal var cardPost: CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community_feed, container, false)
        ButterKnife.bind(this, view)
        setUpToolbar(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editPost!!.setHint(R.string.write_something_here)
        editPost!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        recyclerView!!.layoutManager = LinearLayoutManager(context)
        val communityFeedAdapter = CommunityFeedAdapter(context)
        recyclerView!!.adapter = communityFeedAdapter
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
            .addSharedElement(imageUser!!, Objects.requireNonNull<String>(ViewCompat.getTransitionName(imageUser!!)))
            .addSharedElement(cardPost!!, Objects.requireNonNull<String>(ViewCompat.getTransitionName(cardPost!!)))
            .replace(R.id.frame_root, profileFragment)
            .addToBackStack("fragment_profile")
            .commit()
        val activity = activity as DashboardActivity?
        activity!!.hasGoneToProfileViewImage = true
        activity.getBottomNavigationView().setSelectedItemId(R.id.navigation_profile)
    }

    private fun setUpToolbar(view: View) {
        val toolbarHelper = CustomToolBarHelper(view)
        toolbarHelper.setTitle(R.string.my_community)
        toolbarHelper.setImageTail(R.drawable.ic_my_community_nav_filter)
    }
}

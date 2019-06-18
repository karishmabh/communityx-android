package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.CommunityFeedAdapter
import com.communityx.adapters.ProfileInfoAdapter
import com.communityx.database.FakeDatabase
import com.communityx.utils.AppConstant
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_profile_about_section_v2.*
import kotlinx.android.synthetic.main.layout_profile_post.*
import kotlinx.android.synthetic.main.view_search.*

class ProfileActivity : AppCompatActivity(), AppConstant {

    private val isOtherProfile = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        ButterKnife.bind(this)
        edit_search!!.setHint(R.string.write_something_here)
        edit_search!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        setAboutInfo()
        setMyPost()
        showEditIcon(!isOtherProfile)
        showAddHeadlines(false && !isOtherProfile)
        setPostLabel(isOtherProfile)
        showAddAndMessageButton(isOtherProfile)
    }

    @OnClick(R.id.text_see_all)
    internal fun tappedSeeAll() {
        val intent = Intent(this, SeeAllAboutActivity::class.java)
        intent.putExtra(AppConstant.IS_OTHER_PROFILE, isOtherProfile)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
    }

    private fun setAboutInfo() {
        val linearLayoutManager = LinearLayoutManager(this)
        recycler_about!!.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(recycler_about!!.context, linearLayoutManager.orientation)
        recycler_about!!.addItemDecoration(dividerItemDecoration)

        val adapter = ProfileInfoAdapter(this, FakeDatabase.get().profileInfoDao.profileInfo)
        adapter.setOtherProfile(isOtherProfile)
        recycler_about!!.adapter = adapter
    }

    private fun setMyPost() {
        recycler_post!!.layoutManager = LinearLayoutManager(this)
        val communityFeedAdapter = CommunityFeedAdapter(this)
        communityFeedAdapter.setFromProfile(true)
        communityFeedAdapter.setOtherProfile(isOtherProfile)
        recycler_post!!.adapter = communityFeedAdapter
    }

    private fun showEditIcon(shouldShow: Boolean) {
        Utils.showHideView(edit_profile!!, shouldShow)
    }

    private fun showAddHeadlines(shouldShow: Boolean) {
        Utils.showHideView(view_add_headline!!, shouldShow)
        Utils.showHideView(text_headline!!, !shouldShow)
    }

    private fun showAddAndMessageButton(shouldShow: Boolean) {
        Utils.showHideView(view_add_msg_other!!, shouldShow)
    }

    private fun setPostLabel(isOtherProfile: Boolean) {
        text_my_post!!.text = if (isOtherProfile) getString(R.string.posts) else getString(R.string.my_posts)
    }

    fun goBack(view: View) {
        onBackPressed()
    }
}

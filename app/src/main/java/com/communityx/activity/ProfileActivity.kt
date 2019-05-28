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

class ProfileActivity : AppCompatActivity(), AppConstant {

    @BindView(R.id.recycler_post)
    internal var recyclerPost: RecyclerView? = null
    @BindView(R.id.view_add_headline)
    internal var viewAddHeadLine: View? = null
    @BindView(R.id.view_add_msg_other)
    internal var viewAddAndMsg: View? = null
    @BindView(R.id.edit_profile)
    internal var editProfile: ImageView? = null
    @BindView(R.id.text_headline)
    internal var textHeadline: TextView? = null
    @BindView(R.id.text_my_post)
    internal var textMyPost: TextView? = null
    @BindView(R.id.edit_search)
    internal var editPost: EditText? = null
    @BindView(R.id.recycler_about)
    internal var recyclerAbout: RecyclerView? = null

    @BindString(R.string.my_posts)
    internal var myPost: String? = null
    @BindString(R.string.posts)
    internal var post: String? = null

    private val isOtherProfile = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        ButterKnife.bind(this)
        editPost!!.setHint(R.string.write_something_here)
        editPost!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

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
        recyclerAbout!!.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(recyclerAbout!!.context, linearLayoutManager.orientation)
        recyclerAbout!!.addItemDecoration(dividerItemDecoration)
        val adapter = ProfileInfoAdapter(this, FakeDatabase.get().profileInfoDao.profileInfo)
        adapter.setOtherProfile(isOtherProfile)
        recyclerAbout!!.adapter = adapter
    }

    private fun setMyPost() {
        recyclerPost!!.layoutManager = LinearLayoutManager(this)
        val communityFeedAdapter = CommunityFeedAdapter(this)
        communityFeedAdapter.setFromProfile(true)
        communityFeedAdapter.setOtherProfile(isOtherProfile)
        recyclerPost!!.adapter = communityFeedAdapter
    }

    private fun showEditIcon(shouldShow: Boolean) {
        Utils.showHideView(editProfile!!, shouldShow)
    }

    private fun showAddHeadlines(shoulShow: Boolean) {
        Utils.showHideView(viewAddHeadLine!!, shoulShow)
        Utils.showHideView(textHeadline!!, !shoulShow)
    }

    private fun showAddAndMessageButton(shouldShow: Boolean) {
        Utils.showHideView(viewAddAndMsg!!, shouldShow)
    }

    private fun setPostLabel(isOtherProfile: Boolean) {
        textMyPost!!.text = if (isOtherProfile) post else myPost
    }

    fun goBack(view: View) {
        onBackPressed()
    }
}

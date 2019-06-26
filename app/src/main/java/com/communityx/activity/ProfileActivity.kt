package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.ProfileInfoAdapter
import com.communityx.database.FakeDatabase
import com.communityx.models.profile.ProfileData
import com.communityx.models.profile.ProfileResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.AppConstant
import com.communityx.utils.CustomProgressBar
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_profile_about_section_v2.*

class ProfileActivity : AppCompatActivity(), AppConstant {

    private val isOtherProfile = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        ButterKnife.bind(this)

        setAboutInfo()
        getProfile()
        showEditIcon(!isOtherProfile)
        showAddHeadlines(false && !isOtherProfile)
        showAddAndMessageButton(isOtherProfile)
    }

    @OnClick(R.id.image_back)
    internal fun backTapped() {
        finish()
        overridePendingTransition(R.anim.anim_prev_slide_in, R.anim.anim_prev_slide_out)
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

    private fun getProfile() {
        val dialog = CustomProgressBar.getInstance(this).showProgressDialog("Fetching Profile...")
        DataManager.getProfile(this, object : ResponseListener<ProfileResponse> {
            override fun onSuccess(response: ProfileResponse) {
                dialog.dismiss()

                var profileResponse : ProfileResponse = response
                setProfile(profileResponse.data.get(0))
            }

            override fun onError(error: Any) {
                dialog.dismiss()
               // Utils.showError(this@ProfileActivity, linear_top, error)
            }
        })
    }

    private fun setProfile(profileData: ProfileData) {
        text_name.text = profileData.name.toString()
    }
}

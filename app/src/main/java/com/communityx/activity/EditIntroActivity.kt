package com.communityx.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.SelectedInfoInterestAdapter
import com.communityx.models.editinfo.Data
import com.communityx.models.editinfo.EditInfoInterestResponse
import com.communityx.models.profile.ProfileResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.AppConstant.UserName
import com.communityx.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_intro.*
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.recycler_interests
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.scrollView
import kotlinx.android.synthetic.main.toolbar.*

class EditIntroActivity : AppCompatActivity() {

    private lateinit var mInterestAdapter: SelectedInfoInterestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_intro)
        ButterKnife.bind(this)

        setToolbar()
        getIntentData()
        loadInterest()
    }

    private fun getIntentData() {
        val profileResponse = intent?.extras?.getSerializable(UserName) as ProfileResponse
        if (profileResponse != null) {
            val data = profileResponse.data.get(0)
            edit_full_name.setText(data.first_name + " " + data.last_name)
            edit_recent_job_title.setText(data.type)
            edit_location.setText(data.city)

            if (!data.profile.profile_image.isNullOrEmpty()) {
                Picasso.get().load(data?.profile?.profile_image).into(image_profile)
            }
        }
    }

    @OnClick(R.id.imageView)
    fun closeTapped() {
        finish()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
    }

    private fun setToolbar() {
        image_tail_toolbar.visibility = View.GONE
        text_title.text = getString(R.string.string_edit_profile)
        imageView.setImageDrawable(resources.getDrawable(R.drawable.image_close_white))
    }

    private fun loadInterest() {
        DataManager.getEditInterests(this, object : ResponseListener<EditInfoInterestResponse> {
            override fun onSuccess(response: EditInfoInterestResponse) {

                var list: List<Data> = response.data

                setRecycler(list)
            }

            override fun onError(error: Any) {
                Utils.showError(this@EditIntroActivity, scrollView, error)
            }
        })
    }

    private fun setRecycler(response: List<Data>) {
        recycler_interests.layoutManager = LinearLayoutManager(this)
        mInterestAdapter = SelectedInfoInterestAdapter((response), this, scrollView)
        recycler_interests.adapter = mInterestAdapter
    }
}

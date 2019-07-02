package com.communityx.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.communityx.models.profile.Education
import com.communityx.models.profile.ProfileResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.AppConstant.UserName
import com.communityx.utils.GalleryPicker
import com.communityx.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_intro.*
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.recycler_interests
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.scrollView
import kotlinx.android.synthetic.main.toolbar.*

class EditIntroActivity : AppCompatActivity(), GalleryPicker.GalleryPickerListener {

    private lateinit var mInterestAdapter: SelectedInfoInterestAdapter
    private var listSelected: ArrayList<Education> = ArrayList()
    private var galleryPicker: GalleryPicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_intro)
        ButterKnife.bind(this)

        setToolbar()
        getIntentData()
        loadInterest()

    }

    @OnClick(R.id.image_profile)
    internal fun chooseImage() {
        showImageChooserDialog()
    }

    private fun showImageChooserDialog() {
        galleryPicker = GalleryPicker.with(this).setListener(this@EditIntroActivity)
            .showDialog()
    }

    private fun getIntentData() {
        val profileResponse = intent?.extras?.getSerializable(UserName) as ProfileResponse
        if (profileResponse != null) {
            val data = profileResponse.data.get(0)

            edit_full_name.setText(data.profile.first_name + " " + data.profile.last_name)
            edit_recent_job_title.setText(data.type)
            edit_location.setText(data.state)

            listSelected = data.interests as ArrayList<Education>

            if (!data.profile.profile_image.isNullOrEmpty()) {
                Picasso.get().load(data?.profile?.profile_image).into(image_profile)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) galleryPicker?.fetch(requestCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        galleryPicker?.onResultPermission(requestCode, grantResults)
    }

    override fun onMediaSelected(imagePath: String, uri: Uri, isImage: Boolean) {
        image_profile.setImageURI(uri)
        text_profile.text = resources.getString(R.string.edit_profile_image)
        image_add_edit.setImageResource(R.drawable.ic_signup_edit_image)

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
        mInterestAdapter = SelectedInfoInterestAdapter(response, this, listSelected, scrollView)
        recycler_interests.adapter = mInterestAdapter
    }
}

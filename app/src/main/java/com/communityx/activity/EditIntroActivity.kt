package com.communityx.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.SelectedInfoInterestAdapter
import com.communityx.models.editinfo.Data
import com.communityx.models.editinfo.EditInfoInterestResponse
import com.communityx.models.editintroinfo.EditIntroInfoRequest
import com.communityx.models.editintroinfo.EditIntroInfoResponse
import com.communityx.models.profile.Education
import com.communityx.models.profile.ProfileResponse
import com.communityx.models.signup.image.ImageUploadRequest
import com.communityx.models.signup.image.ImageUploadResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.places.PlacesFieldSelector
import com.communityx.utils.*
import com.communityx.utils.AppConstant.*
import com.google.android.gms.location.places.Place
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_intro.*
import kotlinx.android.synthetic.main.activity_edit_intro.edit_cause
import kotlinx.android.synthetic.main.activity_edit_intro.flex_layout_cause
import kotlinx.android.synthetic.main.activity_edit_intro.image_add_edit
import kotlinx.android.synthetic.main.activity_edit_intro.image_profile
import kotlinx.android.synthetic.main.activity_edit_intro.text_profile
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.recycler_interests
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.scrollView
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditIntroActivity : AppCompatActivity(), GalleryPicker.GalleryPickerListener {

    private lateinit var mInterestAdapter: SelectedInfoInterestAdapter
    var listSelected: ArrayList<Education> = ArrayList()
    private var manaualInterest: MutableList<String>? = null
    private var galleryPicker: GalleryPicker? = null
    var interests: MutableList<String>? = mutableListOf()
    var profileImage : String? = null
    var profileResponse : ProfileResponse? = null
    private var placesFieldSelector: PlacesFieldSelector = PlacesFieldSelector()
    private val PLACE_PICKER_REQUEST = 1
    var latitude : Double = 0.0
    var longitude : Double = 0.0
    var address : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_intro)
        ButterKnife.bind(this)

        Utils.hideSoftKeyboard(this@EditIntroActivity)

        setToolbar()
        getIntentData()
        loadInterest()

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_places_api_key))
        }

        setSuggestedCause()
        edit_cause.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onCauseTyping(s)
            }
        })
    }

    @OnClick(R.id.image_profile)
    internal fun chooseImage() {
        showImageChooserDialog()
    }

    @OnClick(R.id.edit_location)
    fun locationTapped() {
        val autocompleteIntent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placesFieldSelector.getAllFields()).build(this)
        startActivityForResult(autocompleteIntent, PLACE_PICKER_REQUEST)
    }

    @OnClick(R.id.button_submit)
    fun submitTapped() {

        if (TextUtils.isEmpty(edit_first_name.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, resources.getString(R.string.error_first_name_cannot_be_empty))
            return
        }

        if (TextUtils.isEmpty(edit_last_name.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, resources.getString(R.string.error_last_name_canot_be_empty))
            return
        }

        if (TextUtils.isEmpty(edit_recent_job_title.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, resources.getString(R.string.error_job_title_cannot_be_empty))
            return
        }

        if (TextUtils.isEmpty(edit_location.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, resources.getString(R.string.eror_location_cannot_be_empty))
            return
        }

        if (TextUtils.isEmpty(edit_headline.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, resources.getString(R.string.error_headline_cannot_be_empty))
            return
        }

        var editIntroInfoRequest = EditIntroInfoRequest(edit_first_name.text.toString(),
            edit_headline.text.toString(),
            mInterestAdapter.getSelectedIds(),
            edit_last_name.text.toString(),
            latitude.toString() , longitude.toString(), edit_location.text.toString(), AppPreference.getInstance(this).getString(PREF_CATEGORY), AppPreference.getInstance(this).getString(PREF_USER_ID), manaualInterest, profileImage)

        val dialog = CustomProgressBar.getInstance(this).showProgressDialog("Updating Profile...")
        dialog.show()

        DataManager.updateIntroInfo(this, editIntroInfoRequest, object : ResponseListener<EditIntroInfoResponse> {
            override fun onSuccess(response: EditIntroInfoResponse) {
                dialog.dismiss()
                Toast.makeText(this@EditIntroActivity, "Profile updated successfully", Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onError(error: Any) {
                dialog.dismiss()
            }
        })
    }

    private fun showImageChooserDialog() {
        galleryPicker = GalleryPicker.with(this).setListener(this@EditIntroActivity)
            .showDialog()
    }

    private fun getIntentData() {
        profileResponse = intent?.extras?.getSerializable(UserName) as ProfileResponse
        if (profileResponse != null) {
            val data = profileResponse?.data?.get(0)

            edit_first_name.setText(data?.profile?.first_name)
            edit_last_name.setText(data?.profile?.last_name)
            edit_recent_job_title.setText(data?.type)
            edit_location.setText(data?.state)
            edit_headline.setText(data?.headline)
            edit_location.setText(data?.city)

            listSelected = data?.interests as ArrayList<Education>

            if (!data.profile.profile_image.isNullOrEmpty()) {
                Picasso.get().load(data.profile.profile_image).error(R.drawable.profile_placeholder).into(image_profile)
                profileImage = null
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) galleryPicker?.fetch(requestCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PLACE_PICKER_REQUEST) run {
            when (requestCode) {
                PLACE_PICKER_REQUEST -> {
                  var place = Autocomplete.getPlaceFromIntent(data!!)

                    edit_location.setText(place.address.toString())
                    latitude = place.latLng!!.latitude
                    longitude = place.latLng!!.longitude
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        galleryPicker?.onResultPermission(requestCode, grantResults)
    }

    override fun onMediaSelected(imagePath: String, uri: Uri, isImage: Boolean) {
        image_profile.setImageURI(uri)
        text_profile.text = resources.getString(R.string.edit_profile_image)
        image_add_edit.setImageResource(R.drawable.ic_signup_edit_image)
        uploadImage(imagePath, constraintLayout)
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

    private fun validateSelectedItem(): Boolean {
        var b = manaualInterest?.size == 5
        if (b) {
            scrollView.post { Utils.hideSoftKeyboard(this) }
            SnackBarFactory.createSnackBar(this, scrollView, getString(R.string.limit_suggest_interest))
        }
        return b
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSuggestedCause() {
        initManualList()
        edit_cause.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= edit_cause.right - edit_cause.totalPaddingRight) {
                    val suggestedCause = edit_cause.text.toString().trim()
                    if (!suggestedCause.isEmpty()) {
                        if(manaualInterest == null) {
                            manaualInterest = mutableListOf()
                        }
                        val view = LayoutInflater.from(this).inflate(R.layout.item_cause, null)
                        val textView = view.findViewById<TextView>(R.id.text_suggest_cause)
                        textView.text = suggestedCause

                        val imageCross = view.findViewById<ImageView>(R.id.image_cross)
                        imageCross.setOnClickListener { _ ->
                            flex_layout_cause.removeView(view)
                            manaualInterest?.remove(textView.text.toString())
                        }

                        val lp = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        lp.setMargins(10, 10, 10, 10)
                        if(validateSelectedItem()) {
                            return@setOnTouchListener false
                        }
                        manaualInterest?.add(suggestedCause)
                        flex_layout_cause.addView(view, lp)
                        edit_cause.setText("")
                        scrollView.post {
                            scrollView.scrollTo(0, scrollView.height)
                            Utils.hideSoftKeyboard(this)
                        }
                    }
                }
            }
            false
        }
    }

    private fun initManualList() {
        if(manaualInterest.isNullOrEmpty()) {
            manaualInterest?.forEach {
                if(manaualInterest == null) {
                    manaualInterest = mutableListOf()
                }
                val view = LayoutInflater.from(this).inflate(R.layout.item_cause, null)
                val textView = view.findViewById<TextView>(R.id.text_suggest_cause)
                val imageCross = view.findViewById<ImageView>(R.id.image_cross)
                textView.text = it
                imageCross.setOnClickListener { _ ->
                    flex_layout_cause.removeView(view)
                    interests?.remove(textView.text.toString())
                    manaualInterest?.remove(textView.text.toString())
                }
                val lp = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                lp.setMargins(10, 10, 10, 10)
                flex_layout_cause.addView(view, lp)
                edit_cause.setText("")
                scrollView.post { scrollView.scrollTo(0, scrollView.height) }
            }
        }
    }

    internal fun onCauseTyping(s: CharSequence?) {
        edit_cause.setCompoundDrawablesWithIntrinsicBounds(
            0, 0,
            if (s?.length != 0) R.drawable.ic_signup_add_interest else R.drawable.ic_signup_add_interest_deselect, 0
        )
    }

    protected fun uploadImage(imagePath: String, view: View) {

        val dialog = CustomProgressBar.getInstance(this).showProgressDialog("Uploading picture...")
        dialog.show()

        val file = File(imagePath)
        val requestFile = RequestBody.create(MediaType.parse(AppConstant.MILTI_PART_FORM_DATA), file)
        val body = MultipartBody.Part.createFormData(AppConstant.IMAGE_PARAM, file.name, requestFile)
        val type = MultipartBody.Part.createFormData(AppConstant.TYPE, "USER")

        val imageUploadRequest = ImageUploadRequest(body, type)
        SignUpRepo.uploadImage(this, imageUploadRequest, object : ResponseListener<ImageUploadResponse> {
            override fun onSuccess(response: ImageUploadResponse) {
                profileImage = response.data[0].name
                dialog.dismiss()
            }

            override fun onError(error: Any) {
                Utils.showError(this@EditIntroActivity, view, error)
                dialog.dismiss()
            }
        })
    }
}

package com.communityx.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.AutoSuggestAdapter
import com.communityx.models.job_companies.Data
import com.communityx.models.profile.Education
import com.communityx.models.signup.DataX
import com.communityx.models.signup.RoleData
import com.communityx.models.signup.RoleResponse
import com.communityx.models.signup.institute.CompanyRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.places.PlacesFieldSelector
import com.communityx.utils.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_add_experience.*
import kotlinx.android.synthetic.main.fragment_sign_up_professional.*
import kotlinx.android.synthetic.main.toolbar.*

class AddExperienceActivity : BaseActivity() {

    private val TRIGGER_AUTO_COMPLETE = 100
    private lateinit var education: Education
    private lateinit var autoSuggestCompanyAdapter: AutoSuggestAdapter
    private lateinit var autoSuggestTitleAdapter: AutoSuggestAdapter
    private val AUTO_COMPLETE_DELAY: Long = 300
    private lateinit var handler: Handler
    var msg: String = ""
    private var placesFieldSelector: PlacesFieldSelector = PlacesFieldSelector()
    private val PLACE_PICKER_REQUEST = 1
    var latitude: String = "0.0"
    var longitude: String = "0.0"
    var isCompanyAdded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_experience)
        ButterKnife.bind(this)

        if (intent.extras!=null) {
            isCompanyAdded = intent.extras.getBoolean("isAdded")
        }

        Utils.hideSoftKeyboard(this@AddExperienceActivity)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_places_api_key))
        }

        setToolbar()
        getIntentData()
        setAutoCompanyName()
        setAutoJobTitle()
    }

    @OnClick(R.id.edit_start_date)
    fun tappedStartDate() {
        openDate(true)
    }

    @OnClick(R.id.edit_end_date)
    fun tappedEndDate() {
        openDate(false)
    }

    fun openDate(isStartdate: Boolean) {
        Utils.openDatePickerDialog(this@AddExperienceActivity, object : Utils.IDateCallback {
            override fun getDate(date: String?) {
                if (isStartdate) {
                    edit_start_date.setText(date)
                    edit_start_date.setTextColor(ContextCompat.getColor(this@AddExperienceActivity, R.color.Black))
                } else {
                    edit_end_date.setText(date)
                    edit_end_date.setTextColor(ContextCompat.getColor(this@AddExperienceActivity, R.color.Black))
                    textinput_description.requestFocus()
                }
            }
        })
    }

    @OnClick(R.id.imageView)
    fun closeTapped() {
        finish()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
    }

    @OnClick(R.id.auto_edit_location)
    fun locationTapped() {
        val autocompleteIntent =
            Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placesFieldSelector.allFields)
                .build(this)
        startActivityForResult(autocompleteIntent, PLACE_PICKER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PLACE_PICKER_REQUEST) run {
            when (requestCode) {
                PLACE_PICKER_REQUEST -> {
                    var place = Autocomplete.getPlaceFromIntent(data!!)
                    auto_edit_location.setText(place.address.toString())

                    latitude = place.latLng?.latitude.toString()
                    longitude = place.latLng?.longitude.toString()
                }
            }
        }
    }

    private fun setToolbar() {
        image_tail_toolbar.visibility = View.GONE
        text_title.text = getString(R.string.string_add_work_exp)
        imageView.setImageDrawable(resources.getDrawable(R.drawable.image_close_white))
    }

    fun getIntentData() {
        if (intent.hasExtra("data")) {
            education = intent.getSerializableExtra("data") as Education
            setUpData(education)
        }
    }

    private fun setUpData(education: Education) {
        auto_company_name.setText(education.name)
        auto_title.setText(education.role)

        if (education.name != null)
            auto_company_name.setSelection(education.name.length)

        if (education.role != null)
            auto_title.setSelection(education.role.length)
    }

    private fun setAutoCompanyName() {
        autoSuggestCompanyAdapter =
            AutoSuggestAdapter(this@AddExperienceActivity, android.R.layout.simple_dropdown_item_1line)
        auto_company_name.threshold = 1
        auto_company_name.setAdapter(autoSuggestCompanyAdapter)

        auto_company_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE)
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        handler = Handler(object : Handler.Callback {

            override fun handleMessage(msg: Message): Boolean {
                if (msg.what === TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(auto_company_name.text)) {
                        getCompaniesdata(auto_company_name.text.toString())
                    }
                }
                return false
            }
        })
    }

    private fun getCompaniesdata(query: String) {
        SignUpRepo.getCompanies(query, object : ResponseListener<List<Data>> {
            override fun onSuccess(response: List<Data>) {
                setCompaniesData(response)
            }

            override fun onError(error: Any) {
            }
        })
    }

    private fun setCompaniesData(jobs: List<Data>) {
        val jobsList = mutableListOf<String>()
        jobs.forEach {
            jobsList.add(it.name)
        }
        autoSuggestCompanyAdapter.setData(jobsList)
        autoSuggestCompanyAdapter.notifyDataSetChanged()
    }

    private fun setAutoJobTitle() {
        autoSuggestTitleAdapter =
            AutoSuggestAdapter(this@AddExperienceActivity, android.R.layout.simple_dropdown_item_1line)
        auto_title.threshold = 1
        auto_title.setAdapter(autoSuggestTitleAdapter)

        auto_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(auto_title.text)) {
                    getJobRole(auto_title.text.toString())
                }
            }
        })
    }

    private fun getJobRole(query: String) {
        var type = resources.getString(R.string.COMPANY)
        SignUpRepo.getRoles(type, object : ResponseListener<RoleResponse> {
            override fun onSuccess(response: RoleResponse) {
                setSpinnerJobData(response.data)
            }

            override fun onError(error: Any) {
                Utils.showError(this@AddExperienceActivity, coordinator_main, error)
            }
        })
    }

    private fun setSpinnerJobData(jobs: List<RoleData>) {
        val jobsList = mutableListOf<String>()
        jobs.forEach {
            jobsList.add(it.name)
        }
        autoSuggestTitleAdapter.setData(jobsList)
        autoSuggestTitleAdapter.notifyDataSetChanged()
    }

    private fun validate(): Boolean {

        if (TextUtils.isEmpty(auto_company_name.text.trim())) {
            SnackBarFactory.createSnackBar(
                this,
                coordinator_main_exp,
                resources.getString(R.string.error_company_cannot_be_empty)
            )
            return false
        }

        if (TextUtils.isEmpty(auto_title.text.trim())) {
            SnackBarFactory.createSnackBar(
                this,
                coordinator_main_exp,
                resources.getString(R.string.error_title_cannot_be_empty)
            )
            return false
        }

        if (TextUtils.isEmpty(auto_edit_location.text?.trim())) {
            SnackBarFactory.createSnackBar(
                this,
                coordinator_main_exp,
                resources.getString(R.string.error_location_cannot_be_empty)
            )
            return false
        }

        if (TextUtils.isEmpty(edit_start_date.text)) {
            SnackBarFactory.createSnackBar(
                this,
                coordinator_main_exp,
                resources.getString(R.string.error_start_date_cannot_be_empty)
            )
            return false
        }

        if (TextUtils.isEmpty(edit_end_date.text)) {
            SnackBarFactory.createSnackBar(
                this,
                coordinator_main_exp,
                resources.getString(R.string.error_end_date_cannot_be_empty)
            )
            return false
        }

        if (TextUtils.isEmpty(textinput_description.text?.trim())) {
            SnackBarFactory.createSnackBar(
                this,
                coordinator_main_exp,
                resources.getString(R.string.error_description_cannot_be_empty)
            )
            return false
        }

        return true
    }

    @OnClick(R.id.button_save)
    fun SaveTapped() {
        if (validate()) {
            if (isCompanyAdded) {
                newCompanyAdded()
            } else {
                onSubmit()
            }
        }
    }

    private fun onSubmit() {
        var companyName = auto_company_name.text.toString().trim()
        var autoTitle = auto_title.text.toString().trim()
        var userId = AppPreference.getInstance(this).getString(AppConstant.PREF_USER_ID)
        var companyId = education.id
        var startDate = edit_start_date.text.toString().trim()
        var endDate = edit_end_date.text.toString().trim()
        var inputDescription = textinput_description.text.toString().trim()
        var address = auto_edit_location.text.toString().trim()

        msg = if (check_work_here.isChecked) "1" else "0"

        //TODO : hard coded location
        var editExperienceInfoRequest = CompanyRequest(
            companyName, autoTitle, userId, companyId, address,
            latitude, longitude,
            startDate,
            endDate,
            inputDescription,
            msg
        )

        val dialog =
            CustomProgressBar.getInstance(this).showProgressDialog(resources.getString(R.string.upadating_experience))
        dialog.show()
        SignUpRepo.UpdateCompany(this, editExperienceInfoRequest, object : ResponseListener<List<DataX>> {

            override fun onSuccess(response: List<DataX>) {
                dialog.dismiss()

                finish()
                overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
                Toast.makeText(
                    this@AddExperienceActivity,
                    resources.getString(R.string.string_update_experience),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(error: Any) {
                Utils.showError(this@AddExperienceActivity, coordinator_main_exp, error)
                dialog.dismiss()
            }
        })
    }

    private fun newCompanyAdded() {
        var companyName = auto_company_name.text.toString().trim()
        var autoTitle = auto_title.text.toString().trim()
        var startDate = edit_start_date.text.toString().trim()
        var endDate = edit_end_date.text.toString().trim()
        var inputDescription = textinput_description.text.toString().trim()
        var address = auto_edit_location.text.toString().trim()

        msg = if (check_work_here.isChecked) "1" else "0"

        //TODO : hard coded location
        var editExperienceInfoRequest = CompanyRequest(
            companyName, autoTitle, "", "", address,
            latitude, longitude,
            startDate,
            endDate,
            inputDescription,
            msg
        )

        val dialog = CustomProgressBar.getInstance(this).showProgressDialog(resources.getString(R.string.upadating_experience))
        dialog.show()
        SignUpRepo.addCompany(this, editExperienceInfoRequest, object : ResponseListener<List<DataX>> {

            override fun onSuccess(response: List<DataX>) {
                dialog.dismiss()

                finish()
                overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
                Toast.makeText(
                    this@AddExperienceActivity,
                    resources.getString(R.string.string_update_experience),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(error: Any) {
                Utils.showError(this@AddExperienceActivity, coordinator_main_exp, error)
                dialog.dismiss()
            }
        })
    }
}

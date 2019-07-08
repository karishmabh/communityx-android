package com.communityx.activity

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.AutoSuggestAdapter
import com.communityx.models.editintroinfo.EditIntroInfoRequest
import com.communityx.models.job_companies.Data
import com.communityx.models.profile.Education
import com.communityx.models.signup.DataX
import com.communityx.models.signup.RoleData
import com.communityx.models.signup.RoleResponse
import com.communityx.models.signup.institute.CompanyRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.*
import com.communityx.utils.AppConstant.PREF_USER_ID
import com.communityx.utils.Utils.enableButton
import kotlinx.android.synthetic.main.activity_add_experience.*
import kotlinx.android.synthetic.main.activity_edit_education.*
import kotlinx.android.synthetic.main.activity_edit_intro.*
import kotlinx.android.synthetic.main.fragment_sign_up_professional.*
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.*
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.constraintLayout
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.edit_first_name
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.edit_last_name
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.image_profile
import kotlinx.android.synthetic.main.toolbar.*

class AddExperienceActivity : AppCompatActivity() {

    private val TRIGGER_AUTO_COMPLETE = 100
    private lateinit var education: Education
    private lateinit var autoSuggestCompanyAdapter: AutoSuggestAdapter
    private lateinit var autoSuggestTitleAdapter: AutoSuggestAdapter
    private val AUTO_COMPLETE_DELAY: Long = 300
    private lateinit var handler: Handler
    var msg: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_experience)
        ButterKnife.bind(this)

        setToolbar()
        getIntentData()
        setAutoCompanyName()
        setAutoJobTitle()

        val checkBox = findViewById<CheckBox>(R.id.check_work_here)
        if (checkBox.isChecked) {
            msg = "1"
        } else
            msg = "0"

    }

    @OnClick(R.id.edit_start_date)
        fun tappedStartDate(){
        Utils.hideSoftKeyboard(this@AddExperienceActivity)
        Utils.openDatePickerDialog(this@AddExperienceActivity, object : Utils.IDateCallback {
            override fun getDate(date: String?) {
                edit_start_date.setText(date)
                edit_start_date.setTextColor(ContextCompat.getColor(this@AddExperienceActivity,R.color.Black))
                edit_end_date.requestFocus()
            }
        })
    }

    @OnClick(R.id.edit_end_date)
    fun tappedEndDate(){
        Utils.hideSoftKeyboard(this@AddExperienceActivity)
        Utils.openDatePickerDialog(this@AddExperienceActivity, object : Utils.IDateCallback {
            override fun getDate(date: String?) {
                edit_end_date.setText(date)
                edit_end_date.setTextColor(ContextCompat.getColor(this@AddExperienceActivity,R.color.Black))
                edit_end_date.requestFocus()
                textinput_description.requestFocus()
            }
        })
    }


    @OnClick(R.id.imageView)
    fun closeTapped() {
        finish()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
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
    }

    private fun setAutoCompanyName() {
        autoSuggestCompanyAdapter = AutoSuggestAdapter(this@AddExperienceActivity, android.R.layout.simple_dropdown_item_1line)
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
        autoSuggestTitleAdapter = AutoSuggestAdapter(this@AddExperienceActivity, android.R.layout.simple_dropdown_item_1line)
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
        var type = "COMPANY"
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

    @OnClick(R.id.button_login)
    fun tapped(){

        if (TextUtils.isEmpty(auto_company_name.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, "Company name cannot be empty.")
            return
        }

        if (TextUtils.isEmpty(auto_title.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, "Job Title cannot be empty.")
            return
        }

        if (TextUtils.isEmpty(auto_edit_location.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, "Location cannot be empty.")
            return
        }

        if (TextUtils.isEmpty(edit_start_date.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, "Start Date cannot be empty.")
            return
        }

        if (TextUtils.isEmpty(edit_end_date.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, "End Date cannot be empty.")
            return
        }

        if (TextUtils.isEmpty(textinput_description.text)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, "Description cannot be empty.")
            return
        }

        var editExperienceInfoRequest = CompanyRequest(auto_company_name.text.toString(),
            auto_title.text.toString(),
            AppPreference.getInstance(this).getString(
                AppConstant.PREF_USER_ID),
            "Kempton",
            "22.364154", "70.864516",
            edit_start_date.text.toString(),
            edit_end_date.text.toString(),
            textinput_description.text.toString(),
            msg
            )
        val dialog = CustomProgressBar.getInstance(this).showProgressDialog("Updating Experience...")
        dialog.show()
        SignUpRepo.addCompany(this,editExperienceInfoRequest, object : ResponseListener<List<DataX>> {
            override fun onSuccess(response: List<DataX>) {
                finish()
                overridePendingTransition(R.anim.anim_stay,R.anim.anim_slide_down)
                Toast.makeText(this@AddExperienceActivity, resources.getString(R.string.string_update_experience), Toast.LENGTH_SHORT).show()
                dialog.dismiss();
            }

            override fun onError(error: Any) {

                Utils.showError(this@AddExperienceActivity, coordinator_main, error)
                dialog.dismiss();
            }
        })

    }
}

package com.communityx.activity


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import android.content.Intent
import android.widget.Toast
import com.communityx.models.profile.Education
import com.communityx.models.signup.DataX
import com.communityx.models.signup.institute.InstituteRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant
import com.communityx.utils.AppPreference
import com.communityx.utils.CustomProgressBar
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_edit_education.*
import kotlinx.android.synthetic.main.activity_edit_education.edit_college_name
import kotlinx.android.synthetic.main.activity_edit_education.edit_school_name
import kotlinx.android.synthetic.main.activity_edit_education.image_freshman
import kotlinx.android.synthetic.main.activity_edit_education.image_freshman_tick
import kotlinx.android.synthetic.main.activity_edit_education.image_junior
import kotlinx.android.synthetic.main.activity_edit_education.image_junior_tick
import kotlinx.android.synthetic.main.activity_edit_education.image_senior
import kotlinx.android.synthetic.main.activity_edit_education.image_senior_tick
import kotlinx.android.synthetic.main.activity_edit_education.image_sophomore
import kotlinx.android.synthetic.main.activity_edit_education.image_sophomore_tick
import kotlinx.android.synthetic.main.activity_edit_education.layout_college
import kotlinx.android.synthetic.main.activity_edit_education.layout_school
import kotlinx.android.synthetic.main.activity_edit_education.text_freshman
import kotlinx.android.synthetic.main.activity_edit_education.text_junior
import kotlinx.android.synthetic.main.activity_edit_education.text_senior
import kotlinx.android.synthetic.main.activity_edit_education.text_sophomore
import kotlinx.android.synthetic.main.activity_edit_education.view_college
import kotlinx.android.synthetic.main.activity_edit_education.view_freshman_main
import kotlinx.android.synthetic.main.activity_edit_education.view_junior_main
import kotlinx.android.synthetic.main.activity_edit_education.view_school
import kotlinx.android.synthetic.main.activity_edit_education.view_senior_main
import kotlinx.android.synthetic.main.activity_edit_education.view_sophomore_main

class EditEducationActivity : BaseActivity() {

    var role_Selected :String? = null
    private lateinit var education: Education
    var clickContinue : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_education)
        ButterKnife.bind(this)
        Utils.hideSoftKeyboard(this@EditEducationActivity)

        setToolBar(this, getString(R.string.string_edit_education), true, true)

        getIntentData()

        edit_college_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && edit_college_name.text.isNotEmpty()) {
                    button_save.isClickable = true
                    button_save.setBackgroundResource(R.drawable.button_active)
                } else{
                    button_save.isClickable = false
                    button_save.setBackgroundResource(R.drawable.button_inactive)
                }
            }
        })

        edit_school_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && edit_school_name.text.isNotEmpty()) {
                    button_save.isClickable = true
                    button_save.setBackgroundResource(R.drawable.button_active)
                } else{
                    button_save.isClickable = false
                    button_save.setBackgroundResource(R.drawable.button_inactive)
                }
            }
        })

    }

    @OnClick(R.id.imageView)
    fun closeTapped() {
        finish()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
    }

    fun getIntentData() {
        education = intent.getSerializableExtra("data") as Education
        setUpData(education)
    }
    @OnClick(R.id.button_save)
    fun savedata(){

        var institute_name : String? = null

        if (education.type == "COLLEGE_UNIVERSITY") {
           institute_name = edit_college_name.text.toString()
        } else {
            institute_name = edit_school_name.text.toString()
        }

        val instituteRequest = InstituteRequest(institute_name, role_Selected,
                education.type,
                AppPreference.getInstance(this).getString(AppConstant.PREF_USER_ID))


        SignUpRepo.addInstitute(this,  instituteRequest, object : ResponseListener<List<DataX>> {

            var dialog =  CustomProgressBar.getInstance(this@EditEducationActivity).showProgressDialog("Profile updating..")
            override fun onSuccess(response: List<DataX>) {
                dialog.dismiss()
                finish()
                overridePendingTransition(R.anim.anim_stay,R.anim.anim_slide_down)
                Toast.makeText(this@EditEducationActivity, resources.getString(R.string.string_update_profile), Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Any) {
                clickContinue = false
                dialog.dismiss()
                Utils.showError(this@EditEducationActivity, constraint_layout, error)
            }
        })
    }


    @OnClick(R.id.view_freshman_main)
    fun freshmanTapped() {
        role_Selected = "FRESHMAN"
        view_freshman_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.border_orange_bg)
        view_sophomore_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
        view_junior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
        view_senior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)

        image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)
        image_freshman.setImageResource(R.drawable.ic_signup_freshman_select)
        image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
        image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)

        text_freshman.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorBlackTitle))
        text_sophomore.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
        text_junior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
        text_senior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))

        image_senior_tick.visibility = View.GONE
        image_freshman_tick.visibility = View.VISIBLE
        image_sophomore_tick.visibility = View.GONE
        image_junior_tick.visibility = View.GONE
    }

    @OnClick(R.id.view_sophomore_main)
    fun sophomoreTapped() {
        role_Selected = "SOPHOMORE"
        view_senior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
        view_freshman_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
        view_sophomore_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.border_orange_bg)
        view_junior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)

        image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)
        image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
        image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_select)
        image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)

        text_senior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
        text_freshman.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
        text_sophomore.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorBlackTitle))
        text_junior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))

        image_senior_tick.visibility = View.GONE
        image_freshman_tick.visibility = View.GONE
        image_sophomore_tick.visibility = View.VISIBLE
        image_junior_tick.visibility = View.GONE
    }

    @OnClick(R.id.view_junior_main)
    fun juniorTapped() {
        role_Selected = "JUNIOR"
        view_senior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
        view_freshman_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
        view_sophomore_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
        view_junior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.border_orange_bg)

        image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)
        image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
        image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
        image_junior.setImageResource(R.drawable.ic_signup_junior_select)

        text_senior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
        text_freshman.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
        text_sophomore.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
        text_junior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorBlackTitle))

        image_senior_tick.visibility = View.GONE
        image_freshman_tick.visibility = View.GONE
        image_sophomore_tick.visibility = View.GONE
        image_junior_tick.visibility = View.VISIBLE
    }

    @OnClick(R.id.view_senior_main)
    fun seniorTapped() {
        role_Selected = "SENIOR"
        view_senior_main.background = ContextCompat.getDrawable(this@EditEducationActivity ,R.drawable.border_orange_bg)
        view_freshman_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
        view_sophomore_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
        view_junior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)

        image_senior.setImageResource(R.drawable.ic_signup_senior_select)
        image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
        image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
        image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)

        text_senior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorBlackTitle))
        text_freshman.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
        text_sophomore.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
        text_junior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))

        image_senior_tick.visibility = View.VISIBLE
        image_freshman_tick.visibility = View.GONE
        image_sophomore_tick.visibility = View.GONE
        image_junior_tick.visibility = View.GONE
    }

    private fun setUpData(education: Education) {

        if (education.type == "COLLEGE_UNIVERSITY") {
            view_school.visibility = View.GONE
            layout_college.visibility = View.VISIBLE
            edit_college_name.setText(education.name)
        } else {
            view_college.visibility = View.GONE
            layout_school.visibility = View.VISIBLE
            edit_school_name.setText(education.name)
        }

        if (education.role == "SENIOR") {
            role_Selected = "SENIOR"
            view_senior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.border_orange_bg)
            view_freshman_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
            view_sophomore_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
            view_junior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)

            image_senior.setImageResource(R.drawable.ic_signup_senior_select)
            image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
            image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
            image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)

            text_senior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorBlackTitle))
            text_freshman.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
            text_sophomore.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
            text_junior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))

            image_senior_tick.visibility = View.VISIBLE
            image_freshman_tick.visibility = View.GONE
            image_sophomore_tick.visibility = View.GONE
            image_junior_tick.visibility = View.GONE

            if (image_senior == image_senior) {
                image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
                image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
                image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)
                image_senior.setImageResource(R.drawable.ic_signup_senior_select)

            }

        } else if (education.role == "FRESHMAN") {
            role_Selected = "FRESHMAN"
            view_freshman_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.border_orange_bg)
            view_sophomore_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
            view_junior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
            view_senior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)

            image_freshman.setImageResource(R.drawable.ic_signup_freshman_select)
            image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
            image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)
            image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

            text_freshman.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorBlackTitle))
            text_sophomore.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
            text_junior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
            text_senior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))

            image_freshman_tick.visibility = View.VISIBLE
            image_sophomore_tick.visibility = View.GONE
            image_junior_tick.visibility = View.GONE
            image_senior_tick.visibility = View.GONE

            if (image_freshman == image_freshman) {
                image_freshman.setImageResource(R.drawable.ic_signup_freshman_select)
                image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
                image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)
                image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)
            }


        } else if (education.role == "SOPHOMORE") {
            role_Selected = "SOPHOMORE"

            view_sophomore_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.border_orange_bg)
            view_freshman_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
            view_junior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
            view_senior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)

            image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_select)
            image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
            image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)
            image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

            text_sophomore.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorBlackTitle))
            text_freshman.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
            text_junior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
            text_senior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))

            image_sophomore_tick.visibility = View.VISIBLE
            image_freshman_tick.visibility = View.GONE
            image_junior_tick.visibility = View.GONE
            image_senior_tick.visibility = View.GONE

            if (image_sophomore_tick == image_freshman) {
                image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
                image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_select)
                image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)
                image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)
            }
        } else if (education.role == "JUNIOR") {
            role_Selected = "JUNIOR"

            view_junior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.border_orange_bg)
            view_freshman_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
            view_sophomore_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)
            view_senior_main.background = ContextCompat.getDrawable(this@EditEducationActivity,R.drawable.bordered_bg)

            image_junior.setImageResource(R.drawable.ic_signup_junior_select)
            image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
            image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
            image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

            text_junior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorBlackTitle))
            text_freshman.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
            text_sophomore.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))
            text_senior.setTextColor(ContextCompat.getColor(this@EditEducationActivity,R.color.colorLightestGrey))

            image_junior_tick.visibility = View.VISIBLE
            image_freshman_tick.visibility = View.GONE
            image_sophomore_tick.visibility = View.GONE
            image_senior_tick.visibility = View.GONE

            if (image_junior == image_junior)
                image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
            image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
            image_junior.setImageResource(R.drawable.ic_signup_junior_select)
            image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

        }

    }
}

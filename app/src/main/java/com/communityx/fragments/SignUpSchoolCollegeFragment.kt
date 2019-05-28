package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.utils.AppConstant.COLLEGE
import com.communityx.utils.AppConstant.HIGH_SCHOOL
import com.communityx.utils.SnackBarFactory
import kotlinx.android.synthetic.main.fragment_sign_up_school_college.*
import java.util.*

class SignUpSchoolCollegeFragment : BaseSignUpFragment(), View.OnClickListener {

    private var standard: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_school_college, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initField()
        view_school.setOnClickListener(this)
        view_college.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        tappedQualificationInfo(v!!)
    }

    override fun onContinueButtonClicked() {
        if (setFieldsData()) goToNextPage()
    }

    override fun setFieldsData(): Boolean {
        signUpRequest?.standard = standard
        signUpRequest?.standard_name =
            if (standard == HIGH_SCHOOL) edit_school_name.text.toString() else edit_college_name.text.toString()

        return validateEmpty(signUpRequest)
    }

    override fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean): Boolean {
        var isValidated = true
        var msg = ""
        when {
            TextUtils.isEmpty(requestData?.standard) -> {
                isValidated = false
                msg = getString(R.string.select_at_least_one_category)
            }
            TextUtils.isEmpty(requestData?.standard_name) -> {
                isValidated = false
                msg = getString(R.string.please_fill_school_college)
            }
        }
        if (!isValidated && showSnackbar) SnackBarFactory.createSnackBar(context, constraint_layout, msg)
        return isValidated
    }

    private fun initField() {
        if (validateEmpty(signUpRequest, false)) {
            if (standard == HIGH_SCHOOL) {
                edit_school_name.setText(signUpRequest?.standard_name)
                tappedQualificationInfo(view_school)
            } else if (standard == COLLEGE) {
                edit_college_name.setText(signUpRequest?.standard_name)
                tappedQualificationInfo(view_college)
            }
            enableButton(false)
        }
    }

    private fun tappedQualificationInfo(it: View) {
        if (it == view_school)
            selectQualificationInfo(Qualification.SCHOOL)
        else if (it == view_college) selectQualificationInfo(Qualification.COLLEGE)
    }

    private fun selectQualificationInfo(qualification: Qualification) {
        (Objects.requireNonNull<FragmentActivity>(activity) as SignUpStudentInfoActivity).enableButton(true)

        when (qualification) {
            SignUpSchoolCollegeFragment.Qualification.SCHOOL -> {
                view_school!!.background = resources.getDrawable(R.drawable.border_orange_bg)
                view_college!!.background = resources.getDrawable(R.drawable.bordered_bg)

                image_school!!.setImageResource(R.drawable.ic_signup_highschool_select)
                image_college!!.setImageResource(R.drawable.ic_signup_college_deselect)

                text_school!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_college!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                image_school_tick!!.visibility = View.VISIBLE
                image_college_tick!!.visibility = View.GONE

                layout_school!!.visibility = View.VISIBLE
                layout_college!!.visibility = View.GONE

                standard = HIGH_SCHOOL
                edit_school_name.requestFocus()
            }

            SignUpSchoolCollegeFragment.Qualification.COLLEGE -> {
                view_college!!.background = resources.getDrawable(R.drawable.border_orange_bg)
                view_school!!.background = resources.getDrawable(R.drawable.bordered_bg)

                image_college!!.setImageResource(R.drawable.ic_signup_college_select)
                image_school!!.setImageResource(R.drawable.ic_signup_highschool_deselect)

                text_college!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_school!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                image_college_tick!!.visibility = View.VISIBLE
                image_school_tick!!.visibility = View.GONE

                layout_college!!.visibility = View.VISIBLE
                layout_school!!.visibility = View.GONE

                standard = COLLEGE
                edit_college_name.requestFocus()
            }
        }
    }

    private enum class Qualification {
        SCHOOL,
        COLLEGE
    }
}
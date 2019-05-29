package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.communityx.R
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.utils.SnackBarFactory
import kotlinx.android.synthetic.main.fragment_sign_up_professional.*

class SignUpProfessional : BaseSignUpFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_professional, container, false)
    }

    private fun getData() {
        if(TextUtils.isEmpty(edit_job_title.text.toString())) {
            SnackBarFactory.createSnackBar(activity!!, coordinator_main, "Please enter Job Title")
        }

        if(TextUtils.isEmpty(edit_recent_company.text.toString())) {
            SnackBarFactory.createSnackBar(activity!!, coordinator_main, "Please enter recent company")
        }
    }

    override fun setFieldsData(): Boolean {
        signUpStudent?.job_title = edit_job_title.text.toString()
        signUpStudent?.company_name = edit_recent_company.text.toString()
        return validateEmpty(signUpStudent)
    }

    override fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean): Boolean {
        var isValidated = true
        var msg = ""
        when {
            TextUtils.isEmpty(requestData?.job_title) -> {
                msg = getString(R.string.job_title_required)
                isValidated = false
            }
            TextUtils.isEmpty(requestData?.company_name) -> {
                msg = getString(R.string.company_field_required)
                isValidated = false
            }
        }
        if(!isValidated && showSnackbar) SnackBarFactory.createSnackBar(context,coordinator_main,msg)
        return  isValidated
    }

    override fun onContinueButtonClicked() {
        if(setFieldsData()) goToNextPage()
    }
}

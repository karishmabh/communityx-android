package com.communityx.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.communityx.R
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.utils.AppConstant.EMAIL_PATTERN
import com.communityx.utils.SnackBarFactory
import kotlinx.android.synthetic.main.fragment_sign_up_organization.*

class SignUpOrganizationFragment : BaseSignUpFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_organization, container, false)
        return view
    }

    override fun setFieldsData(): Boolean {
        signUpActivity?.signUpRequest?.name = edit_organization.text.toString()
        signUpActivity?.signUpRequest?.email = edit_email.text.toString()
        signUpActivity?.signUpRequest?.organization_website = edit_website.text.toString()
        signUpActivity?.signUpRequest?.postal_code = edit_postalcode.text.toString()

        return validateEmpty(signUpActivity?.signUpRequest)
    }

    override fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean): Boolean {
        var error_message = ""
        var isValidate = true

        when {
            TextUtils.isEmpty(edit_organization.text.toString()) -> {
                isValidate = false
                error_message = getString(R.string.string_organization_cannot_be_empty)
                if (showSnackbar) edit_organization.requestFocus()
            }

            TextUtils.isEmpty(edit_email.text.toString()) -> {
                isValidate = false
                error_message = getString(R.string.string_email_cannot_be_empty)
                if (showSnackbar) edit_email.requestFocus()
            }

            !edit_email.text.toString().matches(EMAIL_PATTERN.toRegex()) -> {
                isValidate = false
                error_message = getString(R.string.string_enter_valid_email_address)
                if (showSnackbar) edit_email.requestFocus()
            }

            TextUtils.isEmpty(edit_website.text.toString()) -> {
                isValidate = false
                error_message = getString(R.string.string_website_cannot_be_empty)
                if (showSnackbar) edit_website.requestFocus()
            }

            TextUtils.isEmpty(edit_postalcode.text.toString()) -> {
                isValidate = false
                error_message = getString(R.string.string_postalcode_cannot_be_empty)
                if (showSnackbar) edit_postalcode.requestFocus()
            }
        }

        if (!isValidate && showSnackbar) SnackBarFactory.createSnackBar(context, constraint_root, error_message)
        return isValidate
    }

    override fun onContinueButtonClicked() {
        if (setFieldsData()) proceedToNextPage()
    }

    protected fun proceedToNextPage() {
        signUpActivity?.goToNextPage()
    }
}

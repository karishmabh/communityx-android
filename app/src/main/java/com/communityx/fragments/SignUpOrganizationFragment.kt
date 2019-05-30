package com.communityx.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.SignUpRequest
import com.communityx.utils.AppConstant.EMAIL_PATTERN
import com.communityx.utils.GalleryPicker
import com.communityx.utils.SnackBarFactory
import kotlinx.android.synthetic.main.fragment_sign_up_organization.*

class SignUpOrganizationFragment : BaseSignUpFragment(), GalleryPicker.GalleryPickerListener {

    var galleryPicker: GalleryPicker? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_organization, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun setFieldsData(): Boolean {
        signUpActivity?.signUpRequest?.name = edit_organization.text.toString()
        signUpActivity?.signUpRequest?.email = edit_email.text.toString()
        signUpActivity?.signUpRequest?.website = edit_website.text.toString()
        signUpActivity?.signUpRequest?.postal_code = edit_postalcode.text.toString()

        return validateEmpty(signUpActivity?.signUpRequest)
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        var errorMessage = ""
        var isValidate = true

        when {
            TextUtils.isEmpty(edit_organization.text.toString()) -> {
                isValidate = false
                errorMessage = getString(R.string.string_organization_cannot_be_empty)
                if (showSnackbar) edit_organization.requestFocus()
            }

            TextUtils.isEmpty(edit_email.text.toString()) -> {
                isValidate = false
                errorMessage = getString(R.string.string_email_cannot_be_empty)
                if (showSnackbar) edit_email.requestFocus()
            }

            !edit_email.text.toString().matches(EMAIL_PATTERN.toRegex()) -> {
                isValidate = false
                errorMessage = getString(R.string.string_enter_valid_email_address)
                if (showSnackbar) edit_email.requestFocus()
            }

            TextUtils.isEmpty(edit_website.text.toString()) -> {
                isValidate = false
                errorMessage = getString(R.string.string_website_cannot_be_empty)
                if (showSnackbar) edit_website.requestFocus()
            }

            TextUtils.isEmpty(edit_postalcode.text.toString()) -> {
                isValidate = false
                errorMessage = getString(R.string.string_postalcode_cannot_be_empty)
                if (showSnackbar) edit_postalcode.requestFocus()
            }
        }

        if (!isValidate && showSnackbar) SnackBarFactory.createSnackBar(context, constraint_root, errorMessage)
        return isValidate
    }

    override fun onContinueButtonClicked() {
        if (setFieldsData()) proceedToNextPage()
    }

    override fun onMediaSelected(imagePath: String, uri: Uri?, isImage: Boolean) {
        image_profile.setImageURI(uri)
        image_add_edit.setImageResource(R.drawable.ic_signup_edit_image)
        text_profile.text = resources.getString(R.string.edit_profile_image)
        signUpActivity?.selectImagePath = imagePath
        uploadImage(imagePath)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) galleryPicker?.fetch(requestCode, data)
    }

    @OnClick(R.id.view_add_image)
    fun tappedAddImage() {
        galleryPicker = GalleryPicker.with(activity, this).setListener(this).showDialog()
    }

    protected fun proceedToNextPage() {
        signUpActivity?.goToNextPage()
    }
}

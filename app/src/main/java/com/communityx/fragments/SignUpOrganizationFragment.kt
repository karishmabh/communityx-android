package com.communityx.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.communityx.R
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.EmailPhoneVerificationRequest
import com.communityx.models.signup.OtpRequest
import com.communityx.models.signup.SignUpRequest
import com.communityx.models.signup.VerifyOtpRequest
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant.EMAIL_PATTERN
import com.communityx.utils.CustomProgressBar
import com.communityx.utils.GalleryPicker
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import com.mukesh.OnOtpCompletionListener
import kotlinx.android.synthetic.main.fragment_sign_up_organization.*

class SignUpOrganizationFragment : BaseSignUpFragment(), GalleryPicker.GalleryPickerListener {

    var galleryPicker: GalleryPicker? = null
    private var hasOtpOrPasswordFieldVisible = false
    private var shouldChangeNumber = false
    private var isDelKeyPressed = false
    private var prefix: String? = null
    val selectionStart: Int = 3
    private var otpMain: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_organization, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefix = resources.getString(R.string.prefix_number)
        initOtpBox()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edit_mobile.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && edit_mobile.text.toString() == prefix) {
                edit_mobile.setSelection(selectionStart)
            }
        }

        view_otp.setOtpCompletionListener(object: OnOtpCompletionListener {
            override fun onOtpCompleted(otp: String?) {
                otpMain = otp
                createOtpAndVerify()
            }
        })

        edit_mobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().startsWith(resources.getString(R.string.prefix_number))){
                    edit_mobile.setText(prefix)
                    Selection.setSelection(edit_mobile.getText(), edit_mobile.text!!.length)
                }
            }
        })

        edit_postalcode.onFocusChangeListener = object: View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (!hasFocus && edit_postalcode.text.toString().length < 5) {
                    textinput_postalcode.setError(resources.getString(R.string.postal_code_should_be_six_character))
                }
            }
        }

        view_password.visibility = if (signUpActivity?.isOtpVerified!!) View.VISIBLE else View.GONE
    }

    @OnTextChanged(R.id.edit_postalcode)
    fun onPostalCodeChanged() {
        if (edit_postalcode.text.toString().length == 5) {
            textinput_postalcode.setError(null)
        }
    }

    override fun setFieldsData(): Boolean {
        signUpActivity?.signUpRequest?.name = edit_organization.text.toString()
        signUpActivity?.signUpRequest?.email = edit_email.text.toString()
        signUpActivity?.signUpRequest?.website = edit_website.text.toString()
        signUpActivity?.signUpRequest?.postal_code = edit_postalcode.text.toString()
        if (edit_mobile.text.toString().length > 4)
            signUpStudent?.phone = edit_mobile.text.toString().substring(selectionStart)
        signUpStudent?.password = edit_confirm_password.text.toString()

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

            !Utils.validateWebsite(edit_website.text.toString()) -> {
                isValidate = false
                errorMessage = getString(R.string.string_website_invalid)
                if (showSnackbar) edit_website.requestFocus()
            }

            TextUtils.isEmpty(edit_postalcode.text.toString()) -> {
                isValidate = false
                errorMessage = getString(R.string.string_postalcode_cannot_be_empty)
                if (showSnackbar) edit_postalcode.requestFocus()
            }
            !TextUtils.isEmpty(requestData?.postal_code) && requestData?.postal_code?.length!! < 5 -> {
                isValidate = false
                errorMessage = getString(R.string.postal_code_should_be_six_character)
                edit_postalcode.requestFocus()
            }
            edit_mobile.text.toString() == prefix -> {
                isValidate = false
                errorMessage = getString(R.string.mobile_field_empty)
                edit_mobile.requestFocus()
                edit_mobile.setSelection(selectionStart)
            }
            !hasOtpOrPasswordFieldVisible -> {
                isValidate = false
                errorMessage = getString(R.string.click_on_send_otp)
            }
            signUpActivity?.isOtpVerified == false -> {
                isValidate = false
                createOtpAndVerify()
                return isValidate
            }
            TextUtils.isEmpty(edit_create_password.text.toString()) -> {
                isValidate = false
                errorMessage = getString(R.string.password_field_empty)
                edit_create_password.requestFocus()
            }
            !TextUtils.isEmpty(edit_create_password?.text) && edit_create_password?.text!!.length < 6 -> {
                isValidate = false
                errorMessage = getString(R.string.password_leght_error)
                edit_create_password.requestFocus()
            }
            TextUtils.isEmpty(requestData?.password) -> {
                isValidate = false
                errorMessage = getString(R.string.confirm_password_field_empty)
                edit_confirm_password.requestFocus()
            }
            edit_confirm_password?.text.toString() != edit_create_password.text.toString() -> {
                isValidate = false
                errorMessage = getString(R.string.password_not_matched)
            }
        }

        if (!isValidate && showSnackbar) SnackBarFactory.createSnackBar(context, constraint_root, errorMessage)
        return isValidate
    }

    override fun onContinueButtonClicked() {
        if (setFieldsData()) {
            context?.let {
                val dialog = CustomProgressBar.getInstance(it).showProgressDialog("verifying data ...")
                dialog.show()
                    val emailPhoneVerificationRequest = EmailPhoneVerificationRequest(signUpActivity?.signUpRequest?.phone.toString().trim(), signUpActivity?.signUpRequest?.email.toString().trim())

                    activity?.let {
                        DataManager.doVerifyEmailPhone(it, emailPhoneVerificationRequest, object : ResponseListener<List<String>> {
                            override fun onSuccess(response: List<String>) {
                                dialog.dismiss()
                                proceedToNextPage()
                            }

                            override fun onError(error: Any) {
                                dialog.dismiss()
                                Utils.showError(activity, constraint_root, error)
                            }
                        })
                    }
            }
        }
    }

    override fun onMediaSelected(imagePath: String, uri: Uri?, isImage: Boolean) {
        image_profile.setImageURI(uri)
        image_add_edit.setImageResource(R.drawable.ic_signup_edit_image)
        text_profile.text = resources.getString(R.string.edit_profile_image)
        signUpActivity?.selectImagePath = imagePath
        uploadImage(imagePath, constraint_root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) galleryPicker?.fetch(requestCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        galleryPicker?.onResultPermission(requestCode, grantResults)
    }

    @OnClick(R.id.view_add_image)
    fun tappedAddImage() {
        Utils.hideSoftKeyboard(activity)
        galleryPicker = GalleryPicker.with(activity, this).setListener(this).showDialog()
    }

    @OnClick(R.id.text_send_otp)
    fun tappedSendOrChangeText() {
        if (!shouldChangeNumber) {
            tappedSentOtp()
        } else {
            edit_mobile.isEnabled = true
            shouldChangeNumber = false
            onMobileChanged()
        }
    }

    fun onMobileChanged() {
        text_send_otp.text = getString(R.string.send_otp)

        text_enter_otp.visibility = View.GONE
        view_otp.visibility = View.GONE
        resend_otp.visibility = View.GONE
        view_password.visibility = View.GONE

        edit_create_password.setText("")
        edit_confirm_password.setText("")
    }

    @OnTextChanged(R.id.edit_confirm_password)
    fun confirmPasswordChanged() {
        if (edit_confirm_password.text.toString().length > 0) {
            enableButton(true)
        } else {
            enableButton(false)
        }
    }

    fun tappedSentOtp() {
        signUpActivity?.isOtpVerified == false
        Utils.hideSoftKeyboard(activity)
        if (edit_mobile.text.toString() == prefix) {
            SnackBarFactory.createSnackBar(context, constraint_root, getString(R.string.mobile_field_empty))
            return
        }
        val number = edit_mobile.text.toString().substring(selectionStart)
        if (number.length < 10) {
            SnackBarFactory.createSnackBar(context, constraint_root, getString(R.string.mobiel_number_less_digits))
            return
        }
        hasOtpOrPasswordFieldVisible = true
        val otpRequest = OtpRequest(phone = number)
        shouldChangeNumber = true
        generateOtp(otpRequest)
    }

    @OnClick(R.id.resend_otp)
    fun tappedResendOtp() {
        tappedSentOtp()
    }

    private fun generateOtp(otpRequest: OtpRequest) {
        var dialog = CustomProgressBar.getInstance(context!!).showProgressDialog("Please wait, sending OTP...")
        SignUpRepo.generateOtp(context!!, otpRequest, object : ResponseListener<String> {

            override fun onSuccess(response: String) {
                SnackBarFactory.createSnackBar(context, constraint_root, response)
                visibleOtpField(true)
                edit_mobile.isEnabled = false
                constraint_root.post { constraint_root.scrollTo(0, constraint_root.height) }
                dialog.dismiss()
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_root, error)
                dialog.dismiss()
            }
        })
    }

    private fun initOtpBox() {
        visibleOtpField(false)
    }

    private fun visibleOtpField(visible: Boolean) {
        if (visible) {
            text_send_otp.text = getString(R.string.change)
            text_enter_otp.visibility = View.VISIBLE
            view_otp.visibility = View.VISIBLE
            resend_otp.visibility = View.VISIBLE
            view_password.visibility = View.GONE
            edit_otp_one.requestFocus()
        } else {
            text_enter_otp.visibility = View.GONE
            view_otp.visibility = View.GONE
            resend_otp.visibility = View.GONE
            view_password.visibility = View.VISIBLE
        }
    }


    private fun createOtpAndVerify() {
        view_otp.setText("")
        val verifyOtpRequest = VerifyOtpRequest(otp = otpMain!!, phone = edit_mobile.text.toString().substring(selectionStart))
        Utils.hideSoftKeyboard(activity)
        verifyOtp(verifyOtpRequest)
        hasOtpOrPasswordFieldVisible = true
    }

    private fun proceedToNextPage() {
        changeButtonStatus(0, true)
        signUpActivity?.goToNextPage()
    }

    private fun verifyOtp(verifyOtpRequest: VerifyOtpRequest) {
        var dialog = CustomProgressBar.getInstance(context!!).showProgressDialog("Verifying otp...")
        SignUpRepo.verifyOtp(context!!, verifyOtpRequest, object : ResponseListener<String> {
            override fun onSuccess(response: String) {
                SnackBarFactory.createSnackBar(context, constraint_root, response)
                view_password.visibility = View.VISIBLE
                constraint_root.post { constraint_root.scrollTo(0, constraint_root.height) }

                visibleOtpField(false)
                signUpActivity?.isOtpVerified = true
                edit_create_password.requestFocus()
                view_otp.setText("")
                dialog.dismiss()
                disabledMobileField(true)
            }

            override fun onError(error: Any) {
                view_otp.setText("")
                Utils.showError(activity, constraint_root, error)
                signUpActivity?.isOtpVerified = false
                dialog.dismiss()
            }
        })
    }

    private fun disabledMobileField(disable: Boolean) {
        edit_mobile.isEnabled = !disable
        text_send_otp.text = if (!disable) getString(R.string.send_otp) else getString(R.string.change)
    }
}

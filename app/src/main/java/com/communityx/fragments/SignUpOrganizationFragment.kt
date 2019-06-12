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
import com.communityx.R
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.*
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant.EMAIL_PATTERN
import com.communityx.utils.CustomProgressBar
import com.communityx.utils.GalleryPicker
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_sign_up_organization.*

class SignUpOrganizationFragment : BaseSignUpFragment(), GalleryPicker.GalleryPickerListener {

    var galleryPicker: GalleryPicker? = null
    private var hasOtpOrPasswordFieldVisible = false
    private var shouldChangeNumber = false
    private var isDelKeyPressed = false
    private var prefix: String? = null

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
                edit_mobile.setSelection(2)
            }
        }

        edit_mobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().startsWith("+1")){
                    edit_mobile.setText(prefix)
                    Selection.setSelection(edit_mobile.getText(), edit_mobile.text!!.length)
                }
            }
        })

        view_password.visibility = if (signUpActivity?.isOtpVerified!!) View.VISIBLE else View.GONE
    }

    override fun setFieldsData(): Boolean {
        signUpActivity?.signUpRequest?.name = edit_organization.text.toString()
        signUpActivity?.signUpRequest?.email = edit_email.text.toString()
        signUpActivity?.signUpRequest?.website = edit_website.text.toString()
        signUpActivity?.signUpRequest?.postal_code = edit_postalcode.text.toString()
        if (edit_mobile.text.toString().length > 4)
            signUpStudent?.phone = edit_mobile.text.toString().substring(2)
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

            !Utils.isValid(edit_website.text.toString()) -> {
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
                edit_mobile.setSelection(2)
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
        if (setFieldsData()) proceedToNextPage()
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
            text_send_otp.text = getString(R.string.send_otp)
        }
    }

    fun tappedSentOtp() {
        signUpActivity?.isOtpVerified == false
        Utils.hideSoftKeyboard(activity)
        if (edit_mobile.text.toString() == prefix) {
            SnackBarFactory.createSnackBar(context, constraint_root, getString(R.string.mobile_field_empty))
            return
        }
        val number = edit_mobile.text.toString().substring(2)
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
        initOtpTextWatcher(edit_otp_one, edit_otp_two, null)
        initOtpTextWatcher(edit_otp_two, edit_otp_three, edit_otp_one)
        initOtpTextWatcher(edit_otp_three, edit_otp_four, edit_otp_two)
        initOtpTextWatcher(edit_otp_four, edit_otp_five, edit_otp_three)
        initOtpTextWatcher(edit_otp_five, edit_otp_six, edit_otp_four)
        initOtpTextWatcher(edit_otp_six, null, edit_otp_five)
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

    private fun initOtpTextWatcher(currentEditText: EditText, nextEditText: EditText?, prevEditText: EditText?) {
        currentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (nextEditText != null && s.length == 1)
                    nextEditText.requestFocus()
                else if (prevEditText != null && s.isEmpty()) prevEditText.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {
                if (currentEditText == edit_otp_six && !TextUtils.isEmpty(edit_otp_six.text.toString())) {
                    createOtpAndVerify()
                }
            }
        })
    }

    private fun createOtpAndVerify() {
        val verifyOtpRequest =
            VerifyOtpRequest(otp = getOtp(), phone = edit_mobile.text.toString().substring(2))
        Utils.hideSoftKeyboard(activity)
        verifyOtp(verifyOtpRequest)
        hasOtpOrPasswordFieldVisible = true
    }

    private fun getOtp(): String {
        var strBuilder = StringBuilder()
        strBuilder.append(edit_otp_one.text.toString())
        strBuilder.append(edit_otp_two.text.toString())
        strBuilder.append(edit_otp_three.text.toString())
        strBuilder.append(edit_otp_four.text.toString())
        strBuilder.append(edit_otp_five.text.toString())
        strBuilder.append(edit_otp_six.text.toString())

        return strBuilder.toString()
    }

    private fun proceedToNextPage() {
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
                clearOtp()
                dialog.dismiss()
                disabledMobileField(true)
            }

            override fun onError(error: Any) {
                clearOtp()
                edit_otp_one.requestFocus()
                Utils.showError(activity, constraint_root, error)
                signUpActivity?.isOtpVerified = false
                dialog.dismiss()
            }
        })
    }

    private fun clearOtp() {
        edit_otp_one.setText("")
        edit_otp_two.setText("")
        edit_otp_three.setText("")
        edit_otp_four.setText("")
        edit_otp_five.setText("")
        edit_otp_six.setText("")
    }

    private fun disabledMobileField(disable: Boolean) {
        edit_mobile.isEnabled = !disable
        text_send_otp.text = if (!disable) getString(R.string.send_otp) else getString(R.string.change)
    }

}

package com.communityx.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
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
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.EmailPhoneVerificationRequest
import com.communityx.models.signup.OtpRequest
import com.communityx.models.signup.SignUpRequest
import com.communityx.models.signup.VerifyOtpRequest
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.*
import com.communityx.utils.AppConstant.EMAIL_PATTERN
import com.mukesh.OnOtpCompletionListener
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.*

class SignUpStudentInfoFragment : BaseSignUpFragment(), AppConstant, View.OnClickListener,
        GalleryPicker.GalleryPickerListener {

    private var galleryPicker: GalleryPicker? = null
    private var isDelKeyPressed = false
    private var hasOtpOrPasswordFieldVisible = false
    private var shouldChangeNumber = false
    private var prefixNumber: String? = null
    val selectionStart: Int = 3
    private var otpMain: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_student_info, null)
        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prefixNumber = resources.getString(R.string.prefix_number)

        initAllField()
        image_profile.setOnClickListener(this)
        text_send_otp.setOnClickListener(this)
        edit_birthday.setOnClickListener(this)

        view_otp.setOtpCompletionListener(object : OnOtpCompletionListener {
            override fun onOtpCompleted(otp: String?) {
                otpMain = otp
                createOtpAndVerify()
            }
        })

        setTextListener()
    }


    fun setTextListener() {
        edit_mobile.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && edit_mobile.text.toString() == prefixNumber) {
                edit_mobile.setSelection(selectionStart)
            }
        }

        edit_mobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().startsWith(resources.getString(R.string.prefix_number))) {
                    edit_mobile.setText(prefixNumber)
                    Selection.setSelection(edit_mobile.text, edit_mobile.text!!.length)
                }
            }
        })

        edit_confirm_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((activity as SignUpStudentInfoActivity) == null) return

                if (s.toString().length > 1) {
                    (activity as SignUpStudentInfoActivity).enableButton(true)
                } else {
                    (activity as SignUpStudentInfoActivity).enableButton(false)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edit_postalcode.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (!hasFocus && edit_postalcode.text.toString().length < 5) {
                    textinput_postalcode.error = resources.getString(R.string.postal_code_should_be_six_character)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        visibleOtpField(false)
        view_password.visibility = if (signUpActivity?.isOtpVerified!!) View.VISIBLE else View.GONE
    }


    @OnTextChanged(R.id.edit_postalcode)
    fun onPostalCodeChanged() {
        if (edit_postalcode.text.toString().length == 5) {
            textinput_postalcode.error = null
        }
    }

    override fun onClick(v: View?) {
        Utils.hideSoftKeyboard(activity!!)
        when {
            v?.id == R.id.image_profile -> chooseImage()
            v?.id == R.id.edit_birthday -> tappedEditBirth()
            v?.id == R.id.text_send_otp -> {
                if (!shouldChangeNumber) {
                    tappedSentOtp()
                } else {
                    edit_mobile.isEnabled = true
                    shouldChangeNumber = false
                    onMobileChanged()
                }
            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) galleryPicker?.fetch(requestCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        galleryPicker?.onResultPermission(requestCode, grantResults)
    }

    override fun onMediaSelected(imagePath: String, uri: Uri, isImage: Boolean) {
        image_profile.setImageURI(uri)
        text_profile.text = resources.getString(R.string.edit_profile_image)
        image_add_edit.setImageResource(R.drawable.ic_signup_edit_image)
        signUpActivity?.selectImagePath = imagePath
        uploadImage(imagePath, scrollView)
    }

    override fun onContinueButtonClicked() {
        if (setFieldsData()) {
            changeButtonStatus(0, true)
            goToNextPage()
        }
    }

    override fun setFieldsData(): Boolean {
        signUpStudent?.first_name = edit_first_name.text.toString().trim()
        signUpStudent?.last_name = edit_last_name.text.toString().trim()

        signUpStudent?.email = edit_email.text.toString().trim()
        signUpStudent?.dob = edit_birthday.text.toString()
        signUpStudent?.postal_code = edit_postalcode.text.toString()

        if (edit_mobile.text.toString().length > 4)
            signUpStudent?.phone = edit_mobile.text.toString().substring(selectionStart)
        signUpStudent?.password = edit_confirm_password.text.toString()

        return validateEmpty(signUpStudent)
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        var msg = ""
        var isValidate = true
        when {
            TextUtils.isEmpty(requestData?.first_name) -> {
                isValidate = false
                msg = getString(R.string.first_name_field_empty)
                if (showSnackbar) edit_first_name.requestFocus()
            }

            TextUtils.isEmpty(requestData?.last_name) -> {
                isValidate = false
                msg = getString(R.string.last_name_field_empty)
                if (showSnackbar) edit_last_name.requestFocus()
            }

            TextUtils.isEmpty(requestData?.email) -> {
                isValidate = false
                msg = getString(R.string.email_field_empty)
                edit_email.requestFocus()
            }
            !TextUtils.isEmpty(requestData?.email) && !requestData?.email!!.matches(EMAIL_PATTERN.toRegex()) -> {
                isValidate = false
                msg = getString(R.string.email_is_not_valid)
                edit_email.requestFocus()
            }
            TextUtils.isEmpty(requestData?.dob) -> {
                isValidate = false
                msg = getString(R.string.dob_empty)
            }
            TextUtils.isEmpty(requestData?.postal_code) -> {
                isValidate = false
                msg = getString(R.string.postal_field_empty)
                edit_postalcode.requestFocus()
            }

            !TextUtils.isEmpty(requestData?.postal_code) && requestData?.postal_code?.length!! < 5 -> {
                isValidate = false
                msg = getString(R.string.postal_code_should_be_six_character)
                edit_postalcode.requestFocus()
            }
            edit_mobile.text.toString() == prefixNumber -> {
                isValidate = false
                msg = getString(R.string.mobile_field_empty)
                edit_mobile.requestFocus()
                edit_mobile.setSelection(selectionStart)
            }
            !hasOtpOrPasswordFieldVisible -> {
                isValidate = false
                msg = getString(R.string.click_on_send_otp)
            }
            signUpActivity?.isOtpVerified == false -> {
                isValidate = false
                createOtpAndVerify()
                return isValidate
            }
            TextUtils.isEmpty(edit_create_password.text.toString()) -> {
                isValidate = false
                msg = getString(R.string.password_field_empty)
                edit_create_password.requestFocus()
            }
            !TextUtils.isEmpty(edit_create_password?.text) && edit_create_password?.text!!.length < 6 -> {
                isValidate = false
                msg = getString(R.string.password_leght_error)
                edit_create_password.requestFocus()
            }
            TextUtils.isEmpty(requestData?.password) -> {
                isValidate = false
                msg = getString(R.string.confirm_password_field_empty)
                edit_confirm_password.requestFocus()
            }
            edit_confirm_password?.text.toString() != edit_create_password.text.toString() -> {
                isValidate = false
                msg = getString(R.string.password_not_matched)
            }
        }
        if (!isValidate && showSnackbar) SnackBarFactory.createSnackBar(context, scrollView, msg)
        return isValidate
    }

    @OnClick(R.id.resend_otp)
    fun tappedResend() {
        tappedSentOtp()
    }

    private fun tappedSentOtp() {
        signUpActivity?.isOtpVerified = false
        if (edit_mobile.text.toString() == prefixNumber) {
            SnackBarFactory.createSnackBar(context, scrollView, getString(R.string.mobile_field_empty))
            return
        }
        val number = edit_mobile.text.toString().substring(selectionStart)
        if (number.length < 10) {
            SnackBarFactory.createSnackBar(context, scrollView, getString(R.string.mobiel_number_less_digits))
            return
        }
        hasOtpOrPasswordFieldVisible = true
        val otpRequest = OtpRequest(phone = number)
        shouldChangeNumber = true
        generateOtp(otpRequest)

    }

    private fun chooseImage() {
        showImageChooserDialog()
    }

    private fun tappedEditBirth() {
        Utils.hideSoftKeyboard(activity)
        Utils.openDatePickerDialog(activity, object : Utils.IDateCallback {
            override fun getDate(date: String?) {
                edit_birthday.setText(date)
                edit_postalcode.requestFocus()
            }
        })
    }

    private fun createOtpAndVerify() {
        view_otp.setText("")
        val verifyOtpRequest =
                VerifyOtpRequest(otp = otpMain!!, phone = edit_mobile.text.toString().substring(selectionStart))
        Utils.hideSoftKeyboard(activity)
        verifyOtp(verifyOtpRequest)
        hasOtpOrPasswordFieldVisible = true
    }

    private fun showImageChooserDialog() {
        galleryPicker = GalleryPicker.with(signUpActivity, this)
                .setListener(this)
                .showDialog()
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

    private fun initAllField() {
        if (validateEmpty(signUpStudent, false)) {
            edit_first_name.setText(signUpStudent?.first_name)
            edit_last_name.setText(signUpStudent?.last_name)
            edit_email.setText(signUpStudent?.email)
            edit_birthday.setText(signUpStudent?.dob)
            edit_postalcode.setText(signUpStudent?.postal_code)
            edit_mobile.setText(signUpStudent?.phone)
            edit_create_password.setText(signUpStudent?.phone)
            edit_confirm_password.setText(signUpStudent?.password)
            if (signUpActivity?.selectImagePath != null || !TextUtils.isEmpty(signUpActivity?.selectImagePath)) {
                image_profile.setImageURI(Uri.parse(signUpActivity?.selectImagePath))
                text_profile.text = resources.getString(R.string.edit_profile_image)
                image_add_edit.setImageResource(R.drawable.ic_signup_edit_image)
            }
        }
        edit_mobile.isEnabled = !signUpActivity?.isOtpVerified!!
        text_send_otp.text =
                if (signUpActivity?.isOtpVerified!!) getString(R.string.change) else getString(R.string.send_otp)
    }

    private fun generateOtp(otpRequest: OtpRequest) {
        val dialog = CustomProgressBar.getInstance(context!!).showProgressDialog("Please wait, sending OTP...")
        SignUpRepo.generateOtp(context!!, otpRequest, object : ResponseListener<String> {

            override fun onSuccess(response: String) {
                SnackBarFactory.createSnackBar(context, scrollView, response)
                visibleOtpField(true)

                edit_mobile.isEnabled = false
                scrollView.post { scrollView.scrollTo(0, scrollView.height) }
                dialog.dismiss()
            }

            override fun onError(error: Any) {
                scrollView.post { scrollView.scrollTo(0, scrollView.height) }
                Utils.showError(activity, scrollView, error)
                dialog.dismiss()
            }
        })
    }

    private fun verifyOtp(verifyOtpRequest: VerifyOtpRequest) {
        var dialog = CustomProgressBar.getInstance(context!!).showProgressDialog("Verifying OTP...")
        SignUpRepo.verifyOtp(context!!, verifyOtpRequest, object : ResponseListener<String> {
            override fun onSuccess(response: String) {
                SnackBarFactory.createSnackBar(context, scrollView, response)
                //scrollView.post { scrollView.scrollTo(0, scrollView.height) }
                focusOnOtpView()

                visibleOtpField(false)
                signUpActivity?.isOtpVerified = true

                edit_create_password.requestFocus()
                view_otp.setText("")
                dialog.dismiss()
                disabledMobileField(true)
            }

            override fun onError(error: Any) {
                view_otp.setText("")
                focusOnOtpView()
                edit_otp_one.requestFocus()
                Utils.showError(activity, scrollView, error)
                signUpActivity?.isOtpVerified = false
                dialog.dismiss()
            }
        })
    }


    private fun focusOnOtpView() {
        scrollView.post(Runnable {
            scrollView.scrollTo(0, view_otp.getBottom())
        })
    }

    private fun disabledMobileField(disable: Boolean) {
        edit_mobile.isEnabled = !disable
        text_send_otp.text = if (!disable) getString(R.string.send_otp) else getString(R.string.change)
    }

    companion object {
        private val permissions =
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}
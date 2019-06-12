package com.communityx.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
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
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.*
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.edit_email_username

class SignUpStudentInfoFragment : BaseSignUpFragment(), AppConstant, View.OnClickListener,
    GalleryPicker.GalleryPickerListener {

    private var galleryPicker: GalleryPicker? = null
    private var isDelKeyPressed = false
    private var hasOtpOrPasswordFieldVisible = false
    private var shouldChangeNumber = false
    private var prefixNumber: String? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_student_info, null)
        ButterKnife.bind(this, view)
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = PermissionHelper(signUpActivity)
            if (!permission.checkPermission(*permissions))
                requestPermissions(permissions, AppConstant.REQUEST_PERMISSION_CODE)
        }*/
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prefixNumber = resources.getString(R.string.prefix_number)

        initAllField()
        image_profile.setOnClickListener(this)
        text_send_otp.setOnClickListener(this)
        edit_birthday.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                tappedEditBirth()
            };true
        }
        edit_birthday.setOnFocusChangeListener { _, isFocused ->
            if (isFocused) {
                tappedEditBirth()
            }
        }
        edit_mobile.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && edit_mobile.text.toString() == prefixNumber) {
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
                    edit_mobile.setText(prefixNumber)
                    Selection.setSelection(edit_mobile.getText(), edit_mobile.text!!.length)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initOtpBox()
        view_password.visibility = if (signUpActivity?.isOtpVerified!!) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        Utils.hideSoftKeyboard(activity!!)
        when {
            v?.id == R.id.image_profile -> chooseImage()
            v?.id == R.id.text_send_otp -> {
                if (!shouldChangeNumber) {
                    tappedSentOtp()
                } else {
                    edit_mobile.isEnabled = true
                    shouldChangeNumber = false
                    text_send_otp.text = getString(R.string.send_otp)
                }
            }
        }
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
            context?.let {
                val dialog = CustomProgressBar.getInstance(it).showProgressDialog("verifying data ...")
                dialog.show()

                if (setFieldsData()) {
                    val emailPhoneVerificationRequest = EmailPhoneVerificationRequest(signUpActivity?.signUpRequest?.phone.toString().trim(), signUpActivity?.signUpRequest?.email.toString().trim())

                    activity?.let {
                        DataManager.doVerifyEmailPhone(it, emailPhoneVerificationRequest, object : ResponseListener<List<String>> {
                            override fun onSuccess(response: List<String>) {
                                dialog.dismiss()
                                goToNextPage()
                            }

                            override fun onError(error: Any) {
                                dialog.dismiss()
                                Utils.showError(activity, constraint_top, error)
                            }
                        })
                    }
                }
            }
        }
    }

    override fun setFieldsData(): Boolean {
        signUpStudent?.full_name = edit_email_username.text.toString()
        signUpStudent?.email = edit_email.text.toString()
        signUpStudent?.dob = edit_birthday.text.toString()
        signUpStudent?.postal_code = edit_postalcode.text.toString()

        if(edit_mobile.text.toString().length > 4)
            signUpStudent?.phone = edit_mobile.text.toString().substring(2)
        signUpStudent?.password = edit_confirm_password.text.toString()

        return validateEmpty(signUpStudent)
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        var msg = ""
        var isValidate = true
        when {
            TextUtils.isEmpty(requestData?.full_name) -> {
                isValidate = false
                msg = getString(R.string.name_field_empty)
                if (showSnackbar) edit_email_username.requestFocus()
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
                edit_mobile.setSelection(2)
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

    private fun chooseImage() {
        showImageChooserDialog()
    }

    private fun tappedSentOtp() {
        signUpActivity?.isOtpVerified = false
        if (edit_mobile.text.toString() == prefixNumber) {
            SnackBarFactory.createSnackBar(context, scrollView, getString(R.string.mobile_field_empty))
            return
        }
        val number = edit_mobile.text.toString().substring(2)
        if (number.length < 10) {
            SnackBarFactory.createSnackBar(context, scrollView, getString(R.string.mobiel_number_less_digits))
            return
        }
        hasOtpOrPasswordFieldVisible = true
        val otpRequest = OtpRequest(phone = number)
        shouldChangeNumber = true
        generateOtp(otpRequest)

    }

    private fun tappedEditBirth() {
        Utils.hideSoftKeyboard(activity)
        Utils.iosDatePicker(activity, object: Utils.IDateCallback {
            override fun getDate(date: String?) {
                edit_birthday.setText(date)
                edit_postalcode.requestFocus()
            }
        })
     /*   Utils.datePicker(signUpActivity) {
            edit_birthday.setText(it)
            edit_postalcode.requestFocus()
        }*/
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
            edit_email_username.setText(signUpStudent?.full_name)
            edit_email.setText(signUpStudent?.email)
            edit_birthday.setText(signUpStudent?.dob)
            edit_postalcode.setText(signUpStudent?.postal_code)
            edit_mobile.setText(signUpStudent?.phone)
            edit_create_password.setText(signUpStudent?.phone)
            edit_confirm_password.setText(signUpStudent?.password)
            if(signUpActivity?.selectImagePath != null || !TextUtils.isEmpty(signUpActivity?.selectImagePath)) {
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
                view_password.visibility = View.VISIBLE
                scrollView.post { scrollView.scrollTo(0, scrollView.height) }
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
                Utils.showError(activity, scrollView, error)
                signUpActivity?.isOtpVerified = false
                dialog.dismiss()
            }
        })
    }

    private fun isOtpFieldEmpty(): Boolean {
        var isEmpty = false
        when {
            TextUtils.isEmpty(edit_otp_one.text.toString()) -> isEmpty = true
            TextUtils.isEmpty(edit_otp_two.text.toString()) -> isEmpty = true
            TextUtils.isEmpty(edit_otp_three.text.toString()) -> isEmpty = true
            TextUtils.isEmpty(edit_otp_four.text.toString()) -> isEmpty = true
            TextUtils.isEmpty(edit_otp_five.text.toString()) -> isEmpty = true
            TextUtils.isEmpty(edit_otp_five.text.toString()) -> isEmpty = true
        }

        return isEmpty
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

    internal fun onMobileNumberChange(s: CharSequence?) {
        edit_mobile.setOnKeyListener { _, keyCode, _ ->
            isDelKeyPressed = keyCode == KeyEvent.KEYCODE_DEL
            false
        }

        if (s?.length!! < 3) {
            edit_mobile.setText(prefixNumber)
            edit_mobile.setSelection(3)
        } else if (s.length == 4 && !isDelKeyPressed) {
            edit_mobile.setText(s.toString().substring(0, 3) + "-" + s.toString().substring(3))
            edit_mobile.setSelection(5)
        }
    }

    companion object {
        private val permissions =
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}
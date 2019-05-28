package com.communityx.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import com.communityx.R
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.OtpRequest
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.models.signup.VerifyOtpRequest
import com.communityx.models.signup.image.ImageUploadRequest
import com.communityx.models.signup.image.ImageUploadResponse
import com.communityx.network.ResponseListener
import com.communityx.network.ServiceRepo.SignUpRepo
import com.communityx.utils.*
import com.communityx.utils.AppConstant.*
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SignUpStudentInfoFragment : BaseSignUpFragment(), AppConstant, View.OnClickListener,
    GalleryPicker.GalleryPickerListener {

    private var galleryPicker: GalleryPicker? = null
    private var isDelKeyPressed = false
    private var hasOtpOrPasswordFieldVisible = false
    private var shouldChangeNumber = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_student_info, null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = PermissionHelper(signUpActivity)
            if (!permission.checkPermission(*permissions))
                requestPermissions(permissions, AppConstant.REQUEST_PERMISSION_CODE)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        edit_mobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onMobileNumberChange(s)
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initOtpBox()
        view_password.visibility = if (signUpActivity?.isOtpVerifed!!) View.VISIBLE else View.GONE
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

    override fun onMediaSelected(imagePath: String, uri: Uri, isImage: Boolean) {
        image_profile.setImageURI(uri)
        text_profile.text = resources.getString(R.string.edit_profile_image)
        image_add_edit.setImageResource(R.drawable.ic_signup_edit_image)
        signUpActivity?.selectImagePath = imagePath
        uploadImage(imagePath)
    }

    override fun onContinueButtonClicked() {
        if(setFieldsData()) goToNextPage()
    }

    override fun setFieldsData(): Boolean {

        signUpRequest?.full_name = edit_email_username.text.toString()
        signUpRequest?.email = edit_email.text.toString()
        signUpRequest?.dob = edit_birthday.text.toString()
        signUpRequest?.postal_code = edit_postalcode.text.toString()
        signUpRequest?.phone = edit_mobile.text.toString()
        signUpRequest?.password = edit_confirm_password.text.toString()

        return validateEmpty(signUpRequest)
    }

    override fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean): Boolean {
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
            TextUtils.isEmpty(requestData?.dob) -> {
                isValidate = false
                msg = getString(R.string.dob_empty)
            }
            TextUtils.isEmpty(requestData?.postal_code) -> {
                isValidate = false
                msg = getString(R.string.postal_field_empty)
                edit_postalcode.requestFocus()
            }

            edit_mobile.text.toString() == "+91" -> {
                isValidate = false
                msg = getString(R.string.mobile_field_empty)
                edit_mobile.requestFocus()
                edit_mobile.setSelection(3)
            }
            !hasOtpOrPasswordFieldVisible -> {
                isValidate = false
                msg = getString(R.string.click_on_send_otp)
            }
            signUpActivity?.isOtpVerifed == false && TextUtils.isEmpty(edit_create_password.text.toString()) -> {
                isValidate = false
                msg = getString(R.string.please_verify_otp)
            }
            TextUtils.isEmpty(requestData?.password) -> {
                isValidate = false
                msg = getString(R.string.password_field_empty)
                edit_create_password.requestFocus()
            }
            edit_confirm_password?.text.toString() != edit_create_password.text.toString() -> {
                isValidate = false
                msg = getString(R.string.password_not_matched)
            }
        }
        if (!isValidate && showSnackbar) SnackBarFactory.createSnackBar(context, scrollView, msg)
        return isValidate
    }

    private fun chooseImage() {
        showImageChooserDialog()
    }

    //todo : hard coded string
    private fun tappedSentOtp() {
        if (edit_mobile.text.toString() == "+91") {
            SnackBarFactory.createSnackBar(context, scrollView, getString(R.string.mobile_field_empty))
            return
        }
        val number = edit_mobile.text.toString().substring(4)
        if (number.length != 10) {
            SnackBarFactory.createSnackBar(context, scrollView, getString(R.string.mobiel_number_not_valid))
            return
        }
        hasOtpOrPasswordFieldVisible = true
        val otpRequest = OtpRequest(phone = number)
        shouldChangeNumber = true
        generateOtp(otpRequest)

    }

    private fun tappedEditBirth() {
        Utils.datePicker(signUpActivity, edit_birthday)
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
                    val verifyOtpRequest =
                        VerifyOtpRequest(otp = getOtp(), phone = edit_mobile.text.toString().substring(4))
                    Utils.hideSoftKeyboard(activity)
                    if (signUpActivity?.isOtpVerifed == false) verifyOtp(verifyOtpRequest)
                    hasOtpOrPasswordFieldVisible = true
                }
            }
        })
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
        } else {
            text_enter_otp.visibility = View.GONE
            view_otp.visibility = View.GONE
            resend_otp.visibility = View.GONE
            view_password.visibility = View.VISIBLE
        }
    }

    private fun initAllField() {
        if (validateEmpty(signUpRequest, false)) {
            edit_email_username.setText(signUpRequest?.full_name)
            edit_email.setText(signUpRequest?.email)
            edit_birthday.setText(signUpRequest?.dob)
            edit_postalcode.setText(signUpRequest?.postal_code)
            edit_mobile.setText(signUpRequest?.phone)
            edit_create_password.setText(signUpRequest?.phone)
            edit_confirm_password.setText(signUpRequest?.password)
            if(signUpActivity?.selectImagePath != null || !TextUtils.isEmpty(signUpActivity?.selectImagePath)) {
                image_profile.setImageURI(Uri.parse(signUpActivity?.selectImagePath))
                text_profile.text = resources.getString(R.string.edit_profile_image)
                image_add_edit.setImageResource(R.drawable.ic_signup_edit_image)
            }
        }
    }

    private fun generateOtp(otpRequest: OtpRequest) {
        SignUpRepo.generateOtp(context!!, otpRequest, object : ResponseListener<String> {

            override fun onSuccess(response: String) {
                SnackBarFactory.createSnackBar(context, scrollView, response)
                visibleOtpField(true)
                edit_mobile.isEnabled = false
                scrollView.post { scrollView.scrollTo(0, scrollView.height) }
            }

            override fun onError(error: Any) {
                SnackBarFactory.createSnackBar(context, scrollView, "Failed to send otp !!")
            }
        })
    }

    private fun verifyOtp(verifyOtpRequest: VerifyOtpRequest) {
        SignUpRepo.verifyOtp(context!!, verifyOtpRequest, object : ResponseListener<String> {
            override fun onSuccess(response: String) {
                view_password.visibility = View.VISIBLE
                scrollView.post { scrollView.scrollTo(0, scrollView.height) }
                visibleOtpField(false)
                signUpActivity?.isOtpVerifed = true
                edit_create_password.requestFocus()
            }

            override fun onError(error: Any) {
                SnackBarFactory.createSnackBar(context, scrollView, "Failed to verify otp !!")
                signUpActivity?.isOtpVerifed = false

                view_password.visibility = View.VISIBLE
                scrollView.post { scrollView.scrollTo(0, scrollView.height) }
                visibleOtpField(false)
                signUpActivity?.isOtpVerifed = true
                edit_create_password.requestFocus()
            }

        })
    }


    private fun uploadImage(imagePath: String) {
        val file =File(imagePath)
        val requestFile = RequestBody.create(MediaType.parse(MILTI_PART_FORM_DATA), file)
        val body = MultipartBody.Part.createFormData(IMAGE_PARAM, file.name, requestFile)
        val type = MultipartBody.Part.createFormData(TYPE, "USER")

        val imageUploadRequest = ImageUploadRequest(body,type)
        SignUpRepo.uploadImage(context!!,imageUploadRequest, object: ResponseListener<ImageUploadResponse> {
            override fun onSuccess(response: ImageUploadResponse) {

            }

            override fun onError(error: Any) {

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

    internal fun onMobileNumberChange(s: CharSequence?) {
        edit_mobile.setOnKeyListener { _, keyCode, _ ->
            isDelKeyPressed = keyCode == KeyEvent.KEYCODE_DEL
            false
        }

        if (s?.length!! < 3) {
            edit_mobile.setText("+91")
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
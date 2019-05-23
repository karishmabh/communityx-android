package com.communityx.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import com.communityx.R
import com.communityx.utils.AppConstant
import com.communityx.utils.GalleryPicker
import com.communityx.utils.PermissionHelper
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_sign_up_student_info.*

class SignUpStudentInfoFragment : Fragment(), AppConstant, View.OnClickListener, GalleryPicker.GalleryPickerListener {

    private var galleryPicker: GalleryPicker? = null
    private var isDelKeyPressed = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_student_info, null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = PermissionHelper(activity)
            if (!permission.checkPermission(*permissions))
                requestPermissions(permissions, AppConstant.REQUEST_PERMISSION_CODE)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        image_profile.setOnClickListener(this)
        text_send_otp.setOnClickListener(this)
        edit_birthday.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                tappedEditBirth()
            };true
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
        view_password.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when {
            v?.id == R.id.image_profile -> chooseImage()
            v?.id == R.id.text_send_otp -> tappedSentOtp()
        }
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

    private fun chooseImage() {
        showImageChooserDialog()
    }

    private fun tappedSentOtp() {
        visibleOtpField(true)
        scrollView.post { scrollView.scrollTo(0, scrollView.height) }
    }

    private fun tappedEditBirth() {
        Utils.datePicker(activity, edit_birthday)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) galleryPicker?.fetch(requestCode, data)
    }

    override fun onMediaSelected(imagePath: String, uri: Uri, isImage: Boolean) {
        image_profile.setImageURI(uri)
        text_profile.text = resources.getString(R.string.edit_profile_image)
        image_add_edit.setImageResource(R.drawable.ic_signup_edit_image)
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
                if (currentEditText == edit_otp_six) {
                    view_password.visibility = View.VISIBLE
                    scrollView.post { scrollView.scrollTo(0, scrollView.height) }
                    visibleOtpField(false)
                }
            }
        })
    }

    private fun showImageChooserDialog() {
        galleryPicker = GalleryPicker.with(activity, this)
            .setListener(this)
            .showDialog()
    }

    //TODO: HARD CODED STRING
    private fun validateField(firstName: String, email: String, birthDate: String, postalCode: String): Boolean {

        if (TextUtils.isEmpty(firstName)) {
            val snackbar = Snackbar.make(constraintLayout!!, "Please Enter First Name", Snackbar.LENGTH_LONG)
            snackbar.show()
            return false
        }

        if (TextUtils.isEmpty(email)) {
            val snackbar = Snackbar.make(constraintLayout!!, "Please Enter Email", Snackbar.LENGTH_LONG)
            snackbar.show()
            return false
        }

        if (TextUtils.isEmpty(birthDate)) {
            val snackbar = Snackbar.make(constraintLayout!!, "Please Enter Birthdate", Snackbar.LENGTH_LONG)
            snackbar.show()
            return false
        }

        if (TextUtils.isEmpty(postalCode)) {
            val snackbar = Snackbar.make(constraintLayout!!, "Please Enter Postal Code", Snackbar.LENGTH_LONG)
            snackbar.show()
            return false
        }
        return true
    }

    private fun visibleOtpField(visible: Boolean) {
        if (visible) {
            text_send_otp.text = "Change"
            text_enter_otp.visibility = View.VISIBLE
            view_otp.visibility = View.VISIBLE
            resend_otp.visibility = View.VISIBLE
        } else {
            text_enter_otp.visibility = View.GONE
            view_otp.visibility = View.GONE
            resend_otp.visibility = View.GONE
        }
    }

    companion object {
        private val permissions =
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

}
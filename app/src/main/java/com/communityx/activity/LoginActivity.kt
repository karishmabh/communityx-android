package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.communityx.R
import com.communityx.models.login.Data
import com.communityx.models.login.LoginRequest
import com.communityx.models.login.LoginResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.session.SessionManager
import com.communityx.utils.AppConstant
import com.communityx.utils.CustomProgressBar
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.jvm.internal.Intrinsics

class LoginActivity : AppCompatActivity(), AppConstant {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        Utils.enableButton(button_login, false)
        setPrefixMobile()
        Intrinsics.checkExpressionValueIsNotNull(edit_password, "edit_password")
        edit_password.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    @OnClick(R.id.text_signup)
    internal fun goToSignUp() {
        startActivity(Intent(this, SignupCategoryActivity::class.java))
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
    }

    @OnClick(R.id.button_login)
    internal fun loginClicked() {
        Utils.hideSoftKeyboard(this)
        startLogin()
    }

    @OnTextChanged(R.id.edit_email_username)
    internal fun onEmailTyping(s: CharSequence) {
        Utils.enableButton(button_login, s.length != 0 && edit_password!!.length() != 0)
    }

    @OnTextChanged(R.id.edit_password)
    internal fun onPasswordTyping(s: CharSequence) {
        Utils.enableButton(button_login, s.length != 0 && edit_email_username!!.length() != 0)
    }

    private fun setPrefixMobile() {
        var prefixNumber = resources.getString(R.string.prefix_number)
        edit_email_username.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && edit_email_username.text.toString() == prefixNumber) {
                edit_email_username.setSelection(2)
            }
        }

        edit_email_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().startsWith("+1")){
                    edit_email_username.setText(prefixNumber)
                    Selection.setSelection(edit_email_username.getText(), edit_email_username.text!!.length)
                }
            }
        })
    }

    private fun navigateActivity() {
        startActivity(
            Intent(this, DashboardActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        finish()
    }

    private fun startLogin() {
        val phone = edit_email_username.text.toString().substring(2).trim()
        val password = edit_password.text.toString().trim()
        val loginRequest = LoginRequest(phone, password)

        if (validate(loginRequest)) {
            getLogin(loginRequest)
        }
    }

    private fun validate(loginrequest: LoginRequest): Boolean {
        val phone = loginrequest.phone
        val password = loginrequest.password

        if (TextUtils.isEmpty(phone)) {
            SnackBarFactory.createSnackBar(this, constraint_top, resources.getString(R.string.please_enter_mobile))
            return false
        } else if (!Utils.validatePhone(phone)) {
            SnackBarFactory.createSnackBar(this, constraint_top, resources.getString(R.string.please_enter_correct_mobile))
            return false
        } else if (phone.length < 10) {
            SnackBarFactory.createSnackBar(this, constraint_top, resources.getString(R.string.mobile_length_mismatch))
            return false
        }

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, constraint_top, resources.getString(R.string.please_enter_password))
            return false
        } else if (password.length < 6) {
            SnackBarFactory.createSnackBar(this, constraint_top, resources.getString(R.string.password_length_mismatch))
            return false
        }

        return true
    }

    private fun getLogin(loginrequest: LoginRequest) {
        val dialog = CustomProgressBar.getInstance(this).showProgressDialog("Logging in...")
        DataManager.doLogin(this, loginrequest, object : ResponseListener<LoginResponse> {
            override fun onSuccess(response: LoginResponse) {
                if (response.data == null) return

                dialog.dismiss()
                val loginData = response.data.get(0)
                saveUserData(loginData)
            }

            override fun onError(error: Any) {
                dialog.dismiss()
                Utils.showError(this@LoginActivity, constraint_top, error)
            }
        })
    }

    private fun saveUserData(loginData: Data) {
        SessionManager.setSession(loginData)

        navigateActivity()
    }
}

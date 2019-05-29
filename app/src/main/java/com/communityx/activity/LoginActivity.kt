package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
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
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.jvm.internal.Intrinsics

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        Utils.enableButton(button_login, false)
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
        startActivity(Intent(this, DashboardActivity::class.java))
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
    }

    @OnTextChanged(R.id.edit_email_username)
    internal fun onEmailTyping(s: CharSequence) {
        Utils.enableButton(button_login, s.length != 0 && edit_password!!.length() != 0)
    }

    @OnTextChanged(R.id.edit_password)
    internal fun onPasswordTyping(s: CharSequence) {
        Utils.enableButton(button_login, s.length != 0 && edit_email_username!!.length() != 0)
    }

    private fun startLogin() {
        val phone = edit_email_username.text.toString().trim()
        val password = edit_password.text.toString().trim()
        val loginRequest = LoginRequest(phone, password)

        if (validate(loginRequest)) {
            getLogin(loginRequest)
        }
    }

    private fun validate(loginrequest: LoginRequest) : Boolean {
        val phone = loginrequest.phone
        val password = loginrequest.password

        if (TextUtils.isEmpty(phone)) {
            SnackBarFactory.createSnackBar(this, constraint_top, resources.getString(R.string.please_enter_mobile))
            return false
        } else if (!Utils.validatePhone(phone)) {
            SnackBarFactory.createSnackBar(this, constraint_top, resources.getString(R.string.please_enter_correct_mobile))
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
        DataManager.doLogin(this, loginrequest, object : ResponseListener<LoginResponse> {
            override fun onSuccess(response: LoginResponse) {
                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                startActivity(intent)
            }

            override fun onError(error: Any) {

            }
        })
    }
}

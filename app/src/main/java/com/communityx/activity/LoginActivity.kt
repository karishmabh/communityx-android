package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.PasswordTransformationMethod
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.communityx.R
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
}

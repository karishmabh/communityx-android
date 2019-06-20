package com.communityx.activity

import android.os.Bundle
import android.view.View
import com.communityx.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setToolbarReset(this, resources.getString(R.string.forgot_password), true)
        button_send.setOnClickListener(View.OnClickListener {
            startNewActivity(this, VerificationActivity::class.java)
        })
    }
}

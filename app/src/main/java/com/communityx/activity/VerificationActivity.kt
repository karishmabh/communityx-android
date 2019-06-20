package com.communityx.activity

import android.os.Bundle
import android.view.View
import com.communityx.R
import kotlinx.android.synthetic.main.activity_verification.*

class VerificationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        setToolbarReset(this, resources.getString(R.string.verification), true)

        button_send.setOnClickListener(View.OnClickListener {
            startNewActivity(this, ResetActivity::class.java)
        })
    }
}

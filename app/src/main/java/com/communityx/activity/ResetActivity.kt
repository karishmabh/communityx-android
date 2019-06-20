package com.communityx.activity

import android.os.Bundle
import android.view.View
import com.communityx.R
import kotlinx.android.synthetic.main.activity_reset.*

class ResetActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        setToolbarReset(this, resources.getString(R.string.reset_password), true)

        button_send.setOnClickListener(View.OnClickListener {
            startNewActivity(this, ResetSuccessfulActivity::class.java)
        })
    }
}

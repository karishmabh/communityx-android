package com.communityx.activity

import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import com.communityx.R
import kotlinx.android.synthetic.main.activity_reset_successful.*
import kotlinx.android.synthetic.main.toolbar_forgot.*

class ResetSuccessfulActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_successful)
        ButterKnife.bind(this)

        view_toolbar.visibility = View.INVISIBLE
        card_view_toolbar.cardElevation = 0f
        button_send.setOnClickListener(View.OnClickListener {
            startActivityWithFlags(this, LoginActivity::class.java)
        })
    }
}

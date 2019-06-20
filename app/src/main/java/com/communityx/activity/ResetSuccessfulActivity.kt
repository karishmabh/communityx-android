package com.communityx.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.communityx.R
import kotlinx.android.synthetic.main.toolbar_forgot.*

class ResetSuccessfulActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_successful)

        view_toolbar.visibility = View.INVISIBLE
    }
}

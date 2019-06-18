package com.communityx.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import com.communityx.R

import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.layout_top_view_logo.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        text_subtitle.visibility = View.INVISIBLE
    }
}

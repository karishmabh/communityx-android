package com.communityx.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import com.communityx.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.layout_top_view_logo.*

class SignUpStudentInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_student_info)

        text_subtitle.text = "Build your social impact identity on CommunityX."

    }
}
package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.communityx.R
import com.communityx.utils.SnackBarFactory
import kotlinx.android.synthetic.main.fragment_sign_up_professional.*

class SignUpProfessional : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_professional, container, false)
    }

    private fun getData() {
        if(TextUtils.isEmpty(edit_job_title.text.toString())) {
            SnackBarFactory.createSnackBar(activity!!, coordinator_main, "Please enter Job Title")
        }

        if(TextUtils.isEmpty(edit_recent_company.text.toString())) {
            SnackBarFactory.createSnackBar(activity!!, coordinator_main, "Please enter recent company")
        }
    }
}

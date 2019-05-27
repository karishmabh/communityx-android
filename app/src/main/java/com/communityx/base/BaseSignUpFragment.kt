package com.communityx.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.models.SignUpRequest

abstract class BaseSignUpFragment : Fragment() {

    protected var signUpActivity : SignUpStudentInfoActivity? = null
    protected var signUpRequest : SignUpRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpActivity = activity as SignUpStudentInfoActivity
        signUpRequest = signUpActivity?.signUpRequest
    }

    protected fun goToNextPage(){
        signUpActivity?.goToNextPage()
    }

    protected fun enableButton(enable : Boolean){
        signUpActivity?.enableButton(enable)
    }

    public abstract fun onContinueButtonClicked()
}
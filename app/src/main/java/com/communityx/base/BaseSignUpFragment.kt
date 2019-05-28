package com.communityx.base

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.utils.AppConstant

abstract class BaseSignUpFragment : Fragment(), AppConstant {

    protected var signUpActivity : SignUpStudentInfoActivity? = null
    protected var signUpRequest : StudentSignUpRequest? = null

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

    protected abstract fun setFieldsData(): Boolean
    protected abstract fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean = true): Boolean

    public abstract fun onContinueButtonClicked()


}
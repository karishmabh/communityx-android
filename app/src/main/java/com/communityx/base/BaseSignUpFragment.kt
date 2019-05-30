package com.communityx.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.models.signup.image.ImageUploadRequest
import com.communityx.models.signup.image.ImageUploadResponse
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

abstract class BaseSignUpFragment : Fragment(), AppConstant {

    protected var signUpActivity : SignUpStudentInfoActivity? = null
    protected var signUpStudent : StudentSignUpRequest? = null
    protected var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpActivity = activity as SignUpStudentInfoActivity
        signUpStudent = signUpActivity?.signUpRequest
        category = signUpActivity?.selectedCategory
    }

    protected fun goToNextPage(){
        signUpActivity?.goToNextPage()
    }

    protected fun enableButton(enable : Boolean){
        signUpActivity?.enableButton(enable)
    }

    protected fun uploadImage(imagePath: String) {
        val file = File(imagePath)
        val requestFile = RequestBody.create(MediaType.parse(AppConstant.MILTI_PART_FORM_DATA), file)
        val body = MultipartBody.Part.createFormData(AppConstant.IMAGE_PARAM, file.name, requestFile)
        val type = MultipartBody.Part.createFormData(AppConstant.TYPE, "USER")

        val imageUploadRequest = ImageUploadRequest(body, type)
        SignUpRepo.uploadImage(context!!, imageUploadRequest, object : ResponseListener<ImageUploadResponse> {
            override fun onSuccess(response: ImageUploadResponse) {
                signUpStudent?.profile_image = response.data[0].name
            }

            override fun onError(error: Any) {

            }
        })
    }

    protected abstract fun setFieldsData(): Boolean

    protected abstract fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean = true): Boolean

    abstract fun onContinueButtonClicked()


}
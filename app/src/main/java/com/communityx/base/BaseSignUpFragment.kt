package com.communityx.base

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.models.signup.SignUpRequest
import com.communityx.models.signup.image.ImageUploadRequest
import com.communityx.models.signup.image.ImageUploadResponse
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant
import com.communityx.utils.PermissionHelper
import com.communityx.utils.Utils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

abstract class BaseSignUpFragment : Fragment(), AppConstant {

    protected var signUpActivity : SignUpStudentInfoActivity? = null
    protected var signUpStudent : SignUpRequest? = null
    protected var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBaseData()
        checkRequiredPermission()
    }

    private fun initBaseData() {
        signUpActivity = activity as SignUpStudentInfoActivity
        signUpStudent = signUpActivity?.signUpRequest
        category = signUpActivity?.selectedCategory
    }

    private fun checkRequiredPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = PermissionHelper(signUpActivity)
            if (!permission.checkPermission(*permissions))
                requestPermissions(permissions, AppConstant.REQUEST_PERMISSION_CODE)
        }
    }

    protected fun goToNextPage(){
        signUpActivity?.goToNextPage()
    }

    protected fun enableButton(enable : Boolean){
        signUpActivity?.enableButton(enable)
    }

    protected fun changeButtonStatus(pos: Int, enable: Boolean) {
        signUpActivity?.changeButtonStatus(pos, enable)
    }

    protected fun uploadImage(imagePath: String, view: View) {
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
                Utils.showError(activity, view, error)
            }
        })
    }

    protected abstract fun setFieldsData(): Boolean

    protected abstract fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean = true): Boolean

    abstract fun onContinueButtonClicked()

    companion object {
        private val permissions =
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

}
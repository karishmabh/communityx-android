package com.communityx.utils

import android.app.Activity
import android.view.View
import com.communityx.R
import com.communityx.models.login.Error
import com.communityx.network.NetworkConnectionInterceptor

class ErrorManager(private val mActivity: Activity, private val mView: View, private val mObject: Any?) {

    fun handleErrorResponse() {
        if (mObject == null) {
            return
        }

        if (mObject is NetworkConnectionInterceptor.NoConnectivityException) {
            SnackBarFactory.createSnackBar(mActivity, mView, mActivity.resources.getString(R.string.no_internet))
        } else if (mObject is Throwable)
            SnackBarFactory.createSnackBar(mActivity, mView, mObject.localizedMessage)

        if (mObject is Error) {
            val response = mObject as Error?
            if (response?.error_message != null && response.error_message.size > 0)
                SnackBarFactory.createSnackBar(mActivity, mView, response.error_message.get(0))
        }
    }
}
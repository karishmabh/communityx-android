package com.communityx.session

import com.communityx.application.MyApplication
import com.communityx.models.login.Data
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.*
import com.communityx.utils.AppPreference

object SessionManager : AppConstant {

    public fun setSession(loginData: Data) {
        AppPreference.getInstance().setString(AppConstant.PREF_SESSION_ID, loginData.session.session_id)
        AppPreference.getInstance().setString(AppConstant.PREF_EMAIL, loginData.user.email)
        AppPreference.getInstance().setString(AppConstant.PREF_USERIMAGE, loginData.user.profile.profile_image)
        AppPreference.getInstance().setString(AppConstant.PREF_CATEGORY, loginData.user.category)
        AppPreference.getInstance().setBoolean(AppConstant.PREF_IS_LOGIN, true)

        AppPreference.getInstance().setString(
            PREF_USERNAME,
            if (loginData.user.category == ORGANIZATION) loginData.user.profile.organization_name
            else loginData.user.profile.full_name
        )

        when (loginData.user.category) {
            STUDENT -> AppPreference.getInstance().setString(
                PREF_STANDARD_NAME,
                loginData.user.profile.standard_name
            )
            PROFESSIONAL -> AppPreference.getInstance().setString(
                PREF_COMPANY_NAME,
                loginData.user.profile.company_name
            )
            ORGANIZATION -> AppPreference.getInstance().setString(
                PREF_WEBSITE_NAME,
                loginData.user.profile.website_name
            )
        }
    }

    public fun getSessionId(): String {
        return AppPreference.getInstance(MyApplication.application!!).getString(PREF_SESSION_ID)
    }

    public fun getUserId(): String {
        return AppPreference.getInstance(MyApplication.application!!).getString(USER_ID)
    }

    public fun hasSession(): Boolean {
        return getSessionId() != ""
    }

    public fun clearSession() {
        //todo clear when logout
    }
}
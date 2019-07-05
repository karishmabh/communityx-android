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
        AppPreference.getInstance().setString(AppConstant.PREF_CATEGORY, loginData.user.type)
        AppPreference.getInstance().setBoolean(AppConstant.PREF_IS_LOGIN, true)
        AppPreference.getInstance().setString(PREF_USER_ID, loginData.user.id)

        AppPreference.getInstance().setString(PREF_USERNAME,
            if (loginData.user.type == ORGANIZATION) loginData.user.name
            else loginData.user.name
        )

        when (loginData.user.type) {
            STUDENT -> AppPreference.getInstance().setString(
                PREF_STANDARD_NAME,
                "Student, "+
                if (loginData.user.education!=null && loginData.user.education.size > 0)
                    loginData.user.education.get(0).name
                else ""
            )
            PROFESSIONAL -> AppPreference.getInstance().setString(
                PREF_COMPANY_NAME,

             if (loginData.user.work_experience!=null && loginData.user.work_experience.size > 0)
                 loginData.user.work_experience.get(0).role + ", " +loginData.user.work_experience.get(0).name
             else ""
            )
            ORGANIZATION -> AppPreference.getInstance().setString(
                PREF_WEBSITE_NAME,
                "Organization, "+ loginData.user.name
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
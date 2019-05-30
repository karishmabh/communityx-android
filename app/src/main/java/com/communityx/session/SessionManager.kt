package com.communityx.session

import com.communityx.application.MyApplication
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.SESSION_KEY
import com.communityx.utils.AppConstant.USER_ID
import com.communityx.utils.AppPreference

object SessionManager : AppConstant {

    public fun setSession(sessionId:String, userId:String) {
        AppPreference.getInstance(MyApplication.application!!).setString(SESSION_KEY, sessionId)
        AppPreference.getInstance(MyApplication.application!!).setString(USER_ID, userId)
    }

    public fun getSessionId(): String {
        return AppPreference.getInstance(MyApplication.application!!).getString(SESSION_KEY)
    }

    public fun getUserId(): String {
        return AppPreference.getInstance(MyApplication.application!!).getString(USER_ID)
    }

    public fun hasSession(): Boolean {
        return getSessionId() != ""
    }
}
package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.communityx.R
import com.communityx.utils.AppConstant
import com.communityx.utils.AppPreference

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        isLogin()
    }

    private fun isLogin() {
        if (AppPreference.getInstance(this).getBoolean(AppConstant.PREF_IS_LOGIN)) {
            val handler = Handler()
            handler.postDelayed({
                startActivity(Intent(this, DashboardActivity::class.java))
                overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
                finish()
            }, 3000)
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
            finish()
        }
    }
}

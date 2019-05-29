package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.communityx.R
import com.communityx.models.oauth.OauthData
import com.communityx.network.ResponseListener
import com.communityx.network.ServiceRepo.AuthRepo
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.PREF_IS_LOGIN
import com.communityx.utils.AppPreference
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(), View.OnClickListener , AppConstant {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_welcome)

        //isLogin()
        getBasicAuth()
        setClickListener()
    }

    override fun onClick(v: View?) {
        when (v) {
            button_login -> {
                goToLogin()
            }
            text_signup -> {
                goToSignUp()
            }
        }
    }

    private fun isLogin() {
        if (AppPreference.getInstance(this).getBoolean(PREF_IS_LOGIN)) {

            button_login.visibility = View.GONE
            text_signup.visibility = View.GONE

            val handler = Handler()
            handler.postDelayed({
                startActivity(Intent(this, DashboardActivity::class.java))
                overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
                finish()
            }, 1000)
        }
    }

    private fun setClickListener() {
        button_login.setOnClickListener(this)
        text_signup.setOnClickListener(this)
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
    }

    private fun goToSignUp() {
        startActivity(Intent(this, SignupCategoryActivity::class.java))
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
    }

    private fun getBasicAuth() {
        AuthRepo.getBasicAuth(this, object : ResponseListener<List<OauthData>> {
            override fun onSuccess(response: List<OauthData>) {
                AuthRepo.saveAccessToken(this@WelcomeActivity, response[0].accessToken)
            }

            override fun onError(error: Any) {
                Utils.showError(this@WelcomeActivity, constraint_top, error)
            }
        })
    }
}

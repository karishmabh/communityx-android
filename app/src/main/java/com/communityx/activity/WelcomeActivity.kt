package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.communityx.R
import com.communityx.models.oauth.OauthData
import com.communityx.network.ResponseListener
import com.communityx.network.ServiceRepo.AuthRepo
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_welcome)

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

            }
        })
    }

    companion object {
        private val TAG = "WelcomeActivity"
    }
}

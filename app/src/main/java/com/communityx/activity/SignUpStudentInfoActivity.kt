package com.communityx.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.Window
import android.widget.Toast
import com.communityx.R
import com.communityx.adapters.SignUpPagerAdapter
import com.communityx.custom_views.CustomViewPager
import com.communityx.fragments.*
import com.communityx.models.login.Data
import com.communityx.models.login.LoginRequest
import com.communityx.models.login.LoginResponse
import com.communityx.models.signup.SignUpRequest
import com.communityx.models.signup.SignUpResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.session.SessionManager
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.SESSION_KEY
import com.communityx.utils.AppPreference
import com.communityx.utils.CustomProgressBar
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_sign_up_student_info.*
import kotlinx.android.synthetic.main.layout_top_view_logo.*
import java.util.*

class SignUpStudentInfoActivity : BaseActivity(), AppConstant, View.OnClickListener {

    private var pagerAdapter: SignUpPagerAdapter? = null
    var selectedCategory: String? = null
    var signUpRequest: SignUpRequest? = null
    var selectImagePath: String? = null
    var manaualInterest: MutableList<String>? = null
    var isOtpVerified = false
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_sign_up_student_info)

        getIntentData()
        initActivity()
        setUpViewPager()

        text_login.setOnClickListener(this)
        button_continue.setOnClickListener(this)
    }

    private fun initActivity() {
        signUpRequest = selectedCategory?.let {
            SignUpRequest(role = it)
        }
        text_subtitle.text = getString(R.string.string_build_social_impact)
        button_continue.tag = true
        button_continue.setBackgroundResource(R.drawable.button_active)
        enableButton(false)
    }

    private fun getIntentData() {
        selectedCategory = intent.action
    }

    override fun onClick(v: View?) {
        when {
            v?.id == R.id.text_login -> goToLogin()
            v?.id == R.id.button_continue -> tappedContinue()
        }
    }

    private fun setUpViewPager() {
        pagerAdapter = SignUpPagerAdapter(supportFragmentManager)
        pagerAdapter?.setFragments(getFragments(selectedCategory!!))
        view_pager.adapter = pagerAdapter
        view_pager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none)
        view_pager.setmSwipeDirectionListener(object : CustomViewPager.SwipeDirectionListener {
            override fun onSwipe(direction: CustomViewPager.SwipeDirection) {

            }

            override fun onPageChange(position: Int) {
                enableButton(pagerAdapter!!.isButtonEnabled(position))
                button_continue!!.setText(if (position == pagerAdapter!!.totalItems - 1) R.string.submit else R.string.continue_button)
            }
        })
        dots_indicator.setViewPager(view_pager)
        dots_indicator.setDotsClickable(false)
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        overridePendingTransition(R.anim.anim_prev_slide_in, R.anim.anim_prev_slide_out)
        finish()
    }

    private fun tappedContinue() {
        pagerAdapter?.getCurrentFragment(view_pager.currentItem)?.onContinueButtonClicked()
    }

    fun changeButtonStatus(pos:Int, value: Boolean) {
        pagerAdapter?.setButtonEnabled(pos, value)
    }

    fun goToNextPage() {
        if (view_pager.currentItem == 0) {
            completedSignUp()
            return
        }
        view_pager?.setCurrentItem(view_pager!!.currentItem + 1, true)
    }

    fun changeContinueClick(value: Boolean) {
        button_continue.isClickable = value
    }

    fun enableButton(enable: Boolean?) {
        enable?.let {
            Utils.enableButton(button_continue, it)
        }
    }

    private fun navigateToConnectAlies(loginData: Data) {
        val intent = Intent(this@SignUpStudentInfoActivity, ConnectAlliesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(SESSION_KEY, loginData.session.id)
        startActivity(intent)
        finish()
    }

    private fun getFragments(selectedCategory: String): List<Fragment> {
        val fragments = ArrayList<Fragment>()
        when (selectedCategory) {
            AppConstant.STUDENT -> {
                fragments.add(SignUpStudentInfoFragment())
                fragments.add(SignUpSchoolCollegeFragment())
                fragments.add(SignUpRoleFragment())
                fragments.add(SignUpMemberOfClub())
            }
            AppConstant.PROFESSIONAL -> {
                fragments.add(SignUpStudentInfoFragment())
                fragments.add(SignUpProfessional())
                fragments.add(SignUpMemberOfClub())
            }
            AppConstant.ORGANIZATION -> fragments.add(SignUpOrganizationFragment())
        }
        fragments.add(SignUpSelectInterest())
        return fragments
    }

    private fun completedSignUp() {
        dialog = CustomProgressBar.getInstance(this).showProgressDialog(getString(R.string.please_wait_while_register))
        SignUpRepo.createSignUp(this, signUpRequest!!, object : ResponseListener<SignUpResponse> {
            override fun onSuccess(response: SignUpResponse) {
                dialog.dismiss()
                AppPreference.getInstance().setString(AppConstant.PREF_USER_ID, response.data.get(0).user_id)
                Toast.makeText(this@SignUpStudentInfoActivity, "You have successfully signed up!", Toast.LENGTH_LONG).show()
                view_pager?.setCurrentItem(view_pager!!.currentItem + 1, true)
            }

            override fun onError(error: Any) {
                Utils.showError(this@SignUpStudentInfoActivity, constraint_layout, error)
                dialog.dismiss()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {
        if (view_pager.currentItem > 0) {
            view_pager?.setCurrentItem(view_pager!!.currentItem - 1, true)
            return
        }
        super.onBackPressed()
    }

    private fun performLogin(phoneNumber: String, password: String) {
        DataManager.doLogin(this, LoginRequest(phoneNumber, password), object : ResponseListener<LoginResponse> {
            override fun onSuccess(response: LoginResponse) {

                dialog.dismiss()
                val loginData = response.data[0]
                saveUserData(loginData)
            }

            override fun onError(error: Any) {
                dialog.dismiss()
                Utils.showError(this@SignUpStudentInfoActivity, constraint_layout, error)
            }

        })
    }

    private fun saveUserData(loginData: Data) {
        SessionManager.setSession(loginData)
        navigateToConnectAlies(loginData)
    }
}
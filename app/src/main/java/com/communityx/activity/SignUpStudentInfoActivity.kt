package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.communityx.R
import com.communityx.adapters.SignUpPagerAdapter
import com.communityx.custom_views.CustomViewPager
import com.communityx.fragments.*
import com.communityx.models.signup.SignUpRequest
import com.communityx.models.signup.SignUpResponse
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.USER_ID
import com.communityx.utils.DialogHelper
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_sign_up_student_info.*
import kotlinx.android.synthetic.main.layout_top_view_logo.*
import java.util.*

class SignUpStudentInfoActivity : AppCompatActivity(), AppConstant, View.OnClickListener {

    private var pagerAdapter: SignUpPagerAdapter? = null
    public var selectedCategory: String? = null
    var signUpRequest : SignUpRequest? = null
    public var selectedClubNameIndex = 0
    public var selectedRole = 0
    var selectImagePath: String? = null
    var manaualInterest: MutableList<String>? = null
    var isOtpVerifed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_student_info)

        getIntentData()
        initActivity()
        setUpViewPager()

        text_login.setOnClickListener(this)
        button_continue.setOnClickListener(this)
    }

    private fun initActivity() {
        signUpRequest = selectedCategory?.let { SignUpRequest(role = it) }
        text_subtitle.text = getString(R.string.string_build_social_impact)
        button_continue.tag = true
        button_continue.setBackgroundResource(R.drawable.button_active)
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
        view_pager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.left)
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
        startActivity(
            Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        finish()
    }

    private fun tappedContinue() {
        pagerAdapter?.getCurrentFragment(view_pager.currentItem)?.onContinueButtonClicked()
    }

    fun goToNextPage(){
        if (view_pager.currentItem == pagerAdapter!!.totalItems - 1) {
            completedSignUp()
            return
        }
        view_pager?.setCurrentItem(view_pager!!.currentItem + 1, true)
    }

    fun enableButton(enable: Boolean?) {
        enable?.let { Utils.enableButton(button_continue, it) }
    }

    private fun navigateToConnectAlies(intent: Intent) {
        startActivity(intent)
        finish()
    }

    private fun getFragments(selectedCategory: String): List<Fragment> {
        val fragments = ArrayList<Fragment>()
        when (selectedCategory) {
            AppConstant.ACTION_SIGN_UP_STUDENT -> {
                fragments.add(SignUpStudentInfoFragment())
                fragments.add(SignUpSchoolCollegeFragment())
                fragments.add(SignUpRoleFragment())
                fragments.add(SignUpMemberOfClub())
            }
            AppConstant.ACTION_SIGN_UP_PROFESSIONAL -> {
                fragments.add(SignUpStudentInfoFragment())
                fragments.add(SignUpProfessional())
                fragments.add(SignUpMemberOfClub())
            }
            AppConstant.ACTION_SIGN_UP_ORGANIZATION -> fragments.add(SignUpOrganizationFragment())
        }
        fragments.add(SignUpSelectInterest())
        return fragments
    }

    //todo : hard coded string
    private fun completedSignUp() {
        var dialog = DialogHelper.showProgressDialog(this, "Please wait... Registering you")
        SignUpRepo.createSignUp(this, signUpRequest!!, object : ResponseListener<SignUpResponse> {
            override fun onSuccess(response: SignUpResponse) {
                val intent =  Intent(this@SignUpStudentInfoActivity, ConnectAlliesActivity::class.java)
                intent.putExtra(USER_ID, response.data[0].user_id)
                navigateToConnectAlies(intent)
                dialog.dismiss()
            }

            override fun onError(error: Any) {
                Utils.showError(this@SignUpStudentInfoActivity, constraint_layout, error)
                dialog.dismiss()
            }
        })
    }
}
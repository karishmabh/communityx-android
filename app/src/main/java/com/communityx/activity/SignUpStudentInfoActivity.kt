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
import com.communityx.models.signup.Error
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.models.signup.StudentSignUpResponse
import com.communityx.network.ResponseListener
import com.communityx.network.ServiceRepo.SignUpRepo
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.ACTION_SIGN_UP_STUDENT
import com.communityx.utils.AppConstant.USER_ID
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_sign_up_student_info.*
import kotlinx.android.synthetic.main.layout_top_view_logo.*
import java.util.*

class SignUpStudentInfoActivity : AppCompatActivity(), AppConstant, View.OnClickListener {

    private var pagerAdapter: SignUpPagerAdapter? = null
    private var selectedCategory: String? = null
    var studentSignUpRequest : StudentSignUpRequest? = null
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
        studentSignUpRequest = selectedCategory?.let { StudentSignUpRequest(role = it) }
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
                if (selectedCategory != AppConstant.ACTION_SIGN_UP_STUDENT) {
                    return
                }

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
        if (view_pager.currentItem == pagerAdapter!!.totalItems - 1) {
            completedSignUp()
            return
        }
        pagerAdapter?.getCurrentFragment(view_pager.currentItem)?.onContinueButtonClicked()
    }

    fun goToNextPage(){
        view_pager?.setCurrentItem(view_pager!!.currentItem + 1, true)
        //enableButton(false)
    }

    fun enableButton(enable: Boolean?) {
        enable?.let { Utils.enableButton(button_continue, it) }
    }

    private fun navigateToConnectAlies(intent: Intent) {
        startActivity(intent)
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

    private fun completedSignUp() {
        when(selectedCategory) {
           ACTION_SIGN_UP_STUDENT -> studentSignUp()
        }
    }

    private fun studentSignUp() {
        SignUpRepo.studentSignUp(this,studentSignUpRequest!!,object: ResponseListener<StudentSignUpResponse> {
            override fun onSuccess(response: StudentSignUpResponse) {
                val intent =  Intent(this@SignUpStudentInfoActivity, ConnectAlliesActivity::class.java)
                intent.putExtra(USER_ID, response.data[0].id)
                navigateToConnectAlies(intent)
            }

            override fun onError(error: Any) {
               if(error is Error) {
                   SnackBarFactory.createSnackBar(this@SignUpStudentInfoActivity,constraint_layout,error.error_message.toString())
               }
            }

        })
    }
}
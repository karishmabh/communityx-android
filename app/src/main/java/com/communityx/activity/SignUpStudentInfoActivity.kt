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
import com.communityx.utils.AppConstant
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_sign_up_student_info.*
import kotlinx.android.synthetic.main.layout_top_view_logo.*
import java.util.*

class SignUpStudentInfoActivity : AppCompatActivity(), AppConstant, View.OnClickListener {

    private var pagerAdapter: SignUpPagerAdapter? = null
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_student_info)

        initActivity()
        setUpViewPager()

        text_login.setOnClickListener(this)
        button_continue.setOnClickListener(this)
    }

    private fun initActivity() {
        getIntentData()
        text_subtitle.text = "Build your social impact identity on CommunityX."
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
            sendToActivity()
            return
        }
        view_pager?.setCurrentItem(view_pager!!.currentItem + 1, true)
        if (selectedCategory != AppConstant.ACTION_SIGN_UP_STUDENT) {
            return
        }
        val isEnabled = pagerAdapter?.isButtonEnabled(view_pager!!.currentItem)
        enableButton(isEnabled)
    }

    fun enableButton(enable: Boolean?) {
        enable?.let { Utils.enableButton(button_continue, it) }
    }

    private fun sendToActivity() {
        startActivity(Intent(this, ConnectAlliesActivity::class.java))
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
}
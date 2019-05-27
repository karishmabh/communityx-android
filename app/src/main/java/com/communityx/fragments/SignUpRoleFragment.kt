package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.utils.SnackBarFactory
import kotlinx.android.synthetic.main.fragment_sign_up_select_role.*
import java.util.*

class SignUpRoleFragment : BaseSignUpFragment(), View.OnClickListener {

    private var standardYear: String? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_select_role, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initField()
        enableButton(true)
        view_freshman_main.setOnClickListener(this)
        view_sophomore_main.setOnClickListener(this)
        view_junior_main.setOnClickListener(this)
        view_senior_main.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        tappedRole(v)
    }

    override fun onContinueButtonClicked() {
        if(setFieldsData()) {
            goToNextPage()
            enableButton(true)
        }

    }

    override fun setFieldsData(): Boolean {
        signUpRequest?.standard_year = standardYear
        return validateEmpty(signUpRequest)
    }

    override fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean): Boolean {
        val b = !TextUtils.isEmpty(requestData?.standard_year)
        if(b && showSnackbar) SnackBarFactory.createSnackBar(context,constraint_layout, "Please select your classification")
        return  b
    }

    private fun initField() {
        if(validateEmpty(signUpRequest,false)) {
            selectRole(Role.valueOf(signUpRequest?.standard_year!!))
            enableButton(true)
        }
    }

    private fun tappedRole(view: View?) {
        when (view) {
            view_freshman_main -> selectRole(Role.FRESHMAN)
            view_sophomore_main -> selectRole(Role.SOPHOMORE)
            view_junior_main -> selectRole(Role.JUNIOR)
            view_senior_main -> selectRole(Role.SENIOR)
        }
    }

    private fun selectRole(role: Role) {
        (Objects.requireNonNull<FragmentActivity>(activity) as SignUpStudentInfoActivity).enableButton(true)
        standardYear = role.name
        when (role) {
            SignUpRoleFragment.Role.FRESHMAN -> {
                view_freshman_main.background = resources.getDrawable(R.drawable.border_orange_bg)
                view_sophomore_main.background = resources.getDrawable(R.drawable.bordered_bg)
                view_junior_main.background = resources.getDrawable(R.drawable.bordered_bg)
                view_senior_main.background = resources.getDrawable(R.drawable.bordered_bg)

                image_freshman.setImageResource(R.drawable.ic_signup_freshman_select)
                image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
                image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)
                image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

                text_freshman.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_sophomore.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_junior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_senior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                image_freshman_tick.visibility = View.VISIBLE
                image_sophomore_tick.visibility = View.GONE
                image_junior_tick.visibility = View.GONE
                image_senior_tick.visibility = View.GONE
            }

            SignUpRoleFragment.Role.SOPHOMORE -> {
                view_sophomore_main.background = resources.getDrawable(R.drawable.border_orange_bg)
                view_freshman_main.background = resources.getDrawable(R.drawable.bordered_bg)
                view_junior_main.background = resources.getDrawable(R.drawable.bordered_bg)
                view_senior_main.background = resources.getDrawable(R.drawable.bordered_bg)

                image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_select)
                image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
                image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)
                image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

                text_sophomore.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_freshman.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_junior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_senior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                image_sophomore_tick.visibility = View.VISIBLE
                image_freshman_tick.visibility = View.GONE
                image_junior_tick.visibility = View.GONE
                image_senior_tick.visibility = View.GONE
            }

            SignUpRoleFragment.Role.JUNIOR -> {
                view_junior_main.background = resources.getDrawable(R.drawable.border_orange_bg)
                view_freshman_main.background = resources.getDrawable(R.drawable.bordered_bg)
                view_sophomore_main.background = resources.getDrawable(R.drawable.bordered_bg)
                view_senior_main.background = resources.getDrawable(R.drawable.bordered_bg)

                image_junior.setImageResource(R.drawable.ic_signup_junior_select)
                image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
                image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
                image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

                text_junior.setTextColor(resources.getColor(R.color.colorBlackTitle))
                text_freshman.setTextColor(resources.getColor(R.color.colorLightestGrey))
                text_sophomore.setTextColor(resources.getColor(R.color.colorLightestGrey))
                text_senior.setTextColor(resources.getColor(R.color.colorLightestGrey))

                image_junior_tick.visibility = View.VISIBLE
                image_freshman_tick.visibility = View.GONE
                image_sophomore_tick.visibility = View.GONE
                image_senior_tick.visibility = View.GONE
            }

            SignUpRoleFragment.Role.SENIOR -> {
                view_senior_main.background = resources.getDrawable(R.drawable.border_orange_bg)
                view_freshman_main.background = resources.getDrawable(R.drawable.bordered_bg)
                view_sophomore_main.background = resources.getDrawable(R.drawable.bordered_bg)
                view_junior_main.background = resources.getDrawable(R.drawable.bordered_bg)

                image_senior.setImageResource(R.drawable.ic_signup_senior_select)
                image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
                image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
                image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)

                text_senior.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_freshman.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_sophomore.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_junior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                image_senior_tick.visibility = View.VISIBLE
                image_freshman_tick.visibility = View.GONE
                image_sophomore_tick.visibility = View.GONE
                image_junior_tick.visibility = View.GONE
            }
        }
    }

    private enum class Role {
        FRESHMAN,
        SOPHOMORE,
        JUNIOR,
        SENIOR
    }
}

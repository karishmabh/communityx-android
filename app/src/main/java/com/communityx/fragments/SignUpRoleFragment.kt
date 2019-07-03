package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.DataX
import com.communityx.models.signup.SignUpRequest
import com.communityx.models.signup.club.ClubResponse
import com.communityx.models.signup.institute.InstituteRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.*
import com.communityx.utils.AppConstant.PREF_USER_ID
import com.communityx.utils.AppConstant.USER_ID
import kotlinx.android.synthetic.main.fragment_sign_up_select_role.*
import java.util.*

class SignUpRoleFragment : BaseSignUpFragment(), View.OnClickListener , AppConstant {

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
            addInstitute()
        }
    }

    override fun setFieldsData(): Boolean {
        signUpStudent?.standard_year = standardYear
        return validateEmpty(signUpStudent)
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        val b = !TextUtils.isEmpty(requestData?.standard_year)
        if (!b && showSnackbar) SnackBarFactory.createSnackBar(
            context,
            constraint_layout,
            getString(R.string.please_select_qualification)
        )
        return  b
    }

    private fun initField() {
        if(validateEmpty(signUpStudent,false)) {
            selectRole(Role.valueOf(signUpStudent?.standard_year!!))
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

    private fun addInstitute() {
        val instituteRequest = InstituteRequest(
            signUpStudent?.standard_name!!,
            signUpStudent?.standard_year!!,
            signUpStudent?.standard!!,
            AppPreference.getInstance(activity!!).getString(PREF_USER_ID))

        var dialog = CustomProgressBar.getInstance(activity!!).showProgressDialog(getString(R.string.please_wait_while_register))
        SignUpRepo.addInstitute(activity!!, instituteRequest, object : ResponseListener<List<DataX>> {
            override fun onSuccess(response: List<DataX>) {
                dialog.dismiss()
                changeButtonStatus(2, true)
                goToNextPage()
                enableButton(true)
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
                dialog.dismiss()
            }
        })
    }

    private enum class Role {
        FRESHMAN,
        SOPHOMORE,
        JUNIOR,
        SENIOR
    }
}

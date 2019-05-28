package com.communityx.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatSpinner
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.utils.SnackBarFactory
import kotlinx.android.synthetic.main.fragment_sign_up_member_of_club.*

import java.util.Objects

class SignUpMemberOfClub : BaseSignUpFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_member_of_club, null)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initField()
        spinner_club_name!!.adapter = ArrayAdapter(
            Objects.requireNonNull<Context>(context),
            R.layout.item_member_of_club,
            R.id.text_item,
            arrayOf(
                "Amnesty International",
                "Best Buddies",
                "Rotary Interact",
                "DECA",
                "Model United Nations",
                "Gay/Straight Alliance",
                "National Business Honor Society",
                "National Technology Honor Society",
                "Mock Trial Club",
                "Student Government",
                "Yoga Club",
                "Animal Rights Organizatio",
                "Entrepreneur Club",
                "Human Rights Club"
            )
        )
        spinner_role!!.adapter =
            ArrayAdapter(context!!, R.layout.item_member_of_club, R.id.text_item, arrayOf("President", "President"))
    }

    override fun onContinueButtonClicked() {
        if(setFieldsData()) goToNextPage()
    }

    override fun setFieldsData(): Boolean {
        signUpRequest?.club_name = spinner_club_name.selectedItem as String
        signUpRequest?.club_role = spinner_role.selectedItem as String
        signUpActivity?.selectedClubNameIndex = spinner_club_name.selectedItemPosition
        signUpActivity?.selectedRole = spinner_role.selectedItemPosition

        return validateEmpty(signUpRequest)
    }

    override fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean): Boolean {
        var b = true
        when {
            TextUtils.isEmpty(signUpRequest?.club_name) -> b = false
            TextUtils.isEmpty(signUpRequest?.club_role) -> b = false
        }
        if(!b && showSnackbar) SnackBarFactory.createSnackBar(context,constraint_layout,"Please select any club or organization")
        return b
    }

    private fun initField() {
        if(validateEmpty(signUpRequest, false)) {
            spinner_club_name.setSelection(signUpActivity?.selectedClubNameIndex!!)
            spinner_role.setSelection(signUpActivity?.selectedRole!!)
        }
    }

}

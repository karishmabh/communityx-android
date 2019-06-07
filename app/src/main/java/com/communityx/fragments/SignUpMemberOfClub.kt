package com.communityx.fragments


import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.*
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_sign_up_member_of_club.*
import java.util.*
import kotlin.collections.ArrayList

class SignUpMemberOfClub : BaseSignUpFragment(), AppConstant {

    private var clubList: List<Club> = ArrayList()
    private var causeList: List<Causes> = ArrayList()
    private var clubListName: MutableList<String> = mutableListOf()
    private var roleList: MutableList<String> = mutableListOf()
    private var mCategory: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_member_of_club, null)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkCategory()
    }

    private fun checkCategory() {
        mCategory = (activity as SignUpStudentInfoActivity).selectedCategory

        if (mCategory.equals(AppConstant.PROFESSIONAL)) {
            textinput_club_cause.hint = getString(R.string.organization_or_group)
            text_heading.text = resources.getString(R.string.member_of_casuse_driven_org)
        } else {
            initField()
        }

        getRole()
    }

    override fun onContinueButtonClicked() {
        if (setFieldsData()) goToNextPage()
    }

    @OnTextChanged(R.id.edit_club)
    fun searchCausesClub(charSequence: CharSequence) {
        if (charSequence.length > 1) {

            when (category) {
                AppConstant.STUDENT -> {
                    getClubAndRole(charSequence.toString())
                }
                AppConstant.PROFESSIONAL -> {
                    getCauseAndRole(charSequence.toString())
                }
            }
        }
    }

    override fun setFieldsData(): Boolean {
        if (mCategory.equals(AppConstant.PROFESSIONAL)) {
            signUpStudent?.cause_id = edit_club.text.toString()
            signUpStudent?.cause_role = edit_role.text.toString().toUpperCase()
        } else {
            signUpStudent?.club_id = edit_club.text.toString()
            signUpStudent?.club_role = edit_role.text.toString().toUpperCase()
        }

        return validateEmpty(signUpStudent)
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        var b = true
        when {
            category == AppConstant.STUDENT && TextUtils.isEmpty(signUpStudent?.club_role) -> b = false
            category == AppConstant.PROFESSIONAL && TextUtils.isEmpty(signUpStudent?.cause_role) -> b = false
            category == AppConstant.PROFESSIONAL && TextUtils.isEmpty(signUpStudent?.cause_id) -> b = false
            category == AppConstant.STUDENT && TextUtils.isEmpty(signUpStudent?.club_id) -> b = false
        }

        if (!b && showSnackbar) SnackBarFactory.createSnackBar(
            context,
            constraint_layout,
            getString(R.string.select_club_organization)
        )
        return b
    }

    private fun initField() {
        if (validateEmpty(signUpStudent, false)) {
            edit_role.setText(signUpStudent?.club_role)
            edit_club.setText(if (category == AppConstant.PROFESSIONAL) signUpStudent?.cause_id else signUpStudent?.club_id)
        }
    }

    private fun getClubAndRole(query: String) {
        SignUpRepo.getClubAndRoles(query, object : ResponseListener<ClubAndRoleData> {
            override fun onSuccess(response: ClubAndRoleData) {
                clubList = response.clubs
                createClubDataId(clubList)
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
            }

        })
    }

    private fun getRole() {
        SignUpRepo.getRoles(object : ResponseListener<RoleResponse> {
            override fun onSuccess(response: RoleResponse) {
                createRoleString(response.data[0])
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
            }
        })
    }

    private fun createRoleString(data: List<RoleData>) {
        roleList.clear()
        data.forEach {
            roleList.add(it.name)
        }
        setRoleData(roleList)
    }

    private fun getCauseAndRole(query: String) {
        SignUpRepo.getCauseAndRoles(query, object : ResponseListener<ClubAndRoleData> {
            override fun onSuccess(response: ClubAndRoleData) {
                createCauseDataId(response.causes)
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
            }
        })
    }

    private fun createClubDataId(clubList: List<Club>) {
        clubListName.clear()
        clubList.forEach {
            clubListName.add(it.name)
        }
        setClubData(clubListName)
    }

    private fun createCauseDataId(clubList: List<Causes>) {
        clubListName.clear()
        clubList.forEach {
            clubListName.add(it.name)
        }
        setClubData(clubListName)
    }

    private fun setRoleData(role: List<String>) {
        val arrayAdapter =
            ArrayAdapter(context!!, R.layout.item_member_of_club, R.id.text_item, role)

        edit_role.setAdapter(arrayAdapter)
    }

    private fun setClubData(club: List<String>) {
        val arrayAdapter = ArrayAdapter(
            Objects.requireNonNull<Context>(context),
            R.layout.item_member_of_club,
            R.id.text_item,
            club
        )
        edit_club.setAdapter(arrayAdapter)
    }
}

package com.communityx.fragments


import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.Club
import com.communityx.models.signup.ClubAndRoleData
import com.communityx.models.signup.SignUpRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_sign_up_member_of_club.*
import java.util.*
import kotlin.collections.ArrayList

class SignUpMemberOfClub : BaseSignUpFragment() {

    private var clubList: List<Club> = ArrayList()
    private var clubListName: MutableList<String> = mutableListOf()

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
        getClubAndRole()
    }

    override fun onContinueButtonClicked() {
        if(setFieldsData()) goToNextPage()
    }

    override fun setFieldsData(): Boolean {
        signUpStudent?.club_id = clubList[spinner_club_name.selectedItemPosition].id
        signUpStudent?.club_role = spinner_role.selectedItem as String
        signUpActivity?.selectedClubNameIndex = spinner_club_name.selectedItemPosition
        signUpActivity?.selectedRole = spinner_role.selectedItemPosition

        return validateEmpty(signUpStudent)
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        var b = true
        when {
            TextUtils.isEmpty(signUpStudent?.club_id) -> b = false
            TextUtils.isEmpty(signUpStudent?.club_role) -> b = false
        }
        if (!b && showSnackbar) SnackBarFactory.createSnackBar(
            context,
            constraint_layout,
            getString(R.string.select_club_organization)
        )
        return b
    }

    private fun initField() {
        if(validateEmpty(signUpStudent, false)) {
            spinner_club_name.setSelection(signUpActivity?.selectedClubNameIndex!!)
            spinner_role.setSelection(signUpActivity?.selectedRole!!)
        }
    }

    private fun getClubAndRole() {
        SignUpRepo.getClubAndRoles(object : ResponseListener<ClubAndRoleData> {
            override fun onSuccess(response: ClubAndRoleData) {
                clubList = response.clubs
                setRoleData(response.roles)
                createClubDataId(clubList)
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
            }

        })
    }

    private fun createClubDataId(clubList: List<Club>) {
        clubList.forEach {
            clubListName.add(it.name)
        }
        setClubData(clubListName)
    }

    private fun setRoleData(role: List<String>) {
        spinner_role!!.adapter =
            ArrayAdapter(context!!, R.layout.item_member_of_club, R.id.text_item, role)
    }

    private fun setClubData(club: List<String>) {
        spinner_club_name!!.adapter = ArrayAdapter(
            Objects.requireNonNull<Context>(context),
            R.layout.item_member_of_club,
            R.id.text_item,
            club
        )
    }
}

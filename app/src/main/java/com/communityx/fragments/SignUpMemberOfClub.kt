package com.communityx.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
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
    private var addedItems = mutableListOf<String>()

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

        addedItems.clear()
        if (mCategory.equals(AppConstant.PROFESSIONAL)) {
            textinput_club_cause.hint = getString(R.string.organization_or_group)
            text_heading.text = resources.getString(R.string.member_of_casuse_driven_org)

            var items = signUpStudent?.cause
            if (items != null) {
                for (i in items.indices) {
                    var name = signUpStudent?.cause?.get(i)!!.cause_name
                    var role = signUpStudent?.cause?.get(i)!!.cause_role

                    addedItems.add(name + "/ " + role)
                }
            }
        } else {
            var items = signUpStudent?.club
            if (items != null) {
                for (i in items.indices) {
                    var name = signUpStudent?.club?.get(i)!!.club_name
                    var role = signUpStudent?.club?.get(i)!!.club_role

                    addedItems.add(name + "/ " + role)
                }
            }
        }

        getRole()
        checkPreviouslyAddedItems()
    }

    @OnClick(R.id.button_add)
    fun onClubCauseAdded() {
        if (validateFields()) {
            initFlexLayout()
        }
    }

    fun checkPreviouslyAddedItems() {
        val view = LayoutInflater.from(context).inflate(R.layout.item_suggest_cause, null)
        val textView = view.findViewById<TextView>(R.id.text_suggest_cause)
        val imageCross = view.findViewById<ImageView>(R.id.image_cross)

        imageCross.setOnClickListener { v1 ->
            flex_layout_cause.removeView(view)

            addedItems.remove(textView.text.toString())
        }

        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(10, 10, 10, 10)


        for (i in addedItems.indices) {
            textView.text = addedItems.get(i)
            flex_layout_cause.addView(view, lp)
            text_header.visibility = View.VISIBLE
        }
    }

    private fun initFlexLayout() {
        val suggestedCause = edit_club.text.toString() + "/ " + edit_role.text.toString()

        val view = LayoutInflater.from(context).inflate(R.layout.item_suggest_cause, null)
        val textView = view.findViewById<TextView>(R.id.text_suggest_cause)
        val imageCross = view.findViewById<ImageView>(R.id.image_cross)
        textView.text = suggestedCause

        imageCross.setOnClickListener { v1 ->
            flex_layout_cause.removeView(view)
            addedItems.remove(textView.text.toString())

            if (addedItems.size == 0) {
                text_header.visibility = View.GONE
                enableButton(false)
            }
        }

        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(10, 10, 10, 10)

        addedItems.add(suggestedCause)
        text_header.visibility = View.VISIBLE

        if (addedItems.size > 0) {
            enableButton(true)
        }

        flex_layout_cause.addView(view, lp)

        edit_club.text.clear()
        edit_role.text.clear()

        Utils.hideSoftKeyboard(activity)
    }

    private fun validateFields(): Boolean {
        if (TextUtils.isEmpty(edit_club.text.toString())) {
            if (mCategory.equals(AppConstant.PROFESSIONAL)) {
                SnackBarFactory.createSnackBar(context, constraint_layout, getString(R.string.select_organization_group))
            } else {
                SnackBarFactory.createSnackBar(context, constraint_layout, getString(R.string.select_club_organization))
            }
            return false
        }

        if (TextUtils.isEmpty(edit_role.text.toString())) {
            SnackBarFactory.createSnackBar(context, constraint_layout, getString(R.string.select_role))
            return false
        }

        addedItems.forEach {
            var itemAdded = it
            var splitted = itemAdded.split("/")
            var name = splitted.get(0).trim()

            if (name.equals(edit_club.text.toString().trim(), true)) {
                if (mCategory.equals(AppConstant.PROFESSIONAL)) {
                    SnackBarFactory.createSnackBar(context, constraint_layout, getString(R.string.previously_added_cause))
                } else if (mCategory.equals(AppConstant.STUDENT)) {
                    SnackBarFactory.createSnackBar(context, constraint_layout, getString(R.string.previously_added_club))
                }
                return false
            }
        }

        return true
    }

    override fun onContinueButtonClicked() {
        if (setFieldsData()) {
            if (mCategory.equals(AppConstant.PROFESSIONAL)) {
                changeButtonStatus(2, true)
            } else {
                changeButtonStatus(3, true)
            }
            goToNextPage()
        }
    }

    @OnTextChanged(R.id.edit_club)
    fun searchCausesClub(charSequence: CharSequence) {
        when (category) {
            AppConstant.STUDENT -> {
                getClubAndRole(charSequence.toString())
            }

            AppConstant.PROFESSIONAL -> {
                getCauseAndRole(charSequence.toString())
            }
        }
    }

    override fun setFieldsData(): Boolean {
        if (addedItems.size == 0) {
            SnackBarFactory.createSnackBar(context, constraint_layout, getString(R.string.select_club_organization))
            return false
        }

        signUpStudent?.cause?.clear()
        signUpStudent?.club?.clear()

        for (i in addedItems.indices) {
            var text = addedItems.get(i)
            var ArrayItem = text.split("/")

            if (mCategory.equals(AppConstant.PROFESSIONAL)) {
                signUpStudent?.cause?.add(CauseData(ArrayItem.get(0).trim(), ArrayItem.get(1).trim()))
            } else {
                signUpStudent?.club?.add(ClubData(ArrayItem.get(0).trim(), ArrayItem.get(1).trim()))
            }
        }

        return true
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        return false
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
        edit_role.threshold = 1
    }

    private fun setClubData(club: List<String>) {
        val arrayAdapter = ArrayAdapter(
            Objects.requireNonNull<Context>(context),
            R.layout.item_member_of_club,
            R.id.text_item,
            club
        )
        edit_club.setAdapter(arrayAdapter)
        edit_club.threshold = 1
    }
}

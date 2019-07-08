package com.communityx.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.adapters.ClubsAdapter
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.*
import com.communityx.models.signup.cause.CauseRequest
import com.communityx.models.signup.club.ClubRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.PREF_USER_ID
import com.communityx.utils.AppConstant.STUDENT
import com.communityx.utils.AppPreference
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_sign_up_member_of_club.*
import java.util.*
import kotlin.collections.ArrayList

class SignUpMemberOfClub : BaseSignUpFragment(), AppConstant , ClubsAdapter.IClubCallback {

    private var clubList: List<Club> = ArrayList()
    private var clubListName: MutableList<String> = mutableListOf()
    private var roleList: MutableList<String> = mutableListOf()
    private var mCategory: String? = null

    public var causesDataList : ArrayList<CauseData> = ArrayList()
    public var clubsAdapter : ClubsAdapter? = null
    private var clickContinue :Boolean = false
    private var isProfessional: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_member_of_club, null)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkCategory()
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        return false
    }

    override fun setFieldsData(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @OnClick(R.id.fab_add)
    fun addTapped() {
       openAddCauseClubDialog()
    }

    fun openAddCauseClubDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_add_club_member)
        dialog.setCancelable(true)

        val layoutParams = dialog.window!!.attributes
        val window = dialog.window
        layoutParams.copyFrom(window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = layoutParams
        layoutParams.gravity = Gravity.CENTER

        var buttonAdd : Button = dialog.findViewById(R.id.button_add)
        var imageClose : ImageView = dialog.findViewById(R.id.image_close)
        var textInputClub : TextInputLayout = dialog.findViewById(R.id.textinput_club_cause)
        var textheading : TextView = dialog.findViewById(R.id.text_heading)

        textheading.text = if (isProfessional) resources.getString(R.string.are_you_a_member_of_any_cause)
                            else resources.getString(R.string.are_you_a_member_of_any_clubs)

        textInputClub.hint = if (isProfessional) resources.getString(R.string.cause_name) else resources.getString(R.string.club_name)

        var editClub : AutoCompleteTextView = dialog.findViewById(R.id.edit_club)
        var editRole : AutoCompleteTextView = dialog.findViewById(R.id.edit_role)

        buttonAdd.isClickable = false

        getRole(editRole)
        editRole.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && editClub.text.isNotEmpty()) {
                    buttonAdd.isClickable = true
                    buttonAdd.setBackgroundResource(R.drawable.button_active)
                } else{
                    buttonAdd.isClickable = false
                    buttonAdd.setBackgroundResource(R.drawable.button_inactive)
                }
            }
        })

        editClub.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && editRole.text.isNotEmpty()) {
                    buttonAdd.isClickable = true
                    buttonAdd.setBackgroundResource(R.drawable.button_active)
                } else{
                    buttonAdd.isClickable = false
                    buttonAdd.setBackgroundResource(R.drawable.button_inactive)
                }

                when (category) {
                    AppConstant.STUDENT -> {
                        getClubAndRole(s.toString(), editClub)
                    }

                    AppConstant.PROFESSIONAL -> {
                        getCauseAndRole(s.toString(), editClub)
                    }
                }
            }
        })

        imageClose.setOnClickListener {
            dialog.dismiss()
        }

        buttonAdd.setOnClickListener {
            causesDataList.add(CauseData(editClub.text.toString(), editRole.text.toString()))
            dialog.dismiss()

            if (clubsAdapter == null) {
                showInList()
            } else {
                clubsAdapter?.notifyDataSetChanged()
            }

            checkVisibility()
        }

        dialog.show()
    }

    private fun checkVisibility() {
        if (causesDataList.size > 0) {
            setVisibility(true)
            enableButton(true)
        } else {
            setVisibility(false)
            enableButton(false)
        }
    }

    fun showInList() {
        recycler_club_members?.layoutManager = LinearLayoutManager(activity)
        clubsAdapter = ClubsAdapter(causesDataList, activity!!, this, this)
        recycler_club_members?.adapter = clubsAdapter
    }

    fun setVisibility(hasData: Boolean) {
        constraint_no_selection.visibility = if (hasData) View.GONE else View.VISIBLE
        text_heading.visibility = if (hasData) View.VISIBLE else View.GONE
    }

    private fun checkCategory() {
        mCategory = (activity as SignUpStudentInfoActivity).selectedCategory
        causesDataList.clear()

        if (mCategory.equals(AppConstant.PROFESSIONAL)) {

            text_heading.text = resources.getString(R.string.member_of_these_causes)
            text_no_clubs_added.text = resources.getString(R.string.string_no_cause_or_group_added)

            isProfessional = true
            var items = signUpStudent?.cause
            if (items != null) {
                for (i in items.indices) {
                    causesDataList.add(items.get(i))
                }
            }
        } else {
            text_heading.text = resources.getString(R.string.member_of_these_clubs)
            text_no_clubs_added.text = resources.getString(R.string.string_no_clubs_or_organizations_added)

            isProfessional = false
            var items = signUpStudent?.club
            if (items != null) {
                for (i in items.indices) {
                    causesDataList.add(CauseData(items.get(i).club_name, items.get(i).club_role))
                }
            }
        }

        if (causesDataList.size > 0) {
            showInList()
        } else {
            constraint_no_selection.visibility = View.VISIBLE
        }
    }

    override fun onContinueButtonClicked() {
        if (!clickContinue) {
            validateData()
        }
    }

    fun validateData() {
        if (causesDataList.size == 0) {
            SnackBarFactory.createSnackBar(context, constraint_layout, getString(R.string.select_club_organization))
            return
        }

        signUpStudent?.cause?.clear()
        signUpStudent?.club?.clear()
        clickContinue = true

        for (i in causesDataList.indices) {
            var data = causesDataList.get(i)

            if (mCategory.equals(AppConstant.PROFESSIONAL)) {
                signUpStudent?.cause?.add(data)
            } else {
                signUpStudent?.club?.add(ClubData(data.cause_name, data.cause_role))

            }
        }

        if (mCategory.equals(AppConstant.PROFESSIONAL)) {
            var causeList =  signUpStudent?.cause
            var causeRequest = CauseRequest(AppPreference.getInstance(activity!!).getString(PREF_USER_ID), causeList!!)
            addCause(causeRequest)

        } else {
            var clubList =  signUpStudent?.club
            var clubRequest = ClubRequest(AppPreference.getInstance(activity!!).getString(PREF_USER_ID), clubList!!)
            addClub(clubRequest)
        }
    }

    private fun getClubAndRole(query: String, editClub: AutoCompleteTextView) {
        SignUpRepo.getClubAndRoles(query, object : ResponseListener<List<Club>> {
            override fun onSuccess(response: List<Club>) {
                clubList = response

                if (clubList != null && isAdded) {
                    createClubDataId(clubList, editClub)
                }
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
            }
        })
    }

    private fun getRole(editRole: AutoCompleteTextView) {
        var type: String
        if (category.equals(STUDENT)) {
            type = "CLUB"
        } else {
            type = "CAUSE"
        }

        SignUpRepo.getRoles(type, object : ResponseListener<RoleResponse> {
            override fun onSuccess(response: RoleResponse) {
                createRoleString(response.data, editRole)
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
            }
        })
    }

    private fun createRoleString(data: List<RoleData>, editRole: AutoCompleteTextView) {
        roleList.clear()
        data.forEach {
            roleList.add(it.name)
        }
        setRoleData(roleList, editRole)
    }

    private fun getCauseAndRole(query: String, editClub: AutoCompleteTextView) {
        SignUpRepo.getCauseAndRoles(query, object : ResponseListener<List<Club>> {
            override fun onSuccess(response: List<Club>) {
                if(isAdded)
                createCauseDataId(response, editClub)
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
            }
        })
    }

    private fun createClubDataId(clubList: List<Club>, editClub: AutoCompleteTextView) {
        clubListName.clear()
        clubList.forEach {
            clubListName.add(it.name)
        }
        setClubData(clubListName, editClub)
    }

    private fun createCauseDataId(clubList: List<Club>, editClub: AutoCompleteTextView) {
        clubListName.clear()
        clubList.forEach {
            clubListName.add(it.name)
        }
        setClubData(clubListName, editClub)
    }

    private fun setRoleData(role: List<String>, editRole: AutoCompleteTextView) {
        val arrayAdapter =
                ArrayAdapter(context!!, R.layout.item_member_of_club, R.id.text_item, role)

        editRole.setAdapter(arrayAdapter)
        editRole.threshold = 1
    }

    private fun setClubData(club: List<String>, editClub: AutoCompleteTextView) {
        val arrayAdapter = ArrayAdapter(
            Objects.requireNonNull<Context>(context),
            R.layout.item_member_of_club,
            R.id.text_item,
            club
        )
        editClub.setAdapter(arrayAdapter)
        editClub.threshold = 1
    }

    private fun addClub(clubRequest: ClubRequest) {
        progress_bar.visibility = View.VISIBLE
        SignUpRepo.addUserClub(activity!!, clubRequest, object : ResponseListener<List<DataX>> {
            override fun onSuccess(response: List<DataX>) {

                proccedOnSuccess()
                clickContinue = false
                progress_bar.visibility = View.GONE
            }

            override fun onError(error: Any) {
                clickContinue = false
                Utils.showError(activity, constraint_layout, error)
                progress_bar.visibility = View.GONE
            }
        })
    }

    private fun addCause(causeRequest: CauseRequest) {
        progress_bar.visibility = View.VISIBLE
        SignUpRepo.addUserCause(activity!!, causeRequest, object : ResponseListener<List<DataX>> {
            override fun onSuccess(response: List<DataX>) {

                proccedOnSuccess()
                clickContinue = false
                progress_bar.visibility = View.GONE
            }

            override fun onError(error: Any) {
                clickContinue = false
                Utils.showError(activity, constraint_layout, error)
                progress_bar.visibility = View.GONE
            }
        })
    }

    private fun proccedOnSuccess() {
        if (mCategory.equals(AppConstant.PROFESSIONAL)) {
            changeButtonStatus(2, true)
        } else {
            changeButtonStatus(3, true)
        }
        goToNextPage()
    }

    override fun onItemRemoved() {
        checkVisibility()
    }
}

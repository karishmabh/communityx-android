package com.communityx.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.adapters.ClubsAdapter
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

    public var causesDataList : ArrayList<CauseData> = ArrayList()
    public var clubsAdapter : ClubsAdapter? = null

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
        }

        dialog.show()
    }

    fun showInList() {
        recycler_club_members?.layoutManager = LinearLayoutManager(activity)
        clubsAdapter = ClubsAdapter(causesDataList, activity!!, this)
        recycler_club_members?.adapter = clubsAdapter

        if (causesDataList.size > 0) {
            enableButton(true)
        }
    }

    private fun checkCategory() {
        mCategory = (activity as SignUpStudentInfoActivity).selectedCategory

        causesDataList.clear()
        if (mCategory.equals(AppConstant.PROFESSIONAL)) {

            var items = signUpStudent?.cause
            if (items != null) {
                for (i in items.indices) {
                    causesDataList.add(items.get(i))
                }
            }
        } else {
            var items = signUpStudent?.club
            if (items != null) {
                for (i in items.indices) {
                    causesDataList.add(CauseData(items.get(i).club_name, items.get(i).club_role))
                }
            }
        }

        if (causesDataList.size > 0) {
            showInList()
        }
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

    override fun setFieldsData(): Boolean {
        if (causesDataList.size == 0) {
            SnackBarFactory.createSnackBar(context, constraint_layout, getString(R.string.select_club_organization))
            return false
        }

        signUpStudent?.cause?.clear()
        signUpStudent?.club?.clear()

        for (i in causesDataList.indices) {
            var data = causesDataList.get(i)

            if (mCategory.equals(AppConstant.PROFESSIONAL)) {
                signUpStudent?.cause?.add(data)
            } else {
                signUpStudent?.club?.add(ClubData(data.cause_name, data.cause_role))
            }
        }

        return true
    }

    private fun getClubAndRole(query: String, editClub: AutoCompleteTextView) {
        SignUpRepo.getClubAndRoles(query, object : ResponseListener<ClubAndRoleData> {
            override fun onSuccess(response: ClubAndRoleData) {
                clubList = response.clubs
                createClubDataId(clubList, editClub)
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
            }

        })
    }

    private fun getRole(editRole: AutoCompleteTextView) {
        SignUpRepo.getRoles(object : ResponseListener<RoleResponse> {
            override fun onSuccess(response: RoleResponse) {
                createRoleString(response.data[0], editRole)
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
        SignUpRepo.getCauseAndRoles(query, object : ResponseListener<ClubAndRoleData> {
            override fun onSuccess(response: ClubAndRoleData) {
                createCauseDataId(response.causes, editClub)
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

    private fun createCauseDataId(clubList: List<Causes>, editClub: AutoCompleteTextView) {
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
}

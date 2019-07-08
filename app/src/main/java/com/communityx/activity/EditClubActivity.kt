package com.communityx.activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.EditClubsAdapter
import com.communityx.models.profile.Education
import com.communityx.models.signup.*
import com.communityx.models.signup.cause.CauseRequest
import com.communityx.models.signup.club.ClubRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.*
import com.communityx.utils.AppConstant.PREF_USER_ID
import kotlinx.android.synthetic.main.activity_edit_club.*
import kotlinx.android.synthetic.main.activity_edit_club.constraint_layout
import kotlinx.android.synthetic.main.fragment_sign_up_member_of_club.*
import java.util.*
import kotlin.collections.ArrayList

class EditClubActivity : BaseActivity() {

    var causesDataList : ArrayList<CauseData> = ArrayList()
    var clubsAdapter : EditClubsAdapter? = null
    private var clubList: List<Club> = ArrayList()
    private var clubListName: MutableList<String> = mutableListOf()
    private var receivedCausesDataList : ArrayList<Education> = ArrayList()
    private var roleList: MutableList<String> = mutableListOf()
    protected var category: String? = null
    protected var signUpStudent : SignUpRequest? = null
    var club: MutableList<ClubData>? = mutableListOf()
    var cause: MutableList<CauseData>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_club)
        ButterKnife.bind(this)

        setToolBar(this, getString(R.string.string_edit_club), true, true)
        getIntentData()
    }

    @OnClick(R.id.imageView)
    fun closeTapped() {
        finish()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
    }

    @OnClick(R.id.button_save)
    fun saveTapped() {

        if (causesDataList.size == 0) {
            SnackBarFactory.createSnackBar(this, constraint_layout, getString(R.string.select_club_organization))
            return
        }

        cause?.clear()
        club?.clear()

        for (i in causesDataList) {
            var data = i

            if (AppPreference.getInstance(this).getString(AppConstant.PREF_CATEGORY) == AppConstant.PROFESSIONAL) {
                cause?.add(data)

                var causeList =  cause
                var causeRequest = CauseRequest(AppPreference.getInstance(this).getString(PREF_USER_ID), causeList!!)
                addCause(causeRequest)

            } else {
                club?.add(ClubData(data.cause_name, data.cause_role))

                var clubList =  club
                var clubRequest = ClubRequest(AppPreference.getInstance(this).getString(PREF_USER_ID), clubList!!)
                addClub(clubRequest)
            }
        }
    }

    private fun addClub(clubRequest: ClubRequest) {
        val dialog = CustomProgressBar.getInstance(this).showProgressDialog("Updating Profile...")
        dialog.show()
        SignUpRepo.addUserClub(this, clubRequest, object : ResponseListener<List<DataX>> {
            override fun onSuccess(response: List<DataX>) {
               dialog.dismiss()
                finish()
            }

            override fun onError(error: Any) {
                Utils.showError(this@EditClubActivity, constraint_layout, error)
                dialog.dismiss()
            }
        })
    }

    private fun addCause(causeRequest: CauseRequest) {
        val dialog = CustomProgressBar.getInstance(this).showProgressDialog("Updating Profile...")
        dialog.show()

        SignUpRepo.addUserCause(this, causeRequest, object : ResponseListener<List<DataX>> {
            override fun onSuccess(response: List<DataX>) {
                dialog.dismiss()
                finish()
            }

            override fun onError(error: Any) {
                Utils.showError(this@EditClubActivity, constraint_layout, error)
                dialog.dismiss()
            }
        })
    }

    @OnClick(R.id.fab_add)
    fun addTapped() {
        val dialog = Dialog(this)
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
        var textError : TextView = dialog.findViewById(R.id.text_error)

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

                when (AppPreference.getInstance().getString(AppConstant.PREF_CATEGORY)) {
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

           var status = false

            if (causesDataList.size > 0) {

                for (item in causesDataList) {
                    if (item.cause_name.equals(editClub.text.toString(),true)) {
                        status= true
                    }
                }

                if (!status) {
                    causesDataList.add(CauseData(editClub.text.toString(), editRole.text.toString()))
                    dialog.dismiss()
                } else {
                    textError.visibility = View.VISIBLE
                }

                if (clubsAdapter == null) {
                    showInList()
                } else {
                    clubsAdapter?.notifyDataSetChanged()
                    relative_no_data.visibility = View.GONE
                }
            } else{
                causesDataList.add(CauseData(editClub.text.toString(), editRole.text.toString()))
                dialog.dismiss()

                if (clubsAdapter == null) {
                    showInList()
                } else {
                    clubsAdapter?.notifyDataSetChanged()
                    relative_no_data.visibility = View.GONE
                }
            }
        }

        dialog.show()
    }

    private fun getIntentData() {
        receivedCausesDataList = intent.getSerializableExtra("data") as ArrayList<Education>
        prepareCausesData(receivedCausesDataList)
    }

    private fun prepareCausesData (list : ArrayList<Education>) {
        for (education : Education in list) {
            if (education.datatype == "club") {
                val causeData = CauseData(education.name, education.role)
                causesDataList.add(causeData)
            }
        }

        if (causesDataList.size == 0) {
            relative_no_data.visibility = View.VISIBLE
        } else {
            showInList()
        }
    }

    private fun showInList() {
        recycler_clubs_and_causes?.layoutManager = LinearLayoutManager(this)
        clubsAdapter = EditClubsAdapter(causesDataList, this)
        recycler_clubs_and_causes?.adapter = clubsAdapter
        relative_no_data.visibility = View.GONE
    }

    private fun getClubAndRole(query: String, editClub: AutoCompleteTextView) {
        SignUpRepo.getClubAndRoles(query, object : ResponseListener<List<Club>> {
            override fun onSuccess(response: List<Club>) {
                clubList = response

                if (clubList != null) {
                    createClubDataId(clubList, editClub)
                }
            }

            override fun onError(error: Any) {
                Utils.showError(this@EditClubActivity, constraint_layout, error)
            }

        })
    }

    private fun getCauseAndRole(query: String, editClub: AutoCompleteTextView) {
        SignUpRepo.getCauseAndRoles(query, object : ResponseListener<List<Club>> {
            override fun onSuccess(response: List<Club>) {
                createCauseDataId(response, editClub)
            }

            override fun onError(error: Any) {
                Utils.showError(this@EditClubActivity, constraint_layout, error)
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

    private fun setClubData(club: List<String>, editClub: AutoCompleteTextView) {
        val arrayAdapter = ArrayAdapter(
                Objects.requireNonNull<Context>(this),
                R.layout.item_member_of_club,
                R.id.text_item,
                club
        )
        editClub.setAdapter(arrayAdapter)
        editClub.threshold = 1
    }

    private fun getRole(editRole: AutoCompleteTextView) {
        var type: String
        if (category.equals(AppConstant.STUDENT)) {
            type = "CLUB"
        } else {
            type = "CAUSE"
        }

        SignUpRepo.getRoles(type, object : ResponseListener<RoleResponse> {
            override fun onSuccess(response: RoleResponse) {
                createRoleString(response.data, editRole)
            }

            override fun onError(error: Any) {
                Utils.showError(this@EditClubActivity, constraint_layout, error)
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

    private fun setRoleData(role: List<String>, editRole: AutoCompleteTextView) {
        val arrayAdapter =
                ArrayAdapter(this, R.layout.item_member_of_club, R.id.text_item, role)

        editRole.setAdapter(arrayAdapter)
        editRole.threshold = 1
    }
}

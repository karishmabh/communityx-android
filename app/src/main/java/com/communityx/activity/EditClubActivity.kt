package com.communityx.activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
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
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant
import com.communityx.utils.AppPreference
import com.communityx.utils.Utils
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

            if (causesDataList.size > 0) {

                //val iterator = causesDataList.iterator()

                for (item in causesDataList) {
                    if (item.cause_name.equals(editClub.text.toString())) {
                        textError.text = getString(R.string.string_sclubname_exists)
                        textError.visibility = View.VISIBLE
                        break
                    } else {
                        textError.visibility = View.GONE
                        causesDataList.add(CauseData(editClub.text.toString(), editRole.text.toString()))
                        break
                    }
                }

                dialog.dismiss()
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
        SignUpRepo.getClubAndRoles(query, object : ResponseListener<ClubAndRoleData> {
            override fun onSuccess(response: ClubAndRoleData) {
                clubList = response.clubs
                createClubDataId(clubList, editClub)
            }

            override fun onError(error: Any) {
                Utils.showError(this@EditClubActivity, constraint_layout, error)
            }

        })
    }

    private fun getCauseAndRole(query: String, editClub: AutoCompleteTextView) {
        SignUpRepo.getCauseAndRoles(query, object : ResponseListener<ClubAndRoleData> {
            override fun onSuccess(response: ClubAndRoleData) {
                createCauseDataId(response.causes, editClub)
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

    private fun createCauseDataId(clubList: List<Causes>, editClub: AutoCompleteTextView) {
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
        SignUpRepo.getRoles(object : ResponseListener<RoleResponse> {
            override fun onSuccess(response: RoleResponse) {
                createRoleString(response.data[0], editRole)
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

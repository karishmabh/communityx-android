package com.communityx.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.SignUpRequest
import com.communityx.models.signup.StandardResponse
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.AppConstant.HIGH_SCHOOL
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_sign_up_school_college.*
import java.util.*

class SignUpSchoolCollegeFragment : BaseSignUpFragment(), View.OnClickListener {

    private var schoolArrayAdapter: ArrayAdapter<String>? = null
    private var collegeArrayAdapter: ArrayAdapter<String>? = null
    private var standard: String? = null
    private var isFirstLoaded: Boolean = false
    private var listSchool: MutableList<String> = mutableListOf()
    private var listCollege: MutableList<String> = mutableListOf()

    private val TRIGGER_AUTO_COMPLETE = 100
    private val TRIGGER_AUTO_SCHOOL = 120
    private val AUTO_COMPLETE_DELAY: Long = 300

    private lateinit var handler: Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_sign_up_school_college, null)
        ButterKnife.bind(this,view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isFirstLoaded = true
        initField()

        edit_school_name.threshold = 0
        edit_college_name.threshold = 0
        edit_school_name.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                enableButton(true)
                handler.removeMessages(TRIGGER_AUTO_SCHOOL)
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_SCHOOL, AUTO_COMPLETE_DELAY)
            }
        })

        edit_college_name.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                enableButton(true)
                handler.removeMessages(TRIGGER_AUTO_COMPLETE)
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY)
            }
        })

         handler = Handler(object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                if (msg.what === TRIGGER_AUTO_COMPLETE) {
                    var name = edit_college_name.text.toString()
                    if(name.length < 2) isFirstLoaded = false
                    if (!isFirstLoaded) {
                        getStandardList(Qualification.COLLEGE_UNIVERSITY, name.trim())
                    } else {
                        isFirstLoaded = false
                        return false
                    }
                } else if (msg.what === TRIGGER_AUTO_SCHOOL) {
                    var name = edit_school_name.text.toString()
                    if(name.length < 2) isFirstLoaded = false
                    if (!isFirstLoaded) {
                        getStandardList(Qualification.HIGH_SCHOOL, name.trim())
                    } else {
                        isFirstLoaded = false
                        return false
                    }
                }
                return false
            }
        })

        view_school.setOnClickListener(this)
        view_college.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        tappedQualificationInfo(v!!)
    }

    override fun onContinueButtonClicked() {
        if (setFieldsData())  {
            changeButtonStatus(1, true)
            goToNextPage()
        }
    }

    override fun setFieldsData(): Boolean {
        signUpStudent?.standard = standard
        signUpStudent?.standard_name =
            if (standard == HIGH_SCHOOL) edit_school_name.text.toString() else edit_college_name.text.toString()

        return validateEmpty(signUpStudent)
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        var isValidated = true
        var msg = ""
        when {
            TextUtils.isEmpty(requestData?.standard) -> {
                isValidated = false
                msg = resources.getString(R.string.select_at_least_one_category)
            }
            requestData?.standard == Qualification.HIGH_SCHOOL.name && TextUtils.isEmpty(requestData.standard_name) -> {
                isValidated = false
                msg = getString(R.string.please_fill_school)
            }
            requestData?.standard == Qualification.COLLEGE_UNIVERSITY.name && TextUtils.isEmpty(requestData.standard_name) -> {
                isValidated = false
                msg = getString(R.string.please_fill_college)
            }
        }
        if (!isValidated && showSnackbar) SnackBarFactory.createSnackBar(context, constraint_layout, msg)
        return isValidated
    }

    private fun initField() {
       //  edit_school_name.threshold = 1
       // edit_college_name.threshold = 1

        if (validateEmpty(signUpStudent, false)) {
            if (standard == Qualification.HIGH_SCHOOL.name) {
                edit_school_name.setText(signUpStudent?.standard_name)
                tappedQualificationInfo(view_school)
            } else if (standard == Qualification.COLLEGE_UNIVERSITY.name) {
                edit_college_name.setText(signUpStudent?.standard_name)
                tappedQualificationInfo(view_college)
            }
            //enableButton(false)
        }
    }

    private fun tappedQualificationInfo(it: View) {
        if (it == view_school)
            selectQualificationInfo(Qualification.HIGH_SCHOOL)
        else if (it == view_college) selectQualificationInfo(Qualification.COLLEGE_UNIVERSITY)
    }

    private fun selectQualificationInfo(qualification: Qualification) {
        (Objects.requireNonNull<FragmentActivity>(activity) as SignUpStudentInfoActivity).enableButton(true)
        standard = qualification.name
        when (qualification) {
            SignUpSchoolCollegeFragment.Qualification.HIGH_SCHOOL -> {
                view_school!!.background = resources.getDrawable(R.drawable.border_orange_bg)
                view_college!!.background = resources.getDrawable(R.drawable.bordered_bg)

                image_school!!.setImageResource(R.drawable.ic_signup_highschool_select)
                image_college!!.setImageResource(R.drawable.ic_signup_college_deselect)

                text_school!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_college!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                image_school_tick!!.visibility = View.VISIBLE
                image_college_tick!!.visibility = View.GONE

                layout_school!!.visibility = View.VISIBLE
                layout_college!!.visibility = View.GONE

                edit_school_name.requestFocus()
            }

            SignUpSchoolCollegeFragment.Qualification.COLLEGE_UNIVERSITY -> {
                view_college!!.background = resources.getDrawable(R.drawable.border_orange_bg)
                view_school!!.background = resources.getDrawable(R.drawable.bordered_bg)

                image_college!!.setImageResource(R.drawable.ic_signup_college_select)
                image_school!!.setImageResource(R.drawable.ic_signup_highschool_deselect)

                text_college!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_school!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                image_college_tick!!.visibility = View.VISIBLE
                image_school_tick!!.visibility = View.GONE

                layout_college!!.visibility = View.VISIBLE
                layout_school!!.visibility = View.GONE

                edit_college_name.requestFocus()
            }
        }
    }

    private enum class Qualification {
        HIGH_SCHOOL,
        COLLEGE_UNIVERSITY
    }

    private fun getStandardList(qualification: Qualification, querString: String) {

        SignUpRepo.getStandardList(qualification.name, querString, object : ResponseListener<StandardResponse> {

            override fun onSuccess(response: StandardResponse) {

                when (qualification) {
                    Qualification.HIGH_SCHOOL -> {
                        listSchool.clear()
                        if (!response.data.isEmpty()) {
                            response.data.forEach {
                                listSchool.add(it.name)
                            }
                        }
                        if (edit_school_name == null) return

                        schoolArrayAdapter = ArrayAdapter(context!!, R.layout.item_member_of_club, R.id.text_item, listSchool)
                        edit_school_name.setAdapter(schoolArrayAdapter)
                        edit_school_name.threshold = 1
                        schoolArrayAdapter?.notifyDataSetChanged()
                    }
                    Qualification.COLLEGE_UNIVERSITY -> {
                        listCollege.clear()
                        response.data.forEach {
                            listCollege.add(it.name)
                        }

                        if (edit_college_name == null) return
                        collegeArrayAdapter = ArrayAdapter(context!!, R.layout.item_member_of_club, R.id.text_item, listCollege)
                        edit_college_name.setAdapter(collegeArrayAdapter)
                        edit_college_name.threshold = 1
                        collegeArrayAdapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onError(error: Any) {
                Utils.showError(activity, constraint_layout, error)
            }

        })
    }

}

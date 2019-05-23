package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import kotlinx.android.synthetic.main.fragment_sign_up_school_college.*
import java.util.*

class SignUpSchoolCollegeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_school_college, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_school.setOnClickListener(this)
        view_college.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        tappedQualificationInfo(v!!)
    }

    private fun tappedQualificationInfo(it: View) {
        if (it == view_school)
            selectQualificationInfo(Qualification.SCHOOL)
        else if (it == view_college) selectQualificationInfo(Qualification.COLLEGE)
    }

    private fun selectQualificationInfo(qualification: Qualification) {
        (Objects.requireNonNull<FragmentActivity>(activity) as SignUpStudentInfoActivity).enableButton(true)

        when (qualification) {
            SignUpSchoolCollegeFragment.Qualification.SCHOOL -> {
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
            }

            SignUpSchoolCollegeFragment.Qualification.COLLEGE -> {
                view_college!!.background = resources.getDrawable(R.drawable.border_orange_bg)
                view_school!!.background = resources.getDrawable(R.drawable.bordered_bg)

                image_college!!.setImageResource(R.drawable.ic_signup_college_select)
                image_school!!.setImageResource(R.drawable.ic_signup_highschool_deselect)

                text_college!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_school!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                text_college!!.visibility = View.VISIBLE
                text_school!!.visibility = View.GONE

                layout_college!!.visibility = View.VISIBLE
                layout_school!!.visibility = View.GONE
            }
        }
    }

    private enum class Qualification {
        SCHOOL,
        COLLEGE
    }
}

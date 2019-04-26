package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.communityx.R
import kotlinx.android.synthetic.main.activity_signup_category.*
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.layout_top_view_logo.*

class SignupCategoryActivity : AppCompatActivity() {

    private var viewStudent : RelativeLayout? = null
    private var viewProfessional : RelativeLayout? = null
    private var viewOrganisation : RelativeLayout? = null

    private var mSelected : Boolean  = false

    private var imageOrganisation : ImageView? = null
    private var imageStudent : ImageView? = null
    private var imageProfessional : ImageView? = null

    private var tickOrganisation : ImageView? = null
    private var tickStudent : ImageView? = null
    private var tickProfessional : ImageView? = null

    private var textOrganisation : TextView? = null
    private var textStudent : TextView? = null
    private var textProfessional : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_category)

        text_subtitle.text = "Build your social impact identity on CommunityX."

        viewStudent = view_student
        viewProfessional = view_professional
        viewOrganisation = view_organisation

        imageOrganisation = image_organisation
        imageProfessional = image_professional
        imageStudent = image_student

        tickOrganisation = image_organisation_tick
        tickProfessional = image_professional_tick
        tickStudent = image_student_tick

        textOrganisation = text_organisation
        textProfessional = text_professional
        textStudent = text_student

        viewStudent?.setOnClickListener(View.OnClickListener { categorySelected("student") })
        viewProfessional?.setOnClickListener(View.OnClickListener { categorySelected("professional") })
        viewOrganisation?.setOnClickListener(View.OnClickListener { categorySelected("organisation")})

        text_login.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
            finish()
        }

        button_continue.setOnClickListener{
            startActivity(Intent(this, SignUpStudentInfoActivity::class.java))
            overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        }
    }

    private fun categorySelected(category: String) {
        button_continue.background = resources.getDrawable(R.drawable.button_active)

        when (category) {
            "student" -> {
                viewStudent?.background = resources.getDrawable(R.drawable.border_orange_bg)
                viewProfessional?.background = resources.getDrawable(R.drawable.bordered_bg)
                viewOrganisation?.background = resources.getDrawable(R.drawable.bordered_bg)

                imageStudent?.setImageResource(R.drawable.ic_signup_student_select)
                imageProfessional?.setImageResource(R.drawable.ic_signup_professional_deselect)
                imageOrganisation?.setImageResource(R.drawable.ic_signup_organization_deselect)

                textStudent?.setTextColor(resources.getColor(R.color.colorBlackTitle))
                textProfessional?.setTextColor(resources.getColor(R.color.colorLightGrey))
                textOrganisation?.setTextColor(resources.getColor(R.color.colorLightGrey))

                tickStudent?.visibility = View.VISIBLE
                tickProfessional?.visibility = View.GONE
                tickOrganisation?.visibility = View.GONE
            }
            "professional" -> {
                viewStudent?.background = resources.getDrawable(R.drawable.bordered_bg)
                viewProfessional?.background = resources.getDrawable(R.drawable.border_orange_bg)
                viewOrganisation?.background = resources.getDrawable(R.drawable.bordered_bg)

                imageStudent?.setImageResource(R.drawable.ic_signup_student_deselect)
                imageProfessional?.setImageResource(R.drawable.ic_signup_professional_select)
                imageOrganisation?.setImageResource(R.drawable.ic_signup_organization_deselect)

                textStudent?.setTextColor(resources.getColor(R.color.colorLightGrey))
                textProfessional?.setTextColor(resources.getColor(R.color.colorBlackTitle))
                textOrganisation?.setTextColor(resources.getColor(R.color.colorLightGrey))

                tickStudent?.visibility = View.GONE
                tickProfessional?.visibility = View.VISIBLE
                tickOrganisation?.visibility = View.GONE
            }
            "organisation" ->  {
                viewStudent?.background = resources.getDrawable(R.drawable.bordered_bg)
                viewProfessional?.background = resources.getDrawable(R.drawable.bordered_bg)
                viewOrganisation?.background = resources.getDrawable(R.drawable.border_orange_bg)

                imageStudent?.setImageResource(R.drawable.ic_signup_student_deselect)
                imageProfessional?.setImageResource(R.drawable.ic_signup_professional_deselect)
                imageOrganisation?.setImageResource(R.drawable.ic_signup_organization_select)

                textStudent?.setTextColor(resources.getColor(R.color.colorLightGrey))
                textProfessional?.setTextColor(resources.getColor(R.color.colorLightGrey))
                textOrganisation?.setTextColor(resources.getColor(R.color.colorBlackTitle))

                tickStudent?.visibility = View.GONE
                tickProfessional?.visibility = View.GONE
                tickOrganisation?.visibility = View.VISIBLE
            }
            else -> println("Number too high")
        }
    }
}

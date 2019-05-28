package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.R.id
import com.communityx.utils.AppConstant

class SignupCategoryActivity : AppCompatActivity(), AppConstant {

    @BindView(id.view_student)
    internal var viewStudent: RelativeLayout? = null
    @BindView(id.view_professional)
    internal var viewProfessional: RelativeLayout? = null
    @BindView(id.view_organisation)
    internal var viewOrganisation: RelativeLayout? = null
    @BindView(id.image_organisation)
    internal var imageOrganisation: ImageView? = null
    @BindView(id.image_student)
    internal var imageStudent: ImageView? = null
    @BindView(id.image_professional)
    internal var imageProfessional: ImageView? = null
    @BindView(id.image_organisation_tick)
    internal var tickOrganisation: ImageView? = null
    @BindView(id.image_student_tick)
    internal var tickStudent: ImageView? = null
    @BindView(R.id.image_professional_tick)
    internal var tickProfessional: ImageView? = null
    @BindView(id.text_organisation)
    internal var textOrganisation: TextView? = null
    @BindView(id.text_student)
    internal var textStudent: TextView? = null
    @BindView(id.text_professional)
    internal var textProfessional: TextView? = null
    @BindView(id.button_continue)
    internal var buttonContinue: Button? = null

    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_signup_category)
        ButterKnife.bind(this)

        val textSubTitle = findViewById<TextView>(id.text_subtitle)
        textSubTitle.text = "Build your social impact identity on CommunityX."
        buttonContinue!!.isClickable = false
        buttonContinue!!.alpha = 0.5f
    }

    @OnClick(id.view_student, id.view_professional, id.view_organisation)
    internal fun selectCategory(it: View) {
        if (it == viewStudent)
            categorySelected(AppConstant.ACTION_SIGN_UP_STUDENT)
        else if (it == viewProfessional)
            categorySelected(AppConstant.ACTION_SIGN_UP_PROFESSIONAL)
        else if (it == viewOrganisation) categorySelected(AppConstant.ACTION_SIGN_UP_ORGANIZATION)
    }


    @OnClick(id.button_continue)
    internal fun letsContinue() {
        val intent = Intent(this, SignUpStudentInfoActivity::class.java)
        intent.action = selectedCategory
        startActivity(intent)
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
    }

    @OnClick(id.text_login)
    internal fun goToLogin() {
        startActivity(Intent(this@SignupCategoryActivity, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        finish()
    }

    private fun categorySelected(category: String) {
        buttonContinue!!.isClickable = true
        buttonContinue!!.alpha = 1.0f
        selectedCategory = category

        when (category) {
            AppConstant.ACTION_SIGN_UP_STUDENT -> {
                viewStudent!!.background = this.resources.getDrawable(R.drawable.border_orange_bg)
                viewProfessional!!.background = this.resources.getDrawable(R.drawable.bordered_bg)
                viewOrganisation!!.background = this.resources.getDrawable(R.drawable.bordered_bg)

                imageStudent!!.setImageResource(R.drawable.ic_signup_student_select)
                imageProfessional!!.setImageResource(R.drawable.ic_signup_professional_deselect)
                imageOrganisation!!.setImageResource(R.drawable.ic_signup_organization_deselect)

                textStudent!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                textProfessional!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                textOrganisation!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                tickStudent!!.visibility = View.VISIBLE
                tickProfessional!!.visibility = View.GONE
                tickOrganisation!!.visibility = View.GONE
            }
            AppConstant.ACTION_SIGN_UP_PROFESSIONAL -> {
                viewStudent!!.background = this.resources.getDrawable(R.drawable.bordered_bg)
                viewProfessional!!.background = this.resources.getDrawable(R.drawable.border_orange_bg)
                viewOrganisation!!.background = this.resources.getDrawable(R.drawable.bordered_bg)

                imageStudent!!.setImageResource(R.drawable.ic_signup_student_deselect)
                imageProfessional!!.setImageResource(R.drawable.ic_signup_professional_select)
                imageOrganisation!!.setImageResource(R.drawable.ic_signup_organization_deselect)

                textStudent!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                textProfessional!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                textOrganisation!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                tickStudent!!.visibility = View.GONE
                tickProfessional!!.visibility = View.VISIBLE
                tickOrganisation!!.visibility = View.GONE
            }
            AppConstant.ACTION_SIGN_UP_ORGANIZATION -> {
                viewStudent!!.background = this.resources.getDrawable(R.drawable.bordered_bg)
                viewProfessional!!.background = this.resources.getDrawable(R.drawable.bordered_bg)
                viewOrganisation!!.background = this.resources.getDrawable(R.drawable.border_orange_bg)

                imageStudent!!.setImageResource(R.drawable.ic_signup_student_deselect)
                imageProfessional!!.setImageResource(R.drawable.ic_signup_professional_deselect)
                imageOrganisation!!.setImageResource(R.drawable.ic_signup_organization_select)

                textStudent!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                textProfessional!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                textOrganisation!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))

                tickStudent!!.visibility = View.GONE
                tickProfessional!!.visibility = View.GONE
                tickOrganisation!!.visibility = View.VISIBLE
                return
            }
        }
    }
}

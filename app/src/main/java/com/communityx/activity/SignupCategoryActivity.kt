package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.R.id
import com.communityx.utils.AppConstant
import kotlinx.android.synthetic.main.activity_signup_category.*

class SignupCategoryActivity : AppCompatActivity(), AppConstant {

    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_signup_category)
        ButterKnife.bind(this)

        val textSubTitle = findViewById<TextView>(id.text_subtitle)
        textSubTitle.text = getString(R.string.string_build_social_impact)
        button_continue.isClickable = false
        button_continue.alpha = 0.5f
    }

    @OnClick(id.view_student, id.view_professional, id.view_organisation)
    internal fun selectCategory(view: View) {
        if (view == view_student)
            categorySelected(AppConstant.STUDENT)
        else if (view == view_professional)
            categorySelected(AppConstant.PROFESSIONAL)
        else if (view == view_organisation)
            categorySelected(AppConstant.ORGANIZATION)
    }


    @OnClick(id.button_continue)
    internal fun continueTapped() {
        val intent = Intent(this, SignUpStudentInfoActivity::class.java)
        intent.action = selectedCategory
        startActivity(intent)
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
    }

    @OnClick(id.text_login)
    internal fun loginTapped() {
        startActivity(Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        finish()
    }

    private fun categorySelected(category: String) {
        button_continue!!.isClickable = true
        button_continue!!.alpha = 1.0f
        selectedCategory = category

        when (category) {
            AppConstant.STUDENT -> {
                view_student!!.background = this.resources.getDrawable(R.drawable.border_orange_bg)
                view_professional!!.background = this.resources.getDrawable(R.drawable.bordered_bg)
                view_organisation!!.background = this.resources.getDrawable(R.drawable.bordered_bg)

                image_student!!.setImageResource(R.drawable.ic_signup_student_select)
                image_professional!!.setImageResource(R.drawable.ic_signup_professional_deselect)
                image_organisation!!.setImageResource(R.drawable.ic_signup_organization_deselect)

                text_student!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_professional!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_organisation!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                image_student_tick!!.visibility = View.VISIBLE
                image_professional_tick!!.visibility = View.GONE
                image_organisation_tick!!.visibility = View.GONE
            }

            AppConstant.PROFESSIONAL -> {
                view_student!!.background = this.resources.getDrawable(R.drawable.bordered_bg)
                view_professional!!.background = this.resources.getDrawable(R.drawable.border_orange_bg)
                view_organisation!!.background = this.resources.getDrawable(R.drawable.bordered_bg)

                image_student!!.setImageResource(R.drawable.ic_signup_student_deselect)
                image_professional!!.setImageResource(R.drawable.ic_signup_professional_select)
                image_organisation!!.setImageResource(R.drawable.ic_signup_organization_deselect)

                text_student!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_professional!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
                text_organisation!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

                image_student_tick!!.visibility = View.GONE
                image_professional_tick!!.visibility = View.VISIBLE
                image_organisation_tick!!.visibility = View.GONE
            }

            AppConstant.ORGANIZATION -> {
                view_student!!.background = this.resources.getDrawable(R.drawable.bordered_bg)
                view_professional!!.background = this.resources.getDrawable(R.drawable.bordered_bg)
                view_organisation!!.background = this.resources.getDrawable(R.drawable.border_orange_bg)

                image_student!!.setImageResource(R.drawable.ic_signup_student_deselect)
                image_professional!!.setImageResource(R.drawable.ic_signup_professional_deselect)
                image_organisation!!.setImageResource(R.drawable.ic_signup_organization_select)

                text_student!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_professional!!.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
                text_organisation!!.setTextColor(this.resources.getColor(R.color.colorBlackTitle))

                image_student_tick!!.visibility = View.GONE
                image_professional_tick!!.visibility = View.GONE
                image_organisation_tick!!.visibility = View.VISIBLE
                return
            }
        }
    }
}

package com.communityx.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.models.profile.Education
import kotlinx.android.synthetic.main.fragment_sign_up_select_role.*

class EditEducationActivity : BaseActivity() {

    private lateinit var education : Education

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_education)
        ButterKnife.bind(this)

        setToolBar(this, getString(R.string.string_edit_education), true, true)
        getIntentData()
    }

    @OnClick(R.id.imageView)
    fun closeTapped() {
        finish()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
    }

    fun getIntentData() {
        education = intent.getSerializableExtra("data") as Education
        setUpData(education!!)
    }

    fun setUpData(education: Education) {

        if (education.role == "SENIOR") {

            view_senior_main.background = resources.getDrawable(R.drawable.border_orange_bg)
            view_freshman_main.background = resources.getDrawable(R.drawable.bordered_bg)
            view_sophomore_main.background = resources.getDrawable(R.drawable.bordered_bg)
            view_junior_main.background = resources.getDrawable(R.drawable.bordered_bg)

            image_senior.setImageResource(R.drawable.ic_signup_senior_select)
            image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
            image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
            image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)

            text_senior.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
            text_freshman.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
            text_sophomore.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
            text_junior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

            image_senior_tick.visibility = View.VISIBLE
            image_freshman_tick.visibility = View.GONE
            image_sophomore_tick.visibility = View.GONE
            image_junior_tick.visibility = View.GONE
        } else if (education.role == "FRESHMAN") {
            view_freshman_main.background = resources.getDrawable(R.drawable.border_orange_bg)
            view_sophomore_main.background = resources.getDrawable(R.drawable.bordered_bg)
            view_junior_main.background = resources.getDrawable(R.drawable.bordered_bg)
            view_senior_main.background = resources.getDrawable(R.drawable.bordered_bg)

            image_freshman.setImageResource(R.drawable.ic_signup_freshman_select)
            image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
            image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)
            image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

            text_freshman.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
            text_sophomore.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
            text_junior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
            text_senior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

            image_freshman_tick.visibility = View.VISIBLE
            image_sophomore_tick.visibility = View.GONE
            image_junior_tick.visibility = View.GONE
            image_senior_tick.visibility = View.GONE

        } else if (education.role == "SOPHOMORE") {

            view_sophomore_main.background = resources.getDrawable(R.drawable.border_orange_bg)
            view_freshman_main.background = resources.getDrawable(R.drawable.bordered_bg)
            view_junior_main.background = resources.getDrawable(R.drawable.bordered_bg)
            view_senior_main.background = resources.getDrawable(R.drawable.bordered_bg)

            image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_select)
            image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
            image_junior.setImageResource(R.drawable.ic_signup_junior_deselect)
            image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

            text_sophomore.setTextColor(this.resources.getColor(R.color.colorBlackTitle))
            text_freshman.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
            text_junior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))
            text_senior.setTextColor(this.resources.getColor(R.color.colorLightestGrey))

            image_sophomore_tick.visibility = View.VISIBLE
            image_freshman_tick.visibility = View.GONE
            image_junior_tick.visibility = View.GONE
            image_senior_tick.visibility = View.GONE
        } else if (education.role == "JUNIOR") {

            view_junior_main.background = resources.getDrawable(R.drawable.border_orange_bg)
            view_freshman_main.background = resources.getDrawable(R.drawable.bordered_bg)
            view_sophomore_main.background = resources.getDrawable(R.drawable.bordered_bg)
            view_senior_main.background = resources.getDrawable(R.drawable.bordered_bg)

            image_junior.setImageResource(R.drawable.ic_signup_junior_select)
            image_freshman.setImageResource(R.drawable.ic_signup_freshman_deselect)
            image_sophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect)
            image_senior.setImageResource(R.drawable.ic_signup_senior_deselect)

            text_junior.setTextColor(resources.getColor(R.color.colorBlackTitle))
            text_freshman.setTextColor(resources.getColor(R.color.colorLightestGrey))
            text_sophomore.setTextColor(resources.getColor(R.color.colorLightestGrey))
            text_senior.setTextColor(resources.getColor(R.color.colorLightestGrey))

            image_junior_tick.visibility = View.VISIBLE
            image_freshman_tick.visibility = View.GONE
            image_sophomore_tick.visibility = View.GONE
            image_senior_tick.visibility = View.GONE
        }
    }
}

package com.communityx.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.models.profile.Education
import kotlinx.android.synthetic.main.activity_add_experience.*
import kotlinx.android.synthetic.main.toolbar.*

class AddExperienceActivity : AppCompatActivity() {

    private lateinit var education : Education

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_experience)
        ButterKnife.bind(this)

        setToolbar()
        getIntentData()
    }

    @OnClick(R.id.imageView)
    fun closeTapped() {
        finish()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
    }

    private fun setToolbar() {
        image_tail_toolbar.visibility = View.GONE
        text_title.text = getString(R.string.string_add_work_exp)
        imageView.setImageDrawable(resources.getDrawable(R.drawable.image_close_white))
    }

    fun getIntentData() {
        education = intent.getSerializableExtra("data") as Education
        setUpData(education)
    }

    private fun setUpData(education: Education) {
        edit_company_name.setText(education.name)
        edit_title.setText(education.role)
    }
}

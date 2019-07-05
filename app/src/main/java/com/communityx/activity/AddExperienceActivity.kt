package com.communityx.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.AutoSuggestAdapter
import com.communityx.models.job_companies.Data
import com.communityx.models.profile.Education
import com.communityx.models.signup.RoleResponse
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.Utils
import com.communityx.utils.Utils.enableButton
import kotlinx.android.synthetic.main.activity_add_experience.*
import kotlinx.android.synthetic.main.fragment_sign_up_professional.*
import kotlinx.android.synthetic.main.toolbar.*

class AddExperienceActivity : AppCompatActivity() {

    private val TRIGGER_AUTO_COMPLETE = 100
    private lateinit var education : Education
    private lateinit var autoSuggestAdapter: AutoSuggestAdapter
    private lateinit var autoSuggestCompanyAdapter: AutoSuggestAdapter
    private val AUTO_COMPLETE_DELAY: Long = 300
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_experience)
        ButterKnife.bind(this)

        setToolbar()
        getIntentData()
        //setAutoCompleteJob()
        //setAutoCompleteCompany()
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
        if (intent.hasExtra("data")) {
            education = intent.getSerializableExtra("data") as Education
            setUpData(education)
        }
    }

    private fun setUpData(education: Education) {
        auto_company_name.setText(education.name)
        auto_title.setText(education.role)
    }



    private fun setSpinnerData(jobs: List<Data>) {
        val jobsList = mutableListOf<String>()
        jobs.forEach {
            jobsList.add(it.name)
        }

        autoSuggestAdapter.setData(jobsList)
        autoSuggestAdapter.notifyDataSetChanged()
    }

    private fun setCompanyData(jobs: List<Data>) {
        val jobsList = mutableListOf<String>()
        jobs.forEach {
            jobsList.add(it.name)
        }

        autoSuggestCompanyAdapter.setData(jobsList)
        autoSuggestCompanyAdapter.notifyDataSetChanged()
    }

}

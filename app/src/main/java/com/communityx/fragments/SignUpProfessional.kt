package com.communityx.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.communityx.R
import com.communityx.adapters.AutoSuggestAdapter
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.job_companies.Data
import com.communityx.models.signup.SignUpRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.SnackBarFactory
import kotlinx.android.synthetic.main.fragment_sign_up_professional.*

class SignUpProfessional : BaseSignUpFragment() {

    private val TRIGGER_AUTO_COMPLETE = 100
    private val AUTO_COMPLETE_DELAY: Long = 300
    private lateinit var handler: Handler
    private lateinit var autoSuggestAdapter: AutoSuggestAdapter
    private lateinit var autoSuggestCompanyAdapter: AutoSuggestAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_professional, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAutoCompleteJob()
        setAutoCompleteCompany()
    }

    override fun setFieldsData(): Boolean {
        signUpStudent?.job_title = auto_complete_job.text.toString().trim()
        signUpStudent?.company_name = auto_complete_company.text.toString().trim()

        return validateEmpty(signUpStudent)
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        var isValidated = true
        var msg = ""
        when {
            TextUtils.isEmpty(requestData?.job_title) -> {
                msg = resources.getString(R.string.job_title_required)
                isValidated = false
            }
            TextUtils.isEmpty(requestData?.company_name) -> {
                msg = resources.getString(R.string.company_field_required)
                isValidated = false
            }
        }
        if (!isValidated && showSnackbar) SnackBarFactory.createSnackBar(context, coordinator_main, msg)
        return isValidated
    }

    override fun onContinueButtonClicked() {
        if (setFieldsData()) goToNextPage()
    }

    private fun getJobTitles(query: String) {
        SignUpRepo.getJobTitle(query, object : ResponseListener<List<List<Data>>> {
            override fun onSuccess(response: List<List<Data>>) {
                setSpinnerData(response.get(0))
            }

            override fun onError(error: Any) {
            }
        })
    }

    private fun getCompanies(query: String) {
        SignUpRepo.getCompanies(query, object : ResponseListener<List<List<Data>>> {
            override fun onSuccess(response: List<List<Data>>) {
                setCompanyData(response.get(0))
            }

            override fun onError(error: Any) {
            }
        })
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

    private fun setAutoCompleteJob() {
        autoSuggestAdapter = AutoSuggestAdapter(activity!!, android.R.layout.simple_dropdown_item_1line)
        auto_complete_job.threshold = 2
        auto_complete_job.setAdapter(autoSuggestAdapter)

        auto_complete_job.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(auto_complete_job.text)) {
                    getJobTitles(auto_complete_job.text.toString())
                }
            }
        })

        handler = Handler(object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                if (msg.what === TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(auto_complete_job.text)) {
                        getJobTitles(auto_complete_job.text.toString())
                    }
                }
                return false
            }
        })
    }

    private fun setAutoCompleteCompany() {
        autoSuggestCompanyAdapter = AutoSuggestAdapter(activity!!, android.R.layout.simple_dropdown_item_1line)
        auto_complete_company.threshold = 2
        auto_complete_company.setAdapter(autoSuggestCompanyAdapter)

        auto_complete_company.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE)
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        handler = Handler(object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                if (msg.what === TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(auto_complete_company.text)) {
                        getCompanies(auto_complete_company.text.toString())
                    }
                }
                return false
            }
        })
    }
}

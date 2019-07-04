package com.communityx.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.communityx.R
import com.communityx.activity.SignUpStudentInfoActivity
import com.communityx.adapters.SelectedInterestAdapter
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.DataX
import com.communityx.models.signup.InterestRequest
import com.communityx.models.signup.MinorsData
import com.communityx.models.signup.SignUpRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.*
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.*

class SignUpSelectInterest : BaseSignUpFragment() {

    private lateinit var mInterestAdapter : SelectedInterestAdapter

    var clickContinue : Boolean = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_select_interest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadInterest()
        if (signUpActivity?.manaualInterest == null) {
            signUpActivity?.manaualInterest = mutableListOf()
        }
    }

    private fun loadInterest() {
        context?.let {
            SignUpRepo.getMajorMinorData(it, object : ResponseListener<List<MinorsData>> {
                override fun onSuccess(response: List<MinorsData>) {
                    if(isAdded)
                    setRecycler(response)
                }

                override fun onError(error: Any) {
                    Utils.showError(activity, scrollView, error)
                }
            })
        }
    }

    private fun setRecycler(response: List<MinorsData>) {
        recycler_interests.layoutManager = LinearLayoutManager(activity)
        mInterestAdapter = SelectedInterestAdapter(response, activity!!, scrollView)
        recycler_interests.adapter = mInterestAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setSuggestedCause()
        edit_cause.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onCauseTyping(s)
            }
        })
    }

    override fun setFieldsData(): Boolean {
        signUpStudent?.suggested_minors = signUpActivity?.manaualInterest
        return validateEmpty(signUpStudent)
    }

    override fun validateEmpty(requestData: SignUpRequest?, showSnackbar: Boolean): Boolean {
        if (mInterestAdapter.getSelectedIds().isNullOrEmpty()) {
            if (showSnackbar) SnackBarFactory.createSnackBar(context, scrollView, resources.getString(R.string.select_atleast_one_interest))
           return false
       }
        signUpStudent?.interests = mInterestAdapter.getSelectedIds()
        return true
    }

    override fun onContinueButtonClicked() {
        if(setFieldsData()) {
            var interestRequest = InterestRequest(
                signUpStudent?.interests!!,
                AppPreference.getInstance(activity!!).getString(AppConstant.PREF_USER_ID)
            )

            var suggestRequest = InterestRequest(
                signUpStudent?.suggested_minors!!,
                AppPreference.getInstance(activity!!).getString(AppConstant.PREF_USER_ID)
            )

            if (!clickContinue && isAdded) {
                addInterests(interestRequest, suggestRequest)
            }

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSuggestedCause() {
        initManualList()
        edit_cause.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= edit_cause.right - edit_cause.totalPaddingRight) {
                    val suggestedCause = edit_cause.text.toString()
                    if (!suggestedCause.isEmpty()) {
                        if(signUpActivity?.manaualInterest == null) {
                            signUpActivity?.manaualInterest = mutableListOf()
                        }

                        val view = LayoutInflater.from(context).inflate(R.layout.item_add_interest, null)
                        val textView = view.findViewById<TextView>(R.id.text_suggest_cause)
                        val imageCross = view.findViewById<ImageView>(R.id.image_cross)
                        textView.text = suggestedCause

                        imageCross.setOnClickListener { v1 ->
                            flex_layout_cause.removeView(view)
                            signUpActivity?.manaualInterest?.remove(textView.text.toString())
                        }

                        val lp = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        lp.setMargins(10, 10, 10, 10)
                        if(validateSelectedItem()) {
                            return@setOnTouchListener false
                        }
                        signUpActivity?.manaualInterest?.add(suggestedCause)
                        flex_layout_cause.addView(view, lp)
                        edit_cause.setText("")
                        scrollView.post {
                            scrollView.scrollTo(0, scrollView.height)
                            Utils.hideSoftKeyboard(activity)
                        }

                    }
                }
            }
            false
        }
    }

    private fun validateSelectedItem(): Boolean {
        var b = signUpActivity?.manaualInterest?.size == 5
        if (b) {
            scrollView.post { Utils.hideSoftKeyboard(activity) }
            SnackBarFactory.createSnackBar(context, scrollView, getString(R.string.limit_suggest_interest))
        }
        return b
    }

    private fun initManualList() {
        if(!signUpActivity?.manaualInterest.isNullOrEmpty()) {
            signUpActivity?.manaualInterest?.forEach {
                if(signUpActivity?.manaualInterest == null) {
                    signUpActivity?.manaualInterest = mutableListOf()
                }
                val view = LayoutInflater.from(context).inflate(R.layout.item_suggest_cause, null)
                val textView = view.findViewById<TextView>(R.id.text_suggest_cause)
                val imageCross = view.findViewById<ImageView>(R.id.image_cross)
                textView.text = it
                imageCross.setOnClickListener { v1 ->
                    flex_layout_cause.removeView(view)
                    signUpStudent?.interests?.remove(textView.text.toString())
                    signUpActivity?.manaualInterest?.remove(textView.text.toString())
                }
                val lp = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                lp.setMargins(10, 10, 10, 10)
                flex_layout_cause.addView(view, lp)
                edit_cause.setText("")
                scrollView.post { scrollView.scrollTo(0, scrollView.height) }
            }
        }
    }

    private fun addInterests(interestRequest: InterestRequest, suggestRequest: InterestRequest) {
       var dialog =  CustomProgressBar.getInstance(activity!!).showProgressDialog("Logging in..")
        SignUpRepo.addInterests(activity!!, interestRequest, object : ResponseListener<List<DataX>> {
            override fun onSuccess(response: List<DataX>) {
                dialog.dismiss()
                clickContinue = false

                if (!suggestRequest.interests.isNullOrEmpty()) {
                    suggestInterests(suggestRequest)
                } else {
                    proceedOnSuccess()
                }
            }

            override fun onError(error: Any) {
                dialog.dismiss()
                clickContinue = false
                Utils.showError(activity, constraint_top, error)
            }
        })
    }

    private fun suggestInterests(interestRequest: InterestRequest) {
        var dialog =  CustomProgressBar.getInstance(activity!!).showProgressDialog("Logging in..")
        SignUpRepo.suggestInterests(activity!!, interestRequest, object : ResponseListener<List<DataX>> {
            override fun onSuccess(response: List<DataX>) {
                dialog.dismiss()
                clickContinue = false

                proceedOnSuccess()
            }

            override fun onError(error: Any) {
                dialog.dismiss()
                clickContinue = false
                Utils.showError(activity, constraint_top, error)
            }
        })
    }

    fun proceedOnSuccess() {
        changeButtonStatus(3, true)

        (activity as SignUpStudentInfoActivity).performLogin(signUpStudent?.phone!!, signUpStudent?.password!!)
    }

    internal fun onCauseTyping(s: CharSequence?) {
        edit_cause.setCompoundDrawablesWithIntrinsicBounds(
            0, 0,
            if (s?.length != 0) R.drawable.ic_signup_add_interest else R.drawable.ic_signup_add_interest_deselect, 0
        )
    }
}
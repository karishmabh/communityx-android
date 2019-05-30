package com.communityx.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.communityx.R
import com.communityx.base.BaseSignUpFragment
import com.communityx.models.signup.Minor
import com.communityx.models.signup.MinorsData
import com.communityx.models.signup.StudentSignUpRequest
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.SnackBarFactory
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.*

class SignUpSelectInterest : BaseSignUpFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up_select_interest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadInterest()
        if (signUpActivity?.manaualInterest == null) {
            signUpActivity?.manaualInterest = mutableListOf()
        }
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
        return validateEmpty(signUpStudent)
    }

    //todo : hard coded string
    override fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean): Boolean {
        if (signUpStudent?.interests.isNullOrEmpty() && signUpActivity?.manaualInterest.isNullOrEmpty()) {
           if(showSnackbar) SnackBarFactory.createSnackBar(context,scrollView,"Please select at lease 1 interest")
           return false
       }
        return true
    }

    override fun onContinueButtonClicked() {
        if(setFieldsData()) goToNextPage()
    }

    private fun setCivilRights(civilRights: List<Minor>) {
        for (civilRight in civilRights) {
            val checkBox = LayoutInflater.from(context).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = civilRight.name
            checkBox.performClick()
            if(validateEmpty(signUpStudent, false)) {
                checkBox.isChecked = signUpStudent?.interests!!.contains(civilRight.id)
                checkBox.setBackgroundResource(if(checkBox.isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive)
            }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked && !validateSelectedItem()) {
                    return@setOnCheckedChangeListener
                }
                checkBox.setBackgroundResource(if (isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive)
                if (isChecked) {
                    signUpStudent?.interests?.add(civilRight.id)
                }else {
                    signUpStudent?.interests?.remove(civilRight.id)
                }
            }

            val lp =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            flex_layout_civil_right.addView(checkBox, lp)
        }
    }

    private fun setHumanRights(humanRights: List<Minor>) {
        for (humanRight in humanRights) {
            val checkBox = LayoutInflater.from(context).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = humanRight.name
            checkBox.performClick()
            if(validateEmpty(signUpStudent, false)) {
                checkBox.isChecked = signUpStudent?.interests!!.contains(humanRight.id)
                checkBox.setBackgroundResource(if(checkBox.isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive)
            }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked && !validateSelectedItem()) {
                    return@setOnCheckedChangeListener
                }
                checkBox.setBackgroundResource(if (isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive)
                if (isChecked) {
                    signUpStudent?.interests?.add(humanRight.id)
                }else {
                    signUpStudent?.interests?.remove(humanRight.id)
                }
            }

            val lp =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            flex_layout_human_right.addView(checkBox, lp)
        }
    }

    private fun setHealth(healths: List<Minor>) {

        for (health in healths) {
            val checkBox = LayoutInflater.from(context).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = health.name
            checkBox.performClick()
            if(validateEmpty(signUpStudent, false)) {
                checkBox.isChecked = signUpStudent?.interests!!.contains(health.id)
                checkBox.setBackgroundResource(if(checkBox.isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive)
            }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked && !validateSelectedItem()) {
                    return@setOnCheckedChangeListener
                }
                checkBox.setBackgroundResource(if (isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive)
                if (isChecked) {
                    signUpStudent?.interests?.add(health.id)
                }else {
                    signUpStudent?.interests?.remove(health.id)
                }
            }

            val lp =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            flex_layout_health.addView(checkBox, lp)
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
                        val view = LayoutInflater.from(context).inflate(R.layout.item_suggest_cause, null)
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
                        if(!validateSelectedItem()) {
                            return@setOnTouchListener false
                        }
                        signUpActivity?.manaualInterest?.add(suggestedCause)
                        flex_layout_cause.addView(view, lp)
                        edit_cause.setText("")
                        scrollView.post { scrollView.scrollTo(0, scrollView.height) }
                        Utils.hideSoftKeyboard(activity)
                    }
                }
            }
            false
        }
    }

    private fun validateSelectedItem() : Boolean{
        var b = signUpStudent?.interests?.size!! + signUpActivity?.manaualInterest?.size!! < 5
        if (!b) SnackBarFactory.createSnackBar(context, scrollView, getString(R.string.limit_interest))
        return b
    }

    private fun loadInterest() {
        context?.let {
            SignUpRepo.getMajorMinorData(it, object : ResponseListener<List<MinorsData>> {
                override fun onSuccess(response: List<MinorsData>) {
                    setCivilRights(response[0].minors)
                    setHumanRights(response[1].minors)
                    setHealth(response[2].minors)
                }

                override fun onError(error: Any) {
                    Utils.showError(activity, scrollView, error)
                }
            })
        }
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

    internal fun onCauseTyping(s: CharSequence?) {
        edit_cause.setCompoundDrawablesWithIntrinsicBounds(
            0, 0,
            if (s?.length != 0) R.drawable.ic_signup_add_interest else R.drawable.ic_signup_add_interest_deselect, 0
        )
    }
}
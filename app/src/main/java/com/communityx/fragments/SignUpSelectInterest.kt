package com.communityx.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.communityx.models.signup.StudentSignUpRequest
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.*

class SignUpSelectInterest : BaseSignUpFragment() {

    private lateinit var listInterest: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up_select_interest, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setCivilRights()
        setHumanRights()
        setHealth()
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun validateEmpty(requestData: StudentSignUpRequest?, showSnackbar: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onContinueButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setCivilRights() {
        val civilRights = arrayOf(
            "LGBTQ +",
            "Women's Rights",
            "Healthcare Reform",
            "Police Misconduct",
            "Gun Control",
            "Immigration",
            "Healthcare Reform",
            "Sports Activsm"
        )

        for (civilRight in civilRights) {
            val checkBox = LayoutInflater.from(context).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = civilRight
            checkBox.performClick()
            checkBox.setOnCheckedChangeListener { _, isChecked -> checkBox.setBackgroundResource(if (isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive) }

            val lp =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            flex_layout_civil_right.addView(checkBox, lp)
        }
    }

    private fun setHumanRights() {
        val civilRights = arrayOf(
            "Feminine Hygiene",
            "Fistula Awareness",
            "Hunger",
            "Homelessness",
            "Prison Reform",
            "Anti-Recidivism",
            "Human Trafficking",
            "Poverty",
            "Economic Development"
        )

        for (civilRight in civilRights) {
            val checkBox = LayoutInflater.from(context).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = civilRight
            checkBox.performClick()
            checkBox.setOnCheckedChangeListener { _, isChecked -> checkBox.setBackgroundResource(if (isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive) }

            val lp =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            flex_layout_human_right.addView(checkBox, lp)
        }
    }

    private fun setHealth() {
        val civilRights = arrayOf(
            "Breast Cancer Awareness",
            "Eating & Exercise",
            "Vegan",
            "Mental Health",
            "Anxiety & Depression",
            "Disabilities & Special Needs",
            "Bullying",
            "Drug & Alcohol Abuse",
            "childhood cancer"
        )

        for (civilRight in civilRights) {
            val checkBox = LayoutInflater.from(context).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = civilRight
            checkBox.performClick()
            checkBox.setOnCheckedChangeListener { _, isChecked -> checkBox.setBackgroundResource(if (isChecked) R.drawable.bg_interest_active else R.drawable.bg_interest_inactive) }

            val lp =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            flex_layout_health.addView(checkBox, lp)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSuggestedCause(): Boolean {
        edit_cause.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= edit_cause.right - edit_cause.totalPaddingRight) {
                    val suggestedCause = edit_cause.text.toString()
                    if (!suggestedCause.isEmpty()) {
                        val view = LayoutInflater.from(context).inflate(R.layout.item_suggest_cause, null)
                        val textView = view.findViewById<TextView>(R.id.text_suggest_cause)
                        val imageCross = view.findViewById<ImageView>(R.id.image_cross)
                        textView.text = suggestedCause
                        imageCross.setOnClickListener { v1 -> flex_layout_cause.removeView(view) }
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
            true
        }
        return false
    }

    private fun validateSelectedItem() : Boolean{
        return listInterest.size <= 5
    }

    internal fun onCauseTyping(s: CharSequence?) {
        edit_cause.setCompoundDrawablesWithIntrinsicBounds(
            0, 0,
            if (s?.length != 0) R.drawable.ic_signup_add_interest else R.drawable.ic_signup_add_interest_deselect, 0
        )
    }
}
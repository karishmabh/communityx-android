package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.SeekBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import butterknife.OnTouch
import com.communityx.R

class CrowdfundingDetailActivity : AppCompatActivity() {

    @BindView(R.id.edit_amount)
    internal var editAmount: TextInputEditText? = null
    @BindView(R.id.radioGroup)
    internal var radioGroupDonation: RadioGroup? = null
    @BindView(R.id.textinput_amount)
    internal var inputLayoutAmount: TextInputLayout? = null
    @BindView(R.id.text_other_amount)
    internal var radioOtherAmount: RadioButton? = null
    @BindView(R.id.text_dollor_one)
    internal var radioDollorOne: RadioButton? = null
    @BindView(R.id.text_dollor_two)
    internal var radioDollorTwo: RadioButton? = null
    @BindView(R.id.button_pay)
    internal var buttonPay: Button? = null
    @BindView(R.id.scrollView)
    internal var scrollView: ScrollView? = null
    @BindView(R.id.seekBar)
    internal var seekDonationProgess: SeekBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crowdfunding_detail)
        ButterKnife.bind(this)
        radioListener()
    }

    private fun radioListener() {
        radioDollorOne!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) buttonPay!!.setBackgroundResource(R.drawable.button_active)
            radioDollorOne!!.setBackgroundResource(if (isChecked) R.drawable.bg_stroke_active else R.drawable.bg_stroke_grey)
        }

        radioDollorTwo!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) buttonPay!!.setBackgroundResource(R.drawable.button_active)
            radioDollorTwo!!.setBackgroundResource(if (isChecked) R.drawable.bg_stroke_active else R.drawable.bg_stroke_grey)
        }

        radioOtherAmount!!.setOnCheckedChangeListener { buttonView, isChecked ->
            inputLayoutAmount!!.visibility = if (isChecked) View.VISIBLE else View.GONE
            if (isChecked) buttonPay!!.setBackgroundResource(R.drawable.button_active)
            radioOtherAmount!!.setBackgroundResource(if (isChecked) R.drawable.bg_stroke_active else R.drawable.bg_stroke_grey)
            scrollView!!.post { scrollView!!.scrollTo(0, scrollView!!.height) }
        }
    }

    @OnClick(R.id.text_comment, R.id.image_comment)
    internal fun tappedPraised() {
        startActivity(Intent(this, PraiseActivity::class.java))
    }

    @OnClick(R.id.button_pay)
    internal fun tappedPayNow() {
        startActivity(Intent(this, PaymentActivity::class.java))
    }

    @OnTextChanged(R.id.edit_amount)
    internal fun onAmountTyping(s: CharSequence) {
        if (s.length < 1) {
            editAmount!!.setText("$")
            editAmount!!.setSelection(1)
        }
    }

    @OnClick(R.id.view_donated_by)
    internal fun tappedViewDonatedBy() {
        startActivity(Intent(this@CrowdfundingDetailActivity, DonatedByActivity::class.java))
    }

    @OnTouch(R.id.seekBar)
    internal fun seekDonationTouch(): Boolean {
        return true
    }
}

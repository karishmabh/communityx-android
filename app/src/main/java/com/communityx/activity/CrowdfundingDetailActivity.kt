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
import kotlinx.android.synthetic.main.activity_crowdfunding_detail.*

class CrowdfundingDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crowdfunding_detail)
        ButterKnife.bind(this)
        radioListener()
    }

    private fun radioListener() {
        text_dollor_one!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) button_pay!!.setBackgroundResource(R.drawable.button_active)
            text_dollor_one!!.setBackgroundResource(if (isChecked) R.drawable.bg_stroke_active else R.drawable.bg_stroke_grey)
        }

        text_dollor_two!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) button_pay!!.setBackgroundResource(R.drawable.button_active)
            text_dollor_two!!.setBackgroundResource(if (isChecked) R.drawable.bg_stroke_active else R.drawable.bg_stroke_grey)
        }

        text_other_amount!!.setOnCheckedChangeListener { buttonView, isChecked ->
            textinput_amount!!.visibility = if (isChecked) View.VISIBLE else View.GONE
            if (isChecked) button_pay!!.setBackgroundResource(R.drawable.button_active)
            text_other_amount!!.setBackgroundResource(if (isChecked) R.drawable.bg_stroke_active else R.drawable.bg_stroke_grey)
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
            edit_amount!!.setText("$")
            edit_amount!!.setSelection(1)
        }
    }

    @OnClick(R.id.view_donated_by)
    internal fun tappedViewDonatedBy() {
        startActivity(Intent(this, DonatedByActivity::class.java))
    }

    @OnTouch(R.id.seekBar)
    internal fun seekDonationTouch(): Boolean {
        return true
    }
}

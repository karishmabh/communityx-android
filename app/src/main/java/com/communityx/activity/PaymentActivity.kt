package com.communityx.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView

import com.communityx.R
import com.communityx.adapters.PaymentAdapter

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_payment_activity.*
import kotlinx.android.synthetic.main.activity_payment_activity.text_title
import kotlinx.android.synthetic.main.toolbar.*

class PaymentActivity : AppCompatActivity(), PaymentAdapter.OnCardCheckedListener {

    private var paymentAdapter: PaymentAdapter? = null
    private val mCardList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_activity)
        ButterKnife.bind(this)

        text_title!!.text = "Payment Method"
        imageView!!.setImageDrawable(resources.getDrawable(R.drawable.ic_praise_back_arrow))
        setAdapter(mCardList)
        setDebitCard()
        setCreditCard()
    }

    private fun setAdapter(cardList: ArrayList<String>) {
        recycler_cards!!.layoutManager = LinearLayoutManager(this)
        paymentAdapter = PaymentAdapter(cardList, this)
        paymentAdapter!!.setCardCheckedListener(this)
        recycler_cards!!.adapter = paymentAdapter
    }

    private fun setDebitCard() {
        radio_debit_button!!.setOnCheckedChangeListener { buttonView, isChecked ->
            radio_credit_button!!.isChecked = !isChecked
            paymentAdapter!!.notifyDataSetChanged()
            makeActive(isChecked, text_debit_card!!)
            showPannel(isChecked, linear_debit_card!!, text_debit_amount!!)
        }
    }

    private fun setCreditCard() {
        radio_credit_button!!.setOnCheckedChangeListener { buttonView, isChecked ->
            radio_debit_button!!.isChecked = !isChecked
            paymentAdapter!!.notifyDataSetChanged()
            makeActive(isChecked, text_credit_card!!)
            showPannel(isChecked, linear_credit_card!!, text_credit_amount!!)
        }
    }

    private fun makeActive(isChecked: Boolean, textView: TextView) {
        textView.typeface = if (isChecked)
            Typeface.createFromAsset(assets, "fonts/poppins_semibold.ttf")
        else
            Typeface.createFromAsset(assets, "fonts/poppins_regular.ttf")
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                if (isChecked) resources.getDimension(R.dimen._16ssp) else resources.getDimension(R.dimen._14ssp))
    }

    override fun onCardChecked(isChecked: Boolean, radioButton: RadioButton?) {
        if (!isChecked) return
        radio_debit_button!!.setOnCheckedChangeListener(null)
        radio_debit_button!!.isChecked = false
        radio_credit_button!!.setOnCheckedChangeListener(null)
        radio_credit_button!!.isChecked = false

        showPannel(false, linear_credit_card!!, text_credit_amount!!)
        showPannel(false, linear_debit_card!!, text_debit_amount!!)
        makeActive(false, text_debit_card!!)
        makeActive(false, text_credit_card!!)

        setCreditCard()
        setDebitCard()
    }

    private fun showPannel(shouldShow: Boolean, linearLayout: LinearLayout, textView: TextView) {
        linearLayout.visibility = if (shouldShow) View.VISIBLE else View.GONE
        textView.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }
}

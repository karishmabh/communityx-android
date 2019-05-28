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

class PaymentActivity : AppCompatActivity(), PaymentAdapter.OnCardCheckedListener {
    @BindView(R.id.text_title)
    internal var textTitle: TextView? = null
    @BindView(R.id.imageView)
    internal var imageArrow: ImageView? = null
    @BindView(R.id.recycler_cards)
    internal var recyclerCards: RecyclerView? = null
    @BindView(R.id.radio_debit_button)
    internal var radioDebitButton: RadioButton? = null
    @BindView(R.id.radio_credit_button)
    internal var radioCreditButton: RadioButton? = null
    @BindView(R.id.linear_debit_card)
    internal var linearDebitCard: LinearLayout? = null
    @BindView(R.id.linear_credit_card)
    internal var linearCreditCard: LinearLayout? = null
    @BindView(R.id.text_debit_amount)
    internal var textDebitAmount: TextView? = null
    @BindView(R.id.text_credit_amount)
    internal var textCreditAmount: TextView? = null
    @BindView(R.id.text_credit_card)
    internal var textCreditCard: TextView? = null
    @BindView(R.id.text_debit_card)
    internal var textDebitCard: TextView? = null

    private var paymentAdapter: PaymentAdapter? = null
    private val mCardList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_activity)
        ButterKnife.bind(this)

        textTitle!!.text = "Payment Method"
        imageArrow!!.setImageDrawable(resources.getDrawable(R.drawable.ic_praise_back_arrow))
        setAdapter(mCardList)
        setDebitCard()
        setCreditCard()
    }

    private fun setAdapter(cardList: ArrayList<String>) {
        recyclerCards!!.layoutManager = LinearLayoutManager(this)
        paymentAdapter = PaymentAdapter(cardList, this@PaymentActivity)
        paymentAdapter!!.setCardCheckedListener(this)
        recyclerCards!!.adapter = paymentAdapter
    }

    private fun setDebitCard() {
        radioDebitButton!!.setOnCheckedChangeListener { buttonView, isChecked ->
            radioCreditButton!!.isChecked = !isChecked
            paymentAdapter!!.notifyDataSetChanged()
            makeActive(isChecked, textDebitCard!!)
            showPannel(isChecked, linearDebitCard!!, textDebitAmount!!)
        }
    }

    private fun setCreditCard() {
        radioCreditButton!!.setOnCheckedChangeListener { buttonView, isChecked ->
            radioDebitButton!!.isChecked = !isChecked
            paymentAdapter!!.notifyDataSetChanged()
            makeActive(isChecked, textCreditCard!!)
            showPannel(isChecked, linearCreditCard!!, textCreditAmount!!)
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

    override fun onCardChecked(isChecked: Boolean, radioButton: RadioButton) {
        if (!isChecked) return
        radioDebitButton!!.setOnCheckedChangeListener(null)
        radioDebitButton!!.isChecked = false
        radioCreditButton!!.setOnCheckedChangeListener(null)
        radioCreditButton!!.isChecked = false

        showPannel(false, linearCreditCard!!, textCreditAmount!!)
        showPannel(false, linearDebitCard!!, textDebitAmount!!)
        makeActive(false, textDebitCard!!)
        makeActive(false, textCreditCard!!)

        setCreditCard()
        setDebitCard()
    }

    private fun showPannel(shouldShow: Boolean, linearLayout: LinearLayout, textView: TextView) {
        linearLayout.visibility = if (shouldShow) View.VISIBLE else View.GONE
        textView.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }
}

package com.communityx.adapters

import android.app.Activity
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.utils.AppConstant

import java.util.ArrayList

class PaymentAdapter(private val mCardList: ArrayList<String>, private val mActivity: Activity) : RecyclerView.Adapter<PaymentAdapter.EventHolder>(), AppConstant {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActivity)
    private var cardCheckedListener: OnCardCheckedListener? = null
    private var isFirstTime = true

    fun setCardCheckedListener(cardCheckedListener: OnCardCheckedListener) {
        this.cardCheckedListener = cardCheckedListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_saved_card, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, viewType: Int) {
        if (!isFirstTime) {
            eventHolder.radioSavedButton!!.isChecked = false
        }
        eventHolder.setRadioSavedButton()
        isFirstTime = false
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.radio_saved_card_button)
        lateinit var radioSavedButton: RadioButton
        @BindView(R.id.linear_saved_card)
        lateinit var linearSavedCard: LinearLayout
        @BindView(R.id.text_saved_amount)
        lateinit var textSavedCard: TextView
        @BindView(R.id.text_debit_card)
        lateinit var textCardName: TextView

        init {
            ButterKnife.bind(this, itemView)
            radioSavedButton!!.isChecked = true
            makeActive(true, textCardName!!)
        }

        fun setRadioSavedButton() {
            radioSavedButton!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (cardCheckedListener != null) cardCheckedListener!!.onCardChecked(isChecked, radioSavedButton)
                makeActive(isChecked, textCardName!!)
                if (isChecked) {
                    linearSavedCard!!.visibility = View.VISIBLE
                    textSavedCard!!.visibility = View.VISIBLE
                } else {
                    linearSavedCard!!.visibility = View.GONE
                    textSavedCard!!.visibility = View.GONE
                }
            }
        }
    }

    interface OnCardCheckedListener {
        fun onCardChecked(isChecked: Boolean, radioButton: RadioButton?)
    }

    private fun makeActive(isChecked: Boolean, textView: TextView) {
        textView.typeface = if (isChecked)
            Typeface.createFromAsset(mActivity.assets, "fonts/poppins_semibold.ttf")
        else
            Typeface.createFromAsset(mActivity.assets, "fonts/poppins_regular.ttf")
        textView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            if (isChecked) mActivity.resources.getDimension(R.dimen._16ssp) else mActivity.resources.getDimension(R.dimen._14ssp)
        )
    }
}

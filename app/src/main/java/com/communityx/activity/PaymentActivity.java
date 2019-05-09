package com.communityx.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.PaymentAdapter;
import com.communityx.utils.Utils;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity implements PaymentAdapter.OnCardCheckedListener {
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.imageView)
    ImageView imageArrow;
    @BindView(R.id.recycler_cards)
    RecyclerView recyclerCards;
    @BindView(R.id.radio_debit_button)
    RadioButton radioDebitButton;
    @BindView(R.id.radio_credit_button)
    RadioButton radioCreditButton;
    @BindView(R.id.linear_debit_card)
    LinearLayout linearDebitCard;
    @BindView(R.id.linear_credit_card)
    LinearLayout linearCreditCard;
    @BindView(R.id.text_debit_amount)
    TextView textDebitAmount;
    @BindView(R.id.text_credit_amount)
    TextView textCreditAmount;
    @BindView(R.id.text_credit_card)
    TextView textCreditCard;
    @BindView(R.id.text_debit_card)
    TextView textDebitCard;

    private PaymentAdapter paymentAdapter;
    private ArrayList<String> mCardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_activity);
        ButterKnife.bind(this);

        textTitle.setText("Payment Method");
        imageArrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_praise_back_arrow));
        setAdapter(mCardList);
        setDebitCard();
        setCreditCard();

    }

    private void setAdapter(ArrayList<String> cardList) {
        recyclerCards.setLayoutManager(new LinearLayoutManager(this));
        paymentAdapter = new PaymentAdapter(cardList, PaymentActivity.this);
        paymentAdapter.setCardCheckedListener(this);
        recyclerCards.setAdapter(paymentAdapter);
    }

    private void setDebitCard() {
        radioDebitButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            radioCreditButton.setChecked(!isChecked);
            paymentAdapter.notifyDataSetChanged();
            makeActive(isChecked,textDebitCard);
            showPannel(isChecked,linearDebitCard,textDebitAmount);
        });
    }

    private void setCreditCard() {
        radioCreditButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            radioDebitButton.setChecked(!isChecked);
            paymentAdapter.notifyDataSetChanged();
            makeActive(isChecked,textCreditCard);
            showPannel(isChecked,linearCreditCard,textCreditAmount);

        });
    }

    private void makeActive(boolean isChecked, TextView textView){
        textView.setTypeface(isChecked ? Typeface.createFromAsset(getAssets(),"fonts/poppins_semibold.ttf")
                : Typeface.createFromAsset(getAssets(),"fonts/poppins_regular.ttf"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                isChecked ? getResources().getDimension(R.dimen._16ssp) : getResources().getDimension(R.dimen._14ssp) );


    }

    @Override
    public void onCardChecked(boolean isChecked, RadioButton radioButton) {
        if(!isChecked) return;
        radioDebitButton.setOnCheckedChangeListener(null);
        radioDebitButton.setChecked(false);
        radioCreditButton.setOnCheckedChangeListener(null);
        radioCreditButton.setChecked(false);

        showPannel(false,linearCreditCard,textCreditAmount);
        showPannel(false,linearDebitCard,textDebitAmount);
        makeActive(false,textDebitCard);
        makeActive(false,textCreditCard);

        setCreditCard();
        setDebitCard();
    }

    private void showPannel(boolean shouldShow, LinearLayout linearLayout, TextView textView){
        linearLayout.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        textView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }
}

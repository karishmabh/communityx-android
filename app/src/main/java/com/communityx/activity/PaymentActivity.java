package com.communityx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.PaymentAdapter;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
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
    @BindView(R.id.text_credit_card)
    LinearLayout linearCreditCard;
    @BindView(R.id.text_debit_amount)
    TextView textDebitAmount;
    @BindView(R.id.text_credit_amount)
    TextView textCreditAmount;
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
        recyclerCards.setAdapter(paymentAdapter);
    }

    private void setDebitCard() {
        radioDebitButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearDebitCard.setVisibility(View.VISIBLE);
                    textDebitAmount.setVisibility(View.VISIBLE);

                } else if (!isChecked) {
                    linearDebitCard.setVisibility(View.GONE);
                    textDebitAmount.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setCreditCard() {
        radioCreditButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearCreditCard.setVisibility(View.VISIBLE);
                    textCreditAmount.setVisibility(View.VISIBLE);

                } else if (!isChecked) {
                    linearCreditCard.setVisibility(View.GONE);
                    textCreditAmount.setVisibility(View.GONE);
                }
            }
        });
    }
}

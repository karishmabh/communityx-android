package com.communityx.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.communityx.R;

public class CrowdfundingDetailActivity extends AppCompatActivity {

    @BindView((R.id.edit_amount))
    TextInputEditText editAmount;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroupDonation;
    @BindView(R.id.textinput_amount)
    TextInputLayout inputLayoutAmount;
    @BindView(R.id.text_other_amount)
    RadioButton radioOtherAmount;
    @BindView(R.id.text_dollor_one)
    RadioButton radioDollorOne;
    @BindView(R.id.text_dollor_two)
    RadioButton radioDollorTwo;
    @BindView(R.id.button_pay)
    Button buttonPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowdfunding_detail);
        ButterKnife.bind(this);
        radioListener();
    }

    private void radioListener() {
        radioDollorOne.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) buttonPay.setBackgroundResource(R.drawable.button_active);
            radioDollorOne.setBackgroundResource(isChecked ? R.drawable.button_active : R.drawable.bg_stroke_grey);
        });

        radioDollorTwo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) buttonPay.setBackgroundResource(R.drawable.button_active);
            radioDollorTwo.setBackgroundResource(isChecked ? R.drawable.button_active : R.drawable.bg_stroke_grey);
        });

        radioOtherAmount.setOnCheckedChangeListener((buttonView, isChecked) -> {
            inputLayoutAmount.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            if(isChecked) buttonPay.setBackgroundResource(R.drawable.button_active);
            radioOtherAmount.setBackgroundResource(isChecked ? R.drawable.button_active : R.drawable.bg_stroke_grey);
        });
    }

    @OnClick({R.id.text_comment,R.id.image_comment})
    void tappedPraised(){
        startActivity(new Intent(this,PraiseActivity.class));
    }

    @OnClick(R.id.button_pay)
    void tappedPayNow(){
        startActivity(new Intent(this,PaymentActivity.class));
    }

    @OnTextChanged(R.id.edit_amount)
    void onAmountTyping(CharSequence s){
        if(s.length() < 1){
            editAmount.setText("$");
            editAmount.setSelection(1);
        }
    }

    @OnClick({R.id.view_donated_by})
    void tappedViewDonatedBy(){
        startActivity(new Intent(CrowdfundingDetailActivity.this, DonatedByActivity.class));
    }
}

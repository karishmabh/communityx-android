package com.communityx.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.communityx.R;
import com.communityx.utils.Utils;
import com.google.android.flexbox.FlexboxLayout;

public class CrowdFundingFragment extends Fragment {

    @BindView(R.id.edit_donation_date)
    TextInputEditText editDonationDate;
    @BindView(R.id.edit_cause)
    EditText editCause;
    @BindView(R.id.flex_layout_cause)
    FlexboxLayout flexboxLayoutCause;
    @BindView(R.id.button_post)
    Button buttonPost;
    @BindView(R.id.edit_amount)
    EditText editAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crowd_funding, container, false);
        ButterKnife.bind(this, view);
        setSuggestedCause();
        return view;
    }

    @OnClick({R.id.textinput_donation, R.id.edit_donation_date})
    void pickDonationDate() {
        Utils.datePicker(getActivity(), new Utils.IDateCallback() {
            @Override
            public void getDate(String date) {
                editDonationDate.setText(Utils.convertedDate(date));
            }
        });
    }

    @OnTextChanged(R.id.edit_amount)
    void onAmountTyping(CharSequence s){
        if(s.length() < 1){
            editAmount.setText("$");
            editAmount.setSelection(1);
        }
    }

    void setButtonActive(boolean isActive){
        buttonPost.setBackgroundResource(isActive? R.drawable.button_active : R.drawable.button_inactive);
    }

    private void setSuggestedCause() {
        editCause.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editCause.getRight() - editCause.getTotalPaddingRight())) {
                    String suggestedCause = editCause.getText().toString();
                    if (!suggestedCause.isEmpty()) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_suggest_cause, null);
                        TextView textView = view.findViewById(R.id.text_suggest_cause);
                        ImageView imageCross = view.findViewById(R.id.image_cross);
                        textView.setText(suggestedCause);

                        imageCross.setOnClickListener(v1 -> {
                            flexboxLayoutCause.removeView(view);
                            if (flexboxLayoutCause.getChildCount()==0) {
                                setButtonActive(false);
                            }
                        });

                        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(10, 10, 10, 10);
                        flexboxLayoutCause.addView(view, lp);
                        if (flexboxLayoutCause.getChildCount()>0) {
                            setButtonActive(true);
                        }
                        editCause.setText("");
                    }
                    return true;
                }
            }
            return false;
        });
    }

    @OnTextChanged(R.id.edit_cause)
    void onCauseTyping(CharSequence s){
        editCause.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                s.length() != 0 ? R.drawable.ic_signup_add_interest : R.drawable.ic_signup_add_interest_deselect, 0);
    }
}

package com.communityx.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.google.android.flexbox.FlexboxLayout;

/**
 * A simple {@link Fragment} subclass.
 */
    public class SignUpSelectInterest extends Fragment {

    @BindView(R.id.flex_layout_civil_right)
    FlexboxLayout flexboxLayoutCivilRight;
    @BindView(R.id.flex_layout_human_right)
    FlexboxLayout flexboxLayoutHumanRights;
    @BindView(R.id.flex_layout_health)
    FlexboxLayout flexboxLayoutHealth;
    @BindView(R.id.edit_cause)
    EditText editCause;
    @BindView(R.id.flex_layout_cause)
    FlexboxLayout flexboxLayoutCause;
    @BindView(R.id.scrollView)
    ScrollView scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_select_interest, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCivilRights();
        setHumanRights();
        setHealth();
        setSuggestedCause();
    }

    private void setCivilRights() {
        String[] civilRights = {"LGBTQ+", "Women's Rights", "Healthcare Reform", "Police Misconduct", "Gun Control", "Immigration", "Healthcare Reform", "Sports Activsm"};

        for (String civilRight : civilRights) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(getContext()).inflate(R.layout.item_interest, null);
            checkBox.setText(civilRight);
            checkBox.performClick();
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                checkBox.setBackgroundResource(isChecked ? R.drawable.bg_interest_active : R.drawable.bg_interest_inactive);
            });

            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            flexboxLayoutCivilRight.addView(checkBox, lp);
        }
    }

    private void setHumanRights() {
        String[] civilRights = {"Feminine Hygiene", "Fistula Awareness", "Hunger", "Homelessness", "Prison Reform", "Anti-Recidivism", "Human Trafficking", "Poverty", "Economic Development"};

        for (String civilRight : civilRights) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(getContext()).inflate(R.layout.item_interest, null);
            checkBox.setText(civilRight);
            checkBox.performClick();
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                checkBox.setBackgroundResource(isChecked ? R.drawable.bg_interest_active : R.drawable.bg_interest_inactive);
            });

            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            flexboxLayoutHumanRights.addView(checkBox, lp);
        }
    }

    private void setHealth() {
        String[] civilRights = {"Breast Cancer Awareness", "Eating & Exercise", "Vegan", "Mental Health", "Anxiety & Depression", "Disabilities & Special Needs", "Bullying", "Drug & Alcohol Abuse", "childhood cancer"};

        for (String civilRight : civilRights) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(getContext()).inflate(R.layout.item_interest, null);
            checkBox.setText(civilRight);
            checkBox.performClick();
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                checkBox.setBackgroundResource(isChecked ? R.drawable.bg_interest_active : R.drawable.bg_interest_inactive);
            });

            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            flexboxLayoutHealth.addView(checkBox, lp);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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
                        imageCross.setOnClickListener(v1 -> flexboxLayoutCause.removeView(view));
                        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(10, 10, 10, 10);
                        flexboxLayoutCause.addView(view, lp);
                        editCause.setText("");
                        scrollView.post(() -> scrollView.scrollTo(0, scrollView.getHeight()));
                    }
                    return true;
                }
            }
            return false;
        });
    }
}
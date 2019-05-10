package com.communityx.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.utils.Utils;
import com.google.android.flexbox.FlexboxLayout;

public class CreateEventFragment extends Fragment {

    @BindView(R.id.edit_cause)
    EditText editCause;
    @BindView(R.id.flex_layout_cause)
    FlexboxLayout flexboxLayoutCause;
    @BindView(R.id.edit_start_date)
    EditText editStartDate;
    @BindView(R.id.edit_end_date)
    EditText editEndDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_create_event, container, false);
        ButterKnife.bind(this, view);
        setSuggestedCause();
        return view;
    }

    @OnClick({R.id.edit_start_date})
    void pickStartDate() {
        Utils.datePicker(getActivity(), new Utils.IDateCallback() {
            @Override
            public void getDate(String date) {
                editStartDate.setText(Utils.convertedDateTime(date));
            }
        });
    }

    @OnClick({R.id.edit_end_date})
    void pickEndDate() {
        Utils.datePicker(getActivity(), new Utils.IDateCallback() {
            @Override
            public void getDate(String date) {
                editEndDate.setText(Utils.convertedDateTime(date));
            }
        });
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
                        });

                        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(10, 10, 10, 10);
                        flexboxLayoutCause.addView(view, lp);
                        editCause.setText("");
                    }
                    return true;
                }
            }
            return false;
        });
    }
}

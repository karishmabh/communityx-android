package com.communityx.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.communityx.R;
import com.communityx.utils.Utils;

public class SignUpStudentInfoFragment extends Fragment {

    @BindView(R.id.view_otp)
    LinearLayout viewOtpBox;
    @BindView(R.id.edit_otp_one)
    EditText editOtpOne;
    @BindView(R.id.edit_otp_two)
    EditText editOtpTwo;
    @BindView(R.id.edit_otp_three)
    EditText editOtpThree;
    @BindView(R.id.edit_otp_four)
    EditText editOtpFour;
    @BindView(R.id.edit_otp_five)
    EditText editOtpFive;
    @BindView(R.id.edit_otp_six)
    EditText editOtpSix;
    @BindView(R.id.text_send_otp)
    TextView textSendOtp;
    @BindView(R.id.text_enter_otp)
    TextView textEnterOtp;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.edit_mobile)
    TextInputEditText editMobile;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.view_password)
    LinearLayout viewPassword;
    @BindView(R.id.resend_otp)
    TextView textResendOtp;
    @BindView(R.id.edit_birthday)
    TextInputEditText editBirthDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_student_info, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOtpBox();
        viewPassword.setVisibility(View.GONE);
    }

    private boolean isDel = false;
    @OnTextChanged(R.id.edit_mobile)
    void onMobileNumberChange(CharSequence s){
        editMobile.setOnKeyListener((v, keyCode, event) -> {
            isDel = keyCode == KeyEvent.KEYCODE_DEL;
            return false;
        });

        if (s.length() < 3) {
            editMobile.setText("+91");
            editMobile.setSelection(3);
        } else if (s.length() == 4 && !isDel) {
            editMobile.setText(s.toString().substring(0,3) + "-" + s.toString().substring(3));
            editMobile.setSelection(5);
        }
    }

    @OnClick(R.id.card_add_image)
    void chooseImage() {
        showImageChooserDialog();
    }

    @OnClick(R.id.text_send_otp)
    void tappedSentOtp() {
         visibleOtpField(true);
        scrollView.post(() -> scrollView.scrollTo(0, scrollView.getHeight()));
    }

    @OnClick(R.id.edit_birthday)
    void tappedEditBirth() {
        Utils.datePicker(getActivity(), editBirthDate);
    }

    private void initOtpBox() {
        visibleOtpField(false);
        initOtpTextWatcher(editOtpOne, editOtpTwo, null);
        initOtpTextWatcher(editOtpTwo, editOtpThree, editOtpOne);
        initOtpTextWatcher(editOtpThree, editOtpFour, editOtpTwo);
        initOtpTextWatcher(editOtpFour, editOtpFive, editOtpThree);
        initOtpTextWatcher(editOtpFive, editOtpSix, editOtpFour);
        initOtpTextWatcher(editOtpSix, null, editOtpFive);
    }

    private void initOtpTextWatcher(EditText currentEditText, EditText nextEditText, EditText prevEditText) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nextEditText != null && s.length() == 1) nextEditText.requestFocus();
                else if (prevEditText != null && s.length() == 0) prevEditText.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(currentEditText.equals(editOtpSix)){
                    viewPassword.setVisibility(View.VISIBLE);
                    scrollView.post(() -> scrollView.scrollTo(0, scrollView.getHeight()));
                    visibleOtpField(false);
                }
            }
        });
    }

    private void showImageChooserDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_image_chooser, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();

        View layoutCamera = dialogView.findViewById(R.id.layout_camera);
        View layoutGallery = dialogView.findViewById(R.id.layout_gallery);
    }

    //TODO: HARD CODED STRING
    private boolean validateField(String firstName, String email, String birthDate, String postalCode) {

        if (TextUtils.isEmpty(firstName)) {
            Snackbar snackbar = Snackbar.make(constraintLayout, "Please Enter First Name", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Snackbar snackbar = Snackbar.make(constraintLayout, "Please Enter Email", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        if (TextUtils.isEmpty(birthDate)) {
            Snackbar snackbar = Snackbar.make(constraintLayout, "Please Enter Birthdate", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        if (TextUtils.isEmpty(postalCode)) {
            Snackbar snackbar = Snackbar.make(constraintLayout, "Please Enter Postal Code", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }

    private void visibleOtpField(boolean visible){
        if(visible){
            textSendOtp.setText("Change");
            textEnterOtp.setVisibility(View.VISIBLE);
            viewOtpBox.setVisibility(View.VISIBLE);
            textResendOtp.setVisibility(View.VISIBLE);
        }
        else{
            textEnterOtp.setVisibility(View.GONE);
            viewOtpBox.setVisibility(View.GONE);
            textResendOtp.setVisibility(View.GONE);
        }
    }
}
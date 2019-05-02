package com.communityx.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_student_info,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOtpBox();
        editMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 3){
                    editMobile.setText("+91");
                    editMobile.setSelection(3);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick(R.id.card_add_image)
    void chooseImage(){
        showImageChooserDialog();
    }

    @OnClick(R.id.text_send_otp)
    void tappedSentOtp(){
        textSendOtp.setText("Change");
        textEnterOtp.setVisibility(View.VISIBLE);
        viewOtpBox.setVisibility(View.VISIBLE);
        scrollView.post(() -> scrollView.scrollTo(0,scrollView.getHeight()));
    }

    private void initOtpBox() {
        textEnterOtp.setVisibility(View.GONE);
        viewOtpBox.setVisibility(View.GONE);
        initOtpTextWatcher(editOtpOne,editOtpTwo, null);
        initOtpTextWatcher(editOtpTwo,editOtpThree,editOtpOne);
        initOtpTextWatcher(editOtpThree,editOtpFour,editOtpTwo);
        initOtpTextWatcher(editOtpFour,editOtpFive,editOtpThree);
        initOtpTextWatcher(editOtpFive,editOtpSix,editOtpFour);
        initOtpTextWatcher(editOtpSix,null,editOtpFive);
    }

    private void initOtpTextWatcher(EditText currentEditText, EditText nextEditText, EditText prevEditText){
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (nextEditText != null && s.length() == 1) nextEditText.requestFocus();
               else if (prevEditText != null && s.length() == 0) prevEditText.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void showImageChooserDialog(){
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_image_chooser,null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();

        View layoutCamera = dialogView.findViewById(R.id.layout_camera);
        View layoutGallery = dialogView.findViewById(R.id.layout_gallery);
    }
}
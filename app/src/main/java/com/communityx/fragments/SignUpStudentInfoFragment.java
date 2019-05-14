package com.communityx.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
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
import com.communityx.utils.AppConstant;
import com.communityx.utils.GalleryPicker;
import com.communityx.utils.PermissionHelper;
import com.communityx.utils.Utils;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class SignUpStudentInfoFragment extends Fragment implements AppConstant, GalleryPicker.GalleryPickerListener {

    private static String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private GalleryPicker galleryPicker;
    private final int CAMERA_PERMISSION_REQ = 201;


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
    @BindView(R.id.image_profile)
    ImageView imageProfile;
    @BindView(R.id.image_add_edit)
    ImageView imageAddEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_student_info, null);
        ButterKnife.bind(this, view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionHelper permission = new PermissionHelper(getActivity());
            if (!permission.checkPermission(permissions))
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }

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
    void onMobileNumberChange(CharSequence s) {
        editMobile.setOnKeyListener((v, keyCode, event) -> {
            isDel = keyCode == KeyEvent.KEYCODE_DEL;
            return false;
        });

        if (s.length() < 3) {
            editMobile.setText("+91");
            editMobile.setSelection(3);
        } else if (s.length() == 4 && !isDel) {
            editMobile.setText(s.toString().substring(0, 3) + "-" + s.toString().substring(3));
            editMobile.setSelection(5);
        }
    }

    @OnClick(R.id.image_profile)
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            galleryPicker.fetch(requestCode, data);
        }
    }

    @Override
    public void onMediaSelected(String imagePath, Uri uri, boolean isImage) {
        imageProfile.setImageURI(uri);
        imageAddEdit.setImageResource(R.drawable.ic_signup_edit_image);
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
                if (currentEditText.equals(editOtpSix)) {
                    viewPassword.setVisibility(View.VISIBLE);
                    scrollView.post(() -> scrollView.scrollTo(0, scrollView.getHeight()));
                    visibleOtpField(false);
                }
            }
        });
    }

    private void showImageChooserDialog() {
        galleryPicker = GalleryPicker.with(getActivity(), this)
                .setListener(this)
                .showDialog();
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
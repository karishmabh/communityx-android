package com.communityx.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;

public class SignUpStudentInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_student_info,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.card_add_image)
    void chooseImage(){
        showImageChooserDialog();
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

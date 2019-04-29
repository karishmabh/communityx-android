package com.communityx.activity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.R.id;
import org.jetbrains.annotations.Nullable;

public class SignUpStudentInfoActivity extends AppCompatActivity {

    @BindView(id.text_subtitle)
    TextView textSubtitle;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student_info);
        ButterKnife.bind(this);

        textSubtitle.setText("Build your social impact identity on CommunityX.");
    }

    @OnClick(id.card_add_image)
    void chooseImage(){
       showImageChooserDialog();
    }

    private void showImageChooserDialog(){
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_image_chooser,null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();

        View layoutCamera = dialogView.findViewById(id.layout_camera);
        View layoutGallery = dialogView.findViewById(id.layout_gallery);
    }
}

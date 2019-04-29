package com.communityx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.communityx.R;
import com.communityx.R.id;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

public class SignUpStudentInfoActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student_info);

        TextView textSubtitle = findViewById(id.text_subtitle);

        textSubtitle.setText("Build your social impact identity on CommunityX.");
    }
}

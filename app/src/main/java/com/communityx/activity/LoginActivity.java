package com.communityx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.communityx.R;
import com.communityx.R.id;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

public class LoginActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        TextInputEditText editPassword = this.findViewById(id.edit_password);
        Intrinsics.checkExpressionValueIsNotNull(editPassword, "edit_password");

        editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        findViewById(id.text_signup).setOnClickListener((new OnClickListener() {
            public final void onClick(View it) {
                LoginActivity.this.startActivity(new Intent((Context)LoginActivity.this, SignupCategoryActivity.class));
                LoginActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
            }
        }));
    }

}

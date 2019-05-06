package com.communityx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.R.id;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_password)
    TextInputEditText editPassword;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Intrinsics.checkExpressionValueIsNotNull(editPassword, "edit_password");
        editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    @OnClick(id.text_signup)
    void goToSignUp(){
        LoginActivity.this.startActivity(new Intent((Context)LoginActivity.this, SignupCategoryActivity.class));
        LoginActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
    }

    @OnClick(id.button_login)
    void loginClicked(){
        LoginActivity.this.startActivity(new Intent((Context)LoginActivity.this, DashboardActivity.class));
        LoginActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
    }

}

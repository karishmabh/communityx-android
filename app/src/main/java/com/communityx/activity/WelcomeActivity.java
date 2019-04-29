package com.communityx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.R.id;
import org.jetbrains.annotations.Nullable;

public class WelcomeActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
    }

    @OnClick(id.button_login)
    void goToLogin(){
        WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        WelcomeActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
    }

    @OnClick(id.text_signup)
    void goToSignUp(){
        WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, SignupCategoryActivity.class));
        WelcomeActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
    }
}

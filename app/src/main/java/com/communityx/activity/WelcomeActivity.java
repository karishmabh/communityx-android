package com.communityx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.communityx.R;
import com.communityx.R.id;
import org.jetbrains.annotations.Nullable;

public class WelcomeActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_welcome);

        this.findViewById(id.button_login).setOnClickListener((new OnClickListener() {
            public final void onClick(View it) {
                WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                WelcomeActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
            }
        }));

        this.findViewById(id.text_signup).setOnClickListener((new OnClickListener() {
            public final void onClick(View it) {
                WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, SignupCategoryActivity.class));
                WelcomeActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
            }
        }));
    }
}

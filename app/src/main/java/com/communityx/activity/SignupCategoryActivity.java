package com.communityx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.communityx.R;
import com.communityx.R.id;
import org.jetbrains.annotations.Nullable;

public class SignupCategoryActivity extends AppCompatActivity {

    private RelativeLayout viewStudent;
    private RelativeLayout viewProfessional;
    private RelativeLayout viewOrganisation;
    private ImageView imageOrganisation;
    private ImageView imageStudent;
    private ImageView imageProfessional;
    private ImageView tickOrganisation;
    private ImageView tickStudent;
    private ImageView tickProfessional;
    private TextView textOrganisation;
    private TextView textStudent;
    private TextView textProfessional;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_signup_category);
        TextView textSubTitle = findViewById(id.text_subtitle);
        textSubTitle.setText("Build your social impact identity on CommunityX.");

        this.viewStudent = findViewById(id.view_student);
        this.viewProfessional = findViewById(id.view_professional);
        this.viewOrganisation = findViewById(id.view_organisation);
        this.imageOrganisation = findViewById(id.image_organisation);
        this.imageProfessional = findViewById(id.image_professional);
        this.imageStudent = findViewById(id.image_student);
        this.tickOrganisation = findViewById(id.image_organisation_tick);
        this.tickProfessional = findViewById(id.image_professional_tick);
        this.tickStudent = findViewById(id.image_student_tick);
        this.textOrganisation = findViewById(id.text_organisation);
        this.textProfessional = findViewById(id.text_professional);
        this.textStudent = findViewById(id.text_student);

        viewStudent.setOnClickListener((new OnClickListener() {
            public final void onClick(View it) {
                SignupCategoryActivity.this.categorySelected("student");
            }
        }));

        viewProfessional.setOnClickListener((new OnClickListener() {
            public final void onClick(View it) {
                SignupCategoryActivity.this.categorySelected("professional");
            }
        }));

        viewOrganisation.setOnClickListener((new OnClickListener() {
            public final void onClick(View it) {
                SignupCategoryActivity.this.categorySelected("organisation");
            }
        }));

        this.findViewById(id.text_login).setOnClickListener((new OnClickListener() {
            public final void onClick(View it) {
                SignupCategoryActivity.this.startActivity((new Intent(SignupCategoryActivity.this, LoginActivity.class))
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                SignupCategoryActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
                SignupCategoryActivity.this.finish();
            }
        }));

        this.findViewById(id.button_continue).setOnClickListener((new OnClickListener() {
            public final void onClick(View it) {
                SignupCategoryActivity.this.startActivity(new Intent(SignupCategoryActivity.this, SignUpStudentInfoActivity.class));
                SignupCategoryActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
            }
        }));
    }

    private final void categorySelected(String category) {
        Button buttonContinue = this.findViewById(id.button_continue);
        buttonContinue.setBackground(this.getResources().getDrawable(R.drawable.button_active));

        switch (category) {
            case ("student"):
                viewStudent.setBackground(this.getResources().getDrawable(R.drawable.border_orange_bg));
                viewProfessional.setBackground(this.getResources().getDrawable(R.drawable.bordered_bg));
                viewOrganisation.setBackground(this.getResources().getDrawable(R.drawable.bordered_bg));

                imageStudent.setImageResource(R.drawable.ic_signup_student_select);
                imageProfessional.setImageResource(R.drawable.ic_signup_professional_deselect);
                imageOrganisation.setImageResource(R.drawable.ic_signup_organization_deselect);

                textStudent.setTextColor(this.getResources().getColor(R.color.colorBlackTitle));
                textProfessional.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textOrganisation.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));

                tickStudent.setVisibility(View.VISIBLE);
                tickProfessional.setVisibility(View.GONE);
                tickOrganisation.setVisibility(View.GONE);

                break;
            case ("professional"):
                viewStudent.setBackground(this.getResources().getDrawable(R.drawable.bordered_bg));
                viewProfessional.setBackground(this.getResources().getDrawable(R.drawable.border_orange_bg));
                viewOrganisation.setBackground(this.getResources().getDrawable(R.drawable.bordered_bg));

                imageStudent.setImageResource(R.drawable.ic_signup_student_deselect);
                imageProfessional.setImageResource(R.drawable.ic_signup_professional_select);
                imageOrganisation.setImageResource(R.drawable.ic_signup_organization_deselect);

                textStudent.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textProfessional.setTextColor(this.getResources().getColor(R.color.colorBlackTitle));
                textOrganisation.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));

                tickStudent.setVisibility(View.GONE);
                tickProfessional.setVisibility(View.VISIBLE);
                tickOrganisation.setVisibility(View.GONE);

                break;
            case ("organisation"):
                viewStudent.setBackground(this.getResources().getDrawable(R.drawable.bordered_bg));
                viewProfessional.setBackground(this.getResources().getDrawable(R.drawable.bordered_bg));
                viewOrganisation.setBackground(this.getResources().getDrawable(R.drawable.border_orange_bg));

                imageStudent.setImageResource(R.drawable.ic_signup_student_deselect);
                imageProfessional.setImageResource(R.drawable.ic_signup_professional_deselect);
                imageOrganisation.setImageResource(R.drawable.ic_signup_organization_select);

                textStudent.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textProfessional.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textOrganisation.setTextColor(this.getResources().getColor(R.color.colorBlackTitle));

                tickStudent.setVisibility(View.GONE);
                tickProfessional.setVisibility(View.GONE);
                tickOrganisation.setVisibility(View.VISIBLE);
                return;
        }
    }
}

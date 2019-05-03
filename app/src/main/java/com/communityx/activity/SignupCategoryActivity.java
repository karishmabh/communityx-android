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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.R.id;
import org.jetbrains.annotations.Nullable;

public class SignupCategoryActivity extends AppCompatActivity {

    @BindView(id.view_student)
    RelativeLayout viewStudent;
    @BindView(id.view_professional)
    RelativeLayout viewProfessional;
    @BindView(id.view_organisation)
    RelativeLayout viewOrganisation;
    @BindView(id.image_organisation)
    ImageView imageOrganisation;
    @BindView(id.image_student)
    ImageView imageStudent;
    @BindView(id.image_professional)
    ImageView imageProfessional;
    @BindView(id.image_organisation_tick)
    ImageView tickOrganisation;
    @BindView(id.image_student_tick)
    ImageView tickStudent;
    @BindView(R.id.image_professional_tick)
    ImageView tickProfessional;
    @BindView(id.text_organisation)
    TextView textOrganisation;
    @BindView(id.text_student)
    TextView textStudent;
    @BindView(id.text_professional)
    TextView textProfessional;
    @BindView(id.button_continue)
    Button buttonContinue;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_signup_category);
        ButterKnife.bind(this);
        TextView textSubTitle = findViewById(id.text_subtitle);
        textSubTitle.setText("Build your social impact identity on CommunityX.");
        buttonContinue.setClickable(false);
        buttonContinue.setAlpha(0.5f);
    }

    @OnClick({id.view_student, id.view_professional,id.view_organisation})
    void selectCategory(View it) {
        if (it.equals(viewStudent)) categorySelected("student");
        else if (it.equals(viewProfessional)) categorySelected("professional");
        else if (it.equals(viewOrganisation)) categorySelected("organisation");
    }


    @OnClick(id.button_continue)
    void letsContinue(){
        SignupCategoryActivity.this.startActivity(new Intent(SignupCategoryActivity.this, SignUpStudentInfoActivity.class));
        SignupCategoryActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
    }

    @OnClick(id.text_login)
    void goToLogin(){
        SignupCategoryActivity.this.startActivity((new Intent(SignupCategoryActivity.this, LoginActivity.class))
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        SignupCategoryActivity.this.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out);
        SignupCategoryActivity.this.finish();
    }

    private final void categorySelected(String category) {
        buttonContinue.setClickable(true);
        buttonContinue.setAlpha(1.0f);

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

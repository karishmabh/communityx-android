package com.communityx.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.activity.SignUpStudentInfoActivity;

import java.util.Objects;

public class SignUpSchoolCollegeFragment extends Fragment {

    @BindView(R.id.view_school)
    View viewSchool;
    @BindView(R.id.view_college)
    View viewCollege;
    @BindView(R.id.layout_school)
    LinearLayout layoutSchool;
    @BindView(R.id.layout_college)
    LinearLayout layoutCollege;
    @BindView(R.id.image_school)
    ImageView imageSchool;
    @BindView(R.id.image_college)
    ImageView imageCollege;
    @BindView(R.id.image_school_tick)
    ImageView tickSchool;
    @BindView(R.id.image_college_tick)
    ImageView tickCollege;
    @BindView(R.id.text_school)
    TextView textSchool;
    @BindView(R.id.text_college)
    TextView textCollege;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_school_college, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.view_school, R.id.view_college})
    void tappedQualificationInfo(View it) {
        if (it.equals(viewSchool)) selectQualificationInfo(Qualification.SCHOOL);
        else if (it.equals(viewCollege)) selectQualificationInfo(Qualification.COLLEGE);
    }

    private void selectQualificationInfo(Qualification qualification) {
        ((SignUpStudentInfoActivity) Objects.requireNonNull(getActivity())).enableButton(true);


        switch (qualification) {
            case SCHOOL:
                viewSchool.setBackground(getResources().getDrawable(R.drawable.border_orange_bg));
                viewCollege.setBackground(getResources().getDrawable(R.drawable.bordered_bg));

                imageSchool.setImageResource(R.drawable.ic_signup_highschool_select);
                imageCollege.setImageResource(R.drawable.ic_signup_college_deselect);

                textSchool.setTextColor(this.getResources().getColor(R.color.colorBlackTitle));
                textCollege.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));

                tickSchool.setVisibility(View.VISIBLE);
                tickCollege.setVisibility(View.GONE);

                layoutSchool.setVisibility(View.VISIBLE);
                layoutCollege.setVisibility(View.GONE);
                break;

            case COLLEGE:
                viewCollege.setBackground(getResources().getDrawable(R.drawable.border_orange_bg));
                viewSchool.setBackground(getResources().getDrawable(R.drawable.bordered_bg));

                imageCollege.setImageResource(R.drawable.ic_signup_college_select);
                imageSchool.setImageResource(R.drawable.ic_signup_highschool_deselect);

                textCollege.setTextColor(this.getResources().getColor(R.color.colorBlackTitle));
                textSchool.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));

                tickCollege.setVisibility(View.VISIBLE);
                tickSchool.setVisibility(View.GONE);

                layoutCollege.setVisibility(View.VISIBLE);
                layoutSchool.setVisibility(View.GONE);
                break;
        }
    }

    private enum Qualification {
        SCHOOL,
        COLLEGE
    }
}

package com.communityx.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.R.id;
import com.communityx.activity.SignUpStudentInfoActivity;

import java.util.Objects;

public class SignUpRoleFragment extends Fragment {

    @BindView(id.view_freshman_main)
    RelativeLayout viewFreshman;
    @BindView(id.view_sophomore_main)
    RelativeLayout viewSophomore;
    @BindView(id.view_junior_main)
    RelativeLayout viewJunior;
    @BindView(id.view_senior_main)
    RelativeLayout viewSenior;
    @BindView(id.image_freshman)
    ImageView imageFreshman;
    @BindView(id.image_sophomore)
    ImageView imageSophomore;
    @BindView(id.image_junior)
    ImageView imageJunior;
    @BindView(id.image_senior)
    ImageView imageSenior;
    @BindView(id.image_freshman_tick)
    ImageView tickFreshman;
    @BindView(id.image_sophomore_tick)
    ImageView tickSophomore;
    @BindView(id.image_junior_tick)
    ImageView tickJunior;
    @BindView(id.image_senior_tick)
    ImageView tickSenior;
    @BindView(id.text_freshman)
    TextView textFreshman;
    @BindView(id.text_sophomore)
    TextView textSophomore;
    @BindView(id.text_junior)
    TextView textJunior;
    @BindView(id.text_senior)
    TextView textSenior;

    private enum Role{
        FRESHMAN,
        SOPHOMORE,
        JUNIOR,
        SENIOR
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_select_role,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({id.view_freshman_main,id.view_sophomore_main,id.view_junior_main,id.view_senior_main})
    void tappedRole(View view){
        if (view.equals(viewFreshman)) selectRole(Role.FRESHMAN);
        else if (view.equals(viewSophomore)) selectRole(Role.SOPHOMORE);
        else if (view.equals(viewJunior)) selectRole(Role.JUNIOR);
        else if (view.equals(viewSenior)) selectRole(Role.SENIOR);
    }

    private void selectRole(Role freshman) {
        ((SignUpStudentInfoActivity) Objects.requireNonNull(getActivity())).buttonContinue.setBackground(this.getResources().getDrawable(R.drawable.button_active));
        ((SignUpStudentInfoActivity) Objects.requireNonNull(getActivity())).buttonContinue.setTag(true);

        switch (freshman){
            case FRESHMAN:
                viewFreshman.setBackground(getActivity().getResources().getDrawable(R.drawable.border_orange_bg));
                viewSophomore.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));
                viewJunior.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));
                viewSenior.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));

                imageFreshman.setImageResource(R.drawable.ic_signup_freshman_select);
                imageSophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect);
                imageJunior.setImageResource(R.drawable.ic_signup_junior_deselect);
                imageSenior.setImageResource(R.drawable.ic_signup_senior_deselect);

                textFreshman.setTextColor(this.getResources().getColor(R.color.colorBlackTitle));
                textSophomore.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textJunior.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textSenior.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));

                tickFreshman.setVisibility(View.VISIBLE);
                tickSophomore.setVisibility(View.GONE);
                tickJunior.setVisibility(View.GONE);
                tickSenior.setVisibility(View.GONE);
                break;

            case SOPHOMORE:
                viewSophomore.setBackground(getActivity().getResources().getDrawable(R.drawable.border_orange_bg));
                viewFreshman.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));
                viewJunior.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));
                viewSenior.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));

                imageSophomore.setImageResource(R.drawable.ic_signup_sophomore_select);
                imageFreshman.setImageResource(R.drawable.ic_signup_freshman_deselect);
                imageJunior.setImageResource(R.drawable.ic_signup_junior_deselect);
                imageSenior.setImageResource(R.drawable.ic_signup_senior_deselect);

                textSophomore.setTextColor(this.getResources().getColor(R.color.colorBlackTitle));
                textFreshman.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textJunior.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textSenior.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));

                tickSophomore.setVisibility(View.VISIBLE);
                tickFreshman.setVisibility(View.GONE);
                tickJunior.setVisibility(View.GONE);
                tickSenior.setVisibility(View.GONE);
                break;

            case JUNIOR:
                viewJunior.setBackground(getActivity().getResources().getDrawable(R.drawable.border_orange_bg));
                viewFreshman.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));
                viewSophomore.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));
                viewSenior.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));

                imageJunior.setImageResource(R.drawable.ic_signup_junior_select);
                imageFreshman.setImageResource(R.drawable.ic_signup_freshman_deselect);
                imageSophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect);
                imageSenior.setImageResource(R.drawable.ic_signup_senior_deselect);

                textJunior.setTextColor(this.getResources().getColor(R.color.colorBlackTitle));
                textFreshman.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textSophomore.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textSenior.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));

                tickJunior.setVisibility(View.VISIBLE);
                tickFreshman.setVisibility(View.GONE);
                tickSophomore.setVisibility(View.GONE);
                tickSenior.setVisibility(View.GONE);
                break;

            case SENIOR:
                viewSenior.setBackground(getActivity().getResources().getDrawable(R.drawable.border_orange_bg));
                viewFreshman.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));
                viewSophomore.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));
                viewJunior.setBackground(getActivity().getResources().getDrawable(R.drawable.bordered_bg));

                imageSenior.setImageResource(R.drawable.ic_signup_senior_select);
                imageFreshman.setImageResource(R.drawable.ic_signup_freshman_deselect);
                imageSophomore.setImageResource(R.drawable.ic_signup_sophomore_deselect);
                imageJunior.setImageResource(R.drawable.ic_signup_junior_deselect);

                textSenior.setTextColor(this.getResources().getColor(R.color.colorBlackTitle));
                textFreshman.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textSophomore.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));
                textJunior.setTextColor(this.getResources().getColor(R.color.colorLightestGrey));

                tickSenior.setVisibility(View.VISIBLE);
                tickFreshman.setVisibility(View.GONE);
                tickSophomore.setVisibility(View.GONE);
                tickJunior.setVisibility(View.GONE);
                break;
        }
    }
}

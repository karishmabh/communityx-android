package com.communityx.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;

public class SignUpMemberOfClub extends Fragment {

    @BindView(R.id.spinner_club_name)
    AppCompatSpinner spinnerClubName;
    @BindView(R.id.spinner_role)
    AppCompatSpinner spinnerRole;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_member_of_club, null);
        ButterKnife.bind(this,view);

        spinnerClubName.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_member_of_club,R.id.text_item, new String[]{"Amnesty International", "Amnesty International"}));
        spinnerRole.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_member_of_club,R.id.text_item, new String[]{"President", "President"}));
        return view;
    }

}

package com.communityx.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.communityx.R;
import com.communityx.activity.EventActivity;
import com.communityx.activity.LoginActivity;
import com.communityx.activity.ProfileActivity;
import com.communityx.models.login.LoginResponse;
import com.communityx.models.logout.LogoutResponse;
import com.communityx.network.DataManager;
import com.communityx.network.ResponseListener;
import com.communityx.utils.AppConstant;
import com.communityx.utils.AppPreference;
import com.communityx.utils.CustomToolBarHelper;
import com.communityx.utils.DialogHelper;
import com.communityx.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements AppConstant {

    @BindView(R.id.image_user_profile)
    CircleImageView imageUser;
    @BindView(R.id.linear_top)
    LinearLayout linearTop;
    @BindView(R.id.text_name)
    TextView textName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        CustomToolBarHelper customToolBarHelper = new CustomToolBarHelper(view);
        customToolBarHelper.setTitle(R.string.my_profile);
        textName.setText(AppPreference.Companion.getInstance(getActivity()).getString(PREF_EMAIL));
        return view;
    }

    @OnClick({R.id.button_view_profile,R.id.image_user_profile})
    void tappedProfile(){
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                Objects.requireNonNull(getActivity()),
                imageUser,
                Objects.requireNonNull(ViewCompat.getTransitionName(imageUser)));
        Objects.requireNonNull(getContext()).startActivity(intent, options.toBundle());
    }

    @OnClick({R.id.image_event_arrow,R.id.image_crowdfunding_arrow,R.id.image_logout_arrow})
    void tappedOptions(View it){
        if(it.getId() == R.id.image_event_arrow){
            startActivity(new Intent(getContext(), EventActivity.class));
            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.anim_next_slide_in,R.anim.anim_next_slide_out);
        }
    }

    @OnClick(R.id.constraint_logout)
    void tappedLogout() {
        DialogHelper.showLogoutDialog(getActivity(), new DialogHelper.IDialogCallback() {
            @Override
            public void onConfirmed() {
                doLogout();
            }

            @Override
            public void onDenied() {
            }
        });
    }

    private void doLogout() {
        if (getActivity() == null) return;

        DataManager.INSTANCE.doLogOut(getActivity(), new ResponseListener<LogoutResponse>() {
            @Override
            public void onSuccess(LogoutResponse response) {

                AppPreference.Companion.getInstance(getActivity()).clearSession();
                startActivity(new Intent(getActivity(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                Toast.makeText(getActivity(), "You have been Logout successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NotNull Object error) {
                Utils.showError(getActivity(), linearTop, error);
            }
        });
    }
}

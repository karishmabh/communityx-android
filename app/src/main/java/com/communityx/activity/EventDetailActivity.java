package com.communityx.activity;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.adapters.MultipleImagesAdapter;
import com.communityx.utils.CustomToolBarHelper;

import java.util.ArrayList;

public class EventDetailActivity extends AppCompatActivity {

    @BindView(R.id.recycler_going)
    RecyclerView recyclerGoing;
    @BindView(R.id.recycler_interested)
    RecyclerView recyclerInterested;

    @BindDrawable(R.drawable.ic_event_details_gray_star)
    Drawable drawableStar;
    @BindDrawable(R.drawable.ic_interested_popup_going_select)
    Drawable drawableGoing;
    @BindDrawable(R.drawable.ic_interested_popup_not_interested_select)
    Drawable drawableNotInterested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        ButterKnife.bind(this);
        setUpToobar();
        setRecyclerImages();
    }

    private void setUpToobar() {
        CustomToolBarHelper customToolBarUtils = new CustomToolBarHelper(this);
        customToolBarUtils.setTitle(R.string.event_detail);
        customToolBarUtils.enableBackPress();
    }

    private void setRecyclerImages() {
        ArrayList<String> imagesArrayList = new ArrayList<>();
        imagesArrayList.add("1");
        imagesArrayList.add("1");
        imagesArrayList.add("1");
        imagesArrayList.add("1");

        recyclerGoing.setLayoutManager(new LinearLayoutManager(EventDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerInterested.setLayoutManager(new LinearLayoutManager(EventDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));

        recyclerGoing.setAdapter(new MultipleImagesAdapter(EventDetailActivity.this, imagesArrayList));
        recyclerInterested.setAdapter(new MultipleImagesAdapter(EventDetailActivity.this, imagesArrayList));
    }

    @OnClick(R.id.button_interested)
    void openBottomSheet() {
        openDialog();
    }

    public void openDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setContentView(R.layout.dialog_interested);
        window.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        dialog.setCancelable(true);

        RadioButton radioInterested = dialog.findViewById(R.id.radio_button_1);
        RadioButton radioGoing = dialog.findViewById(R.id.radio_button_2);
        RadioButton radioNotInterested = dialog.findViewById(R.id.radio_button_3);

        radioInterested.setChecked(true);
        setTint(radioGoing, getResources().getColor(R.color.colorLightGrey), drawableGoing);
        setTint(radioNotInterested, getResources().getColor(R.color.colorLightGrey), drawableNotInterested);

        radioInterested.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setTint(radioInterested, getResources().getColor(R.color.colorAccent), drawableStar);
                } else {
                    setTint(radioInterested, getResources().getColor(R.color.colorLightGrey), drawableStar);
                }
            }
        });

        radioGoing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setTint(radioGoing, getResources().getColor(R.color.colorAccent), drawableGoing);
                } else {
                    setTint(radioGoing, getResources().getColor(R.color.colorLightGrey), drawableGoing);
                }
            }
        });

        radioNotInterested.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setTint(radioNotInterested, getResources().getColor(R.color.colorAccent), drawableNotInterested);
                } else {
                    setTint(radioNotInterested, getResources().getColor(R.color.colorLightGrey), drawableNotInterested);
                }
            }
        });
        dialog.show();
    }

    private void setTint(RadioButton radioButton, int color, Drawable drawable1) {
        Drawable drawable = drawable1;
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);

        radioButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }
}

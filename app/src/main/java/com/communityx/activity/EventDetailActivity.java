package com.communityx.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.adapters.MultipleImagesAdapter;
import com.communityx.utils.CustomToolBarHelper;

import java.util.ArrayList;
import java.util.Objects;

public class EventDetailActivity extends AppCompatActivity {

    @BindView(R.id.recycler_going)
    RecyclerView recyclerGoing;
    @BindView(R.id.recycler_interested)
    RecyclerView recyclerInterested;

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
        window.setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        dialog.setCancelable(true);

        RadioButton radioButton = dialog.findViewById(R.id.radio_button_1);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        radioButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }
                }
            }
        });
        dialog.show();
    }
}

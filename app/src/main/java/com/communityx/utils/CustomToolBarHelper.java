package com.communityx.utils;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;

public class CustomToolBarHelper {

    @BindView(R.id.imageView)
    ImageView imageLogo;
    @BindView(R.id.image_tail_toolbar)
    ImageView imageTail;
    @BindView(R.id.text_title)
    TextView textTitle;
    private Activity activity;

    public CustomToolBarHelper(@NonNull View view) {
        ButterKnife.bind(this,view);
    }

    public CustomToolBarHelper(@NonNull Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        ButterKnife.bind(this,view);
        this.activity = activity;
    }

    public void setTitle(String title){
        if(textTitle == null) return;
        textTitle.setText(title);
    }

    public void setTitle(@StringRes int title){
        if(textTitle == null) return;
        textTitle.setText(title);
    }

    public void setLogoIcon(@DrawableRes int resId){
        if(imageLogo == null) return;
        imageLogo.setImageResource(resId);
    }

    public void setImageTail(@DrawableRes int resId) {
        if(imageTail == null) return;
        imageTail.setVisibility(View.VISIBLE);
        imageTail.setImageResource(resId);
    }

    public void enableBackPress(){
        setLogoIcon(R.drawable.ic_praise_back_arrow);
        goBack();
    }

    private void goBack(){
        if(activity == null) return;
        imageLogo.setOnClickListener(v -> activity.finish());
    }
}

package com.communityx.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;

public class CustomToolBarUtils {

    @BindView(R.id.imageView)
    ImageView imageLogo;
    @BindView(R.id.image_tail_toolbar)
    ImageView imageTail;
    @BindView(R.id.text_title)
    TextView textTitle;

    public CustomToolBarUtils(@NonNull View view) {
        ButterKnife.bind(this,view);
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
        imageTail.setImageResource(resId);
    }
}

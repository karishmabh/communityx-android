package com.communityx.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.communityx.R;
import com.communityx.utils.GalleryPicker;
import com.google.android.flexbox.FlexboxLayout;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class CreatePostContentFragment extends Fragment implements GalleryPicker.GalleryPickerListener {

    @BindView(R.id.edit_cause)
    EditText editCause;
    @BindView(R.id.flex_layout_cause)
    FlexboxLayout flexboxLayoutCause;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.button_post)
    Button buttonPost;

    private GalleryPicker galleryPicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post_content, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSuggestedCause();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setSuggestedCause() {
        editCause.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editCause.getRight() - editCause.getTotalPaddingRight())) {
                    String suggestedCause = editCause.getText().toString();
                    if (!suggestedCause.isEmpty()) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_suggest_cause, null);
                        TextView textView = view.findViewById(R.id.text_suggest_cause);
                        ImageView imageCross = view.findViewById(R.id.image_cross);
                        textView.setText(suggestedCause);
                        imageCross.setOnClickListener(v1 -> flexboxLayoutCause.removeView(view));
                        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(10, 10, 10, 10);
                        flexboxLayoutCause.addView(view, lp);
                        editCause.setText("");
                    }
                    return true;
                }
            }
            return false;
        });
    }

    @OnTextChanged(R.id.edit_content)
    void onWritingContent(CharSequence s){
        buttonPost.setBackgroundResource(s.length() != 0 ? R.drawable.button_active : R.drawable.button_inactive);
    }

    @OnClick({R.id.image_video,R.id.image_gallery})
    void tappedChooseMedia(View it){
       galleryPicker = GalleryPicker.with(getActivity(),this)
               .setMedia(it.getId() == R.id.image_gallery ? GalleryPicker.Media.IMAGE : GalleryPicker.Media.VIDEO)
               .setListener(this)
               .showDialog();
    }

    @Override
    public void onMediaSelected(String path, Uri uri, boolean isImage) {
        //TODO: set image/video
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) galleryPicker.fetch(requestCode,data);
    }

    @OnTextChanged(R.id.edit_cause)
    void onCauseTyping(CharSequence s){
        editCause.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                s.length() != 0 ? R.drawable.ic_signup_add_interest : R.drawable.ic_signup_add_interest_deselect, 0);
    }
}

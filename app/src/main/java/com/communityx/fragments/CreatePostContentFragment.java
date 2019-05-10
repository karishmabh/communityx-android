package com.communityx.fragments;


import android.annotation.SuppressLint;
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
import com.google.android.flexbox.FlexboxLayout;

import java.util.Objects;


public class CreatePostContentFragment extends Fragment {

    @BindView(R.id.edit_cause)
    EditText editCause;
    @BindView(R.id.flex_layout_cause)
    FlexboxLayout flexboxLayoutCause;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.button_post)
    Button buttonPost;

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_image_chooser,null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Objects.requireNonNull(getContext()));
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}

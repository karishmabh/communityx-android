package com.communityx.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.communityx.R;

public class DialogHelper {

    public static void showEventDialog(Context context, String content){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        //window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setContentView(R.layout.dialog_create_event);
        dialog.setCancelable(true);
        dialog.show();
    }

    public static void showDonationSuccessDialog(Context context, String amount){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setContentView(R.layout.dialog_donation_success);
        dialog.setCancelable(true);
        dialog.show();

        ImageView imageClose = dialog.findViewById(R.id.image_cross);
        imageClose.setOnClickListener(v -> dialog.dismiss());
        TextView textDonation = dialog.findViewById(R.id.text_amount);
        textDonation.setText(amount);
    }
}

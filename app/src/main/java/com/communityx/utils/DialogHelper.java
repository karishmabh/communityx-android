package com.communityx.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.communityx.R;

public class DialogHelper {

    public static void showReportingDialog(Context context,@NonNull String content){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setContentView(R.layout.dialog_reporting);
        dialog.setCancelable(true);
        dialog.show();

        ImageView imageClose = dialog.findViewById(R.id.image_cross);
        imageClose.setOnClickListener(v -> dialog.dismiss());
        TextView textContent = dialog.findViewById(R.id.text_content);
        textContent.setText(content);
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

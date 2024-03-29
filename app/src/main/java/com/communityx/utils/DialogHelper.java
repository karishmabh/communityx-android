package com.communityx.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.communityx.R;
import com.communityx.network.DataManager;

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



     public static void selectImage(Activity activity) {
          final int PICK_FROM_CAMERA = 0;
          final int PICK_FROM_GALLERY = 1;

         View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_image_chooser, null);
         BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
         bottomSheetDialog.setContentView(dialogView);
         bottomSheetDialog.show();

         View layoutGallery = dialogView.findViewById(R.id.layout_gallery);
         layoutGallery.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                         android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 activity.startActivityForResult(pickPhoto, PICK_FROM_GALLERY);
                 bottomSheetDialog.dismiss();
             }
         });

         View layoutCamera = dialogView.findViewById(R.id.layout_camera);
         layoutCamera.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 activity.startActivityForResult(takePicture, PICK_FROM_CAMERA);
                 bottomSheetDialog.dismiss();
             }
         });
         bottomSheetDialog.show();
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

    public static void showLogoutDialog(Context context, IDialogCallback iDialogCallback) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setCancelable(true);

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        Window window = dialog.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        layoutParams.gravity = Gravity.CENTER;

        dialog.findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iDialogCallback.onConfirmed();
            }
        });

        dialog.findViewById(R.id.button_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.image_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showReportPopDialog(Context context, IDialogCallback iDialogCallback) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_report_pop, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(dialogView);

        bottomSheetDialog.findViewById(R.id.card_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                iDialogCallback.onConfirmed();
            }
        });
        bottomSheetDialog.show();
    }

    public static void showReportDialog(Context context, IDialogCallback iDialogCallback) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_report_post);
        dialog.setCancelable(true);

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        Window window = dialog.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        layoutParams.gravity = Gravity.CENTER;

        dialog.findViewById(R.id.image_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.button_community).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public interface IDialogCallback {
        void onConfirmed();
        void onDenied();
    }
}

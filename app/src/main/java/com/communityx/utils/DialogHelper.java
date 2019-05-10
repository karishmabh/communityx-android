package com.communityx.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
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
}

package com.communityx.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.communityx.R;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class GalleryPicker {

    public static final int CAPTURE_IMAGE = 100;
    public static final int PICK_GALLERY = 200;
    private static final String TAG = "GalleryPicker";
    private Activity mActivity;
    private GalleryPickerListener galleryPickerListener;
    private Uri mSelectedImage;

    private GalleryPicker(Activity activity) {
        this.mActivity = activity;
    }

    @RequiresPermission(allOf = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    })
    public static GalleryPicker with(Activity activity) {
        return new GalleryPicker(activity);
    }

    private static String getImagePath(Activity activity, Uri uri) {
        String path = null;
        try {
            if (uri != null) {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
                if (cursor == null) return null;

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private static Uri getImageUri(Context mContext, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), photo, "", null);
        return Uri.parse(path);
    }

    public GalleryPicker setListener(GalleryPickerListener galleryPickerListener) {
        this.galleryPickerListener = galleryPickerListener;
        return this;
    }

    public GalleryPicker showDialog() {
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_image_chooser, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mActivity);
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();
        initViews(dialogView);
        return this;
    }

    public void fetch(int requestCode, Intent data) {
        switch (requestCode) {
            case PICK_GALLERY:
                mSelectedImage = data.getData();
                break;
            case CAPTURE_IMAGE:
                Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                assert bitmap != null;
                mSelectedImage = getImageUri(mActivity, bitmap);
                break;
        }
        galleryPickerListener.onImageSelected(getImagePath(mActivity, mSelectedImage), mSelectedImage);
    }

    private void initViews(View view) {
        ImageView imageCamera = view.findViewById(R.id.image_camera);
        ImageView imageGallery = view.findViewById(R.id.image_gallery);

        imageCamera.setOnClickListener(v -> fireIntent(Option.CAMERA, CAPTURE_IMAGE));
        imageGallery.setOnClickListener(v -> fireIntent(Option.GALLERY, PICK_GALLERY));
    }

    private void fireIntent(Option option, int requestCode) {
        Intent intent = new Intent();
        if (option == Option.GALLERY) {
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else if (option == Option.CAMERA) {
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        try {
            mActivity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "fireIntent: Activity not found");
        }
    }

    private enum Option {
        CAMERA,
        GALLERY
    }

    public interface GalleryPickerListener {
        void onImageSelected(String imagePath, Uri uri);
    }
}

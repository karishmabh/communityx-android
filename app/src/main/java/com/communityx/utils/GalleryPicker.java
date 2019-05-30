package com.communityx.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
    private Fragment mFragment;
    private GalleryPickerListener galleryPickerListener;
    private Uri mSelectedImage;
    private BottomSheetDialog bottomSheetDialog;
    private Media media = Media.IMAGE;

    private GalleryPicker(Activity activity, Fragment fragment) {
        this.mFragment = fragment;
        this.mActivity = activity;
    }

    private GalleryPicker(Activity activity) {
        this.mActivity = activity;
    }

    public static GalleryPicker with(Activity activity, Fragment fragment) {
        return new GalleryPicker(activity, fragment);
    }

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

    public GalleryPicker setMedia(Media media) {
        this.media = media;
        return this;
    }

    public GalleryPicker showDialog() {
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_image_chooser, null);
        bottomSheetDialog = new BottomSheetDialog(mActivity);
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
                if (media == Media.IMAGE) {
                    Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    assert bitmap != null;
                    mSelectedImage = getImageUri(mActivity, bitmap);
                } else mSelectedImage = data.getData();
                break;
        }
        galleryPickerListener.onMediaSelected(getImagePath(mActivity, mSelectedImage), mSelectedImage, media == Media.IMAGE);
    }

    private void initViews(View view) {
        ImageView imageCamera = view.findViewById(R.id.image_camera);
        ImageView imageGallery = view.findViewById(R.id.image_gallery);

        imageCamera.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Camera Permission Required");
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 101);
                return;
            }
            fireIntent(Option.CAMERA, CAPTURE_IMAGE);
        });
        imageGallery.setOnClickListener(v -> {
            fireIntent(Option.GALLERY, PICK_GALLERY);
            bottomSheetDialog.dismiss();
        });
    }

    private void fireIntent(Option option, int requestCode) {
        Intent intent = new Intent();
        if (option == Option.GALLERY) {
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType(media.getMedia());
        } else if (option == Option.CAMERA) {
            intent.setAction(media == Media.IMAGE ? MediaStore.ACTION_IMAGE_CAPTURE : MediaStore.ACTION_VIDEO_CAPTURE);
        }
        try {
            if (mFragment != null) mFragment.startActivityForResult(intent, requestCode);
            else mActivity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "fireIntent: Activity not found");
        }
    }

    private enum Option {
        CAMERA,
        GALLERY
    }

    public enum Media {

        IMAGE("image/jpg"),
        VIDEO("video/*");
        private String media;

        Media(String media) {
            this.media = media;
        }

        public String getMedia() {
            return media;
        }
    }

    public interface GalleryPickerListener {
        void onMediaSelected(String imagePath, Uri uri, boolean isImage);
    }
}

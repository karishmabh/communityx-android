package com.communityx.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.communityx.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class GalleryPicker {

    public static final int CAPTURE_IMAGE = 100;
    public static final int PICK_GALLERY = 200;
    public static final int CAMERA_PERMISSION_CODE = 1000;
    public static final int ALL_PERMISSION_CODE = 5000;
    public static final int GALLERY_PERMISSION_CODE = 2000;
    private static final String TAG = "GalleryPicker";
    private Activity mActivity;
    private Fragment mFragment;
    private GalleryPickerListener galleryPickerListener;
    private Uri mSelectedImage;
    private BottomSheetDialog bottomSheetDialog;
    private Media media = Media.IMAGE;
    private String[] permissions = {Manifest.permission.CAMERA,  Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.READ_EXTERNAL_STORAGE};

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
                    try {
                        rotateImageIfRequired(mActivity, bitmap, mSelectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else mSelectedImage = data.getData();
                break;
        }
        galleryPickerListener.onMediaSelected(getImagePath(mActivity, mSelectedImage), mSelectedImage, media == Media.IMAGE);
    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {
        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public void onResultPermission(int requestCode, int[] grantResults) {
        if (requestCode == GalleryPicker.CAMERA_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        } else if (requestCode == GalleryPicker.GALLERY_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void initViews(View view) {
        View imageCamera = view.findViewById(R.id.layout_camera);
        View imageGallery = view.findViewById(R.id.layout_gallery);

        if (!new PermissionHelper(mActivity).checkPermission(permissions)) {
            mFragment.requestPermissions(permissions, ALL_PERMISSION_CODE);
        }

        imageCamera.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (mFragment != null) {
                    mFragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                    return;
                }
                requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                return;
            }
            fireIntent(Option.CAMERA, CAPTURE_IMAGE);
        });

        imageGallery.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (mFragment != null) {
                    mFragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, GALLERY_PERMISSION_CODE);
                    return;
                }
                requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, GALLERY_PERMISSION_CODE);
                return;
            }
            fireIntent(Option.GALLERY, PICK_GALLERY);
        });
    }

    public void openCamera() {
        fireIntent(Option.CAMERA, CAPTURE_IMAGE);
    }

    public void openGallery() {
        fireIntent(Option.GALLERY, PICK_GALLERY);
    }

    public void fireIntent(Option option, int requestCode) {
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

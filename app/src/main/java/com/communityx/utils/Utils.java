package com.communityx.utils;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.communityx.R;
import com.communityx.datepicker.DateTimeWheel.DateWheel.DatePickerPopWin;
import com.communityx.datepicker.MainActivity;
import com.squareup.picasso.Picasso;
import io.blackbox_vision.wheelview.view.DatePickerPopUpWindow;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String PHONE_REGEX = "^[0-9\\+]*$";
    private static final String WEBSITE_REGEX = "^(https?:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean validateWebsite(String website) {
        Pattern pattern = Pattern.compile(WEBSITE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(website);
        return matcher.matches();
    }

    public static void showError(Activity activity, View view, Object error) {
        ErrorManager errorManager = new ErrorManager(activity, view, error);
        errorManager.handleErrorResponse();
    }

    public static void datePicker(Activity activity, EditText editText) {
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear += 1;
                        editText.setText(String.format("%s/%d/%d", "0" + monthOfYear, dayOfMonth, year));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static boolean isValid(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }

    public static void datePicker(Activity activity, IDateCallback iDateCallback) {
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear += 1;
                        iDateCallback.getDate(String.format("%s/%d/%d", "0" + monthOfYear, dayOfMonth, year));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    static String pickedTime = "";

    public static void iosModDatePicker(Activity activity, IDateCallback iDateCallback) {
        pickedTime = "2010-6-21";
        final DatePickerPopUpWindow datePicker = new DatePickerPopUpWindow.Builder(activity)
                .setMinYear(1950)
                .setMaxYear(2011)
                .setSelectedDate(pickedTime)
                .setConfirmButtonText("CONFIRM")
                .setOnDateSelectedListener(new DatePickerPopUpWindow.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(int i, int i1, int i2) {
                        pickedTime = String.format("%s/%d/%d", i1, i2, i);
                        iDateCallback.getDate(String.format("%s/%d/%d", i1, i2, i));
                    }
                })
                .setCancelButtonText("CANCEL")
                .setConfirmButtonTextColor(activity.getResources().getColor(R.color.colorAccent))
                .setCancelButtonTextColor(activity.getResources().getColor(R.color.colorLightGrey))
                .setButtonTextSize(16)
                .setViewTextSize(22)
                .build();
        datePicker.show(activity);
    }

    private void onDateSelected(int year, int month, int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());

        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
    }

     public static void openDatePickerDialog(Activity activity, IDateCallback iDateCallback) {
         pickedTime = "2010-6-21";
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(activity, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                iDateCallback.getDate(String.format("%s/%d/%d", month, day, year));
            }
        })      .textConfirm("CONFIRM")
                .textCancel("CANCEL")
                .btnTextSize(16)
                .viewTextSize(22)
                .colorCancel(activity.getResources().getColor(R.color.colorLightGrey))
                .colorConfirm(activity.getResources().getColor(R.color.colorAccent))
                .minYear(1950)
                .maxYear(2011)
                .dateChose(pickedTime)
                .build();

        pickerPopWin.showPopWin(activity);
        pickerPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DatePickerPopWin.clearDim();
            }
        });
    }


    public static Bitmap convertToBitmap(Activity activity, String filename) {
        try {
            FileInputStream inputStream = activity.openFileInput(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface IDateCallback {
       public void getDate(String date);
    }

    public static String convertedDate(String givenTime) {
        String initialPattern = "MM/dd/yyyy";
        String requiredPattern = "MMMM dd, yyyy";
        String outputDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(initialPattern);
        Date date = null;
        try {
            date = dateFormat.parse(givenTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(requiredPattern);
            outputDate = simpleDateFormat.format(date);
            return outputDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static String convertedDateTime(String givenTime) {
        String initialPattern = "MM/dd/yyyy";
        String requiredPattern = "E, MMM dd, yyyy . h:mm a";
        String outputDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(initialPattern);
        Date date = null;
        try {
            date = dateFormat.parse(givenTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(requiredPattern);
            outputDate = simpleDateFormat.format(date);
            return outputDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, boolean addToStack, String tag){
        if(activity == null) return;

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if(addToStack) transaction.addToBackStack(tag);
        transaction.replace(R.id.frame_root,fragment,tag).commit();
    }

    public static void enableButton(@NonNull Button button, boolean enable){
        button.setAlpha(enable ? 1.0f : 0.5f);
        button.setClickable(enable);
        button.setEnabled(enable);
    }

    public static Uri getImageUri(Context mContext, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), photo, "", null);
        return Uri.parse(path);
    }

    public static void showHideView(@NonNull View view, boolean show){
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public static void loadProfile(String profileUrl, @NonNull ImageView imageView) {
        Picasso.get().load(profileUrl)
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .into(imageView);
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            if (Objects.requireNonNull(inputMethodManager).isActive()) {
                inputMethodManager.hideSoftInputFromWindow(
                        Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
            }
        }catch (Exception ignored) {}
    }
}

package com.communityx.utils;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.communityx.R;

import java.util.Calendar;

public class Utils {

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

   /* public static String convertedDate(String givenTime) {
        String initialPattern = "MM-dd-yyyy";
        String requiredPattern = "h:mm a";
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
    }*/

    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, boolean addToStack, String tag){
        if(activity == null) return;

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if(addToStack) transaction.addToBackStack(tag);
        transaction.replace(R.id.frame_root,fragment,tag).commit();
    }

    public static void enableButton(@NonNull Button button, boolean enable){
        button.setAlpha(enable ? 1.0f : 0.5f);
        button.setClickable(enable);
    }
}

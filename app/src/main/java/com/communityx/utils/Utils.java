package com.communityx.utils;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

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
}

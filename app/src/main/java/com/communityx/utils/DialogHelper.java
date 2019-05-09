package com.communityx.utils;

import android.app.Dialog;
import android.content.Context;
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
}

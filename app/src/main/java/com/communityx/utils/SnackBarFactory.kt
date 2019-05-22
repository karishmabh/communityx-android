package com.communityx.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView
import com.communityx.R

object SnackBarFactory {

    fun createSnackBar(context: Context, view: View, message: String): Snackbar {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val sbView = snackbar.view
        val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(context.resources.getColor(R.color.colorWhite))
        snackbar.show()
        return snackbar
    }

    fun createSnackBarIndefinite(context: Context, view: View, message: String): Snackbar {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.show()

        return snackbar
    }

    fun createSnackBarMultiLine(context: Context, view: View, message: String): Snackbar {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackBarTextView = snackbar.view.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        snackBarTextView.maxLines = 999
        snackbar.show()

        return snackbar
    }
}
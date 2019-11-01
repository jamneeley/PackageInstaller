package com.jamesneeley.packageinstaller.misc

import android.app.AlertDialog
import android.content.Context
import com.jamesneeley.packageinstaller.R

fun Context.showDismissAlertWith(title: String, message: String) {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
        .setCancelable(false)
        .setNegativeButton(getString(R.string.dismiss)) { dialog, _ -> dialog.cancel() }

    val alert = dialogBuilder.create()
    alert.setTitle(title)
    alert.show()
}
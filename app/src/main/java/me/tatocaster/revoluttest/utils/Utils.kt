package me.tatocaster.revoluttest.utils

import android.app.Activity
import android.widget.EditText
import com.tapadoo.alerter.Alerter
import me.tatocaster.revoluttest.R


fun showErrorAlert(activity: Activity, title: String, message: String) {
    Alerter.create(activity)
            .setTitle(title)
            .setText(message)
            .setBackgroundColorRes(R.color.errorBackground)
            .show()
}

fun showSuccessAlert(activity: Activity, message: String) {
    Alerter.create(activity)
            .setText(message)
            .setBackgroundColorRes(R.color.successBackground)
            .show()
}

fun EditText.setTextAndSelect(text: CharSequence) {
    setText(text)
    setSelection(text.length)
}
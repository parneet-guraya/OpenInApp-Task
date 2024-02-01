package com.example.openinapptask.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.openinapptask.R
import com.google.android.material.snackbar.Snackbar

fun Int.toDrawable(resources: Resources): Drawable? {
    return ResourcesCompat.getDrawable(resources, this, null)
}

fun Int.toColor(resources: Resources): Int {
    return ResourcesCompat.getColor(resources, this, null)
}

object ViewUtils {
    fun copyTextToClipboard(text: String, context: Context) {
        val clipboard =
            ContextCompat.getSystemService(context, ClipboardManager::class.java)
        clipboard?.let {
            val clip: ClipData = ClipData.newPlainText("Copied:", text)
            it.setPrimaryClip(clip)
        }
    }

    fun toggleButtonStyle(
        resources: Resources,
        newButton: Button,
        currentlySelected: Button,
        activeColor: Int,
        activeTextColor: Int = R.color.white,
        inActiveTextColor: Int = R.color.inactive_text_color,
        inActiveColor: Int = android.R.color.transparent
    ) {
        newButton.setBackgroundColor(activeColor.toColor(resources))
        newButton.setTextColor(activeTextColor.toColor(resources))

        if (newButton != currentlySelected) {
            currentlySelected.setBackgroundColor(inActiveColor.toColor(resources))
            currentlySelected.setTextColor(inActiveTextColor.toColor(resources))
        }
    }

    fun showSnackBar(view: View, text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, text, duration).show()
    }
}
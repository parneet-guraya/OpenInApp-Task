package com.example.openinapptask.presentation

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import com.example.openinapptask.R

fun Int.toDrawable(resources: Resources): Drawable? {
    return ResourcesCompat.getDrawable(resources, this, null)
}

fun Int.toColor(resources: Resources): Int {
    return ResourcesCompat.getColor(resources, this, null)
}

fun Button.updateButtonStyle(isSelected: Boolean) {
    setBackgroundColor(
        if (isSelected) R.color.primary.toColor(resources) else android.R.color.transparent.toColor(
            resources
        )
    )
    setTextColor(
        if (isSelected) R.color.white.toColor(resources) else R.color.inactive_text_color.toColor(
            resources
        )
    )
}

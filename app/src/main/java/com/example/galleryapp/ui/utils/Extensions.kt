package com.example.galleryapp.ui.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: String) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Int.convertWithK(): String {
    return if (this > 1000) {
        val decimalValue = this % 1000
        val formattedDecimal = if (decimalValue > 0) {
            String.format("%.3f", decimalValue / 1000.0).removeSuffix("0").removeSuffix(".")
        } else {
            ""
        }
        "${this / 1000}$formattedDecimal" + "K"
    } else {
        this.toString()
    }
}

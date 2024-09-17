package com.inderjeet.notynote.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

// Extension function to show Toast easily
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, duration)
}
package com.example.coremodule.data

import android.content.res.Resources

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

class ConfigRepository {
}
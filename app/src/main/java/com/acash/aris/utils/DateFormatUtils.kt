package com.acash.aris.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Date.getTimeAgo():String {
    val now = Date()
    val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - this.time)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - this.time)
    val hours = TimeUnit.MILLISECONDS.toHours(now.time - this.time)

    return when {
        seconds < 60 -> {
            "Few Seconds ago"
        }

        minutes < 60 -> {
            "$minutes minutes ago"
        }

        hours < 24 -> {
            "$hours hours ${minutes % 60} minutes ago"
        }

        else -> {
            SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US).format(this)
        }
    }
}
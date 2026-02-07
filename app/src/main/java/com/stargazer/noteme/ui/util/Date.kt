package com.stargazer.noteme.ui.util

fun formatLongToDate(time: Long): String {
    val date = java.util.Date(time)
    val format = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
    return format.format(date)
}
package com.example.xpenses

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CommonUtils @Inject constructor(@ApplicationContext val context: Context){

    fun getToday(): String{
        return getFormatDate(Calendar.getInstance().time)
    }

    fun getFormatDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(date)
    }

    fun hideKeyboard(view:View){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

package com.example.search

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
}
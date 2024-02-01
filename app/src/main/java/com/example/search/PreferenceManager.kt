package com.example.search

import android.content.Context

object PreferenceManager {
    fun saveData (context: Context, query: String) {
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        pref.edit().putString("name", query).apply()
    }

    fun loadData (context: Context): String? {
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return pref.getString("name", null)
    }
}
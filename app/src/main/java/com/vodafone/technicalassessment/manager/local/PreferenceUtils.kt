package com.vodafone.technicalassessment.manager.local

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

class PreferenceUtils @Inject constructor(private val sharedPreferences: SharedPreferences) {

    inner class ConvertType<T> {
        private fun getSavedData(key: String): String? {
            return with(sharedPreferences) {
                getString(key, null)
            }
        }

        fun convert(key: String, type: Class<T>): T? {
            return Gson().fromJson(getSavedData(key), type)
        }
    }

    fun saveData(key: String, value: Any?) {
        with(sharedPreferences.edit()) {
            putString(key, Gson().toJson(value))
            apply()
        }
    }

    fun clearUserData() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}
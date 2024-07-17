package com.student.diary.util

import android.content.Context
import android.content.SharedPreferences


/**
 * Helper class to manage SharedPreferences operations.
 *
 * @property context The context used to access the SharedPreferences.
 */
class SharedPreferencesHelper(context: Context) {

    companion object {
        private const val PREF_NAME = "diary" // SharedPreferences file name
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /**
     * Stores a string value in SharedPreferences with the specified key.
     *
     * @param key The key used to store the value.
     * @param value The string value to be stored.
     */
    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Retrieves a string value from SharedPreferences for the specified key.
     *
     * @param key The key used to retrieve the value.
     * @return The string value associated with the key, or null if not found.
     */
    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    /**
     * Removes the value associated with the specified key from SharedPreferences.
     *
     * @param key The key of the value to be removed.
     */
    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * Clears all data stored in SharedPreferences.
     */
    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}

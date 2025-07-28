
package com.example.fiulostandfound.data

import android.content.Context

object UserPrefs {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_TOKEN  = "jwt_token"

    fun saveToken(ctx: Context, token: String) {
        ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    fun getToken(ctx: Context): String? {
        return ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_TOKEN, null)
    }


}
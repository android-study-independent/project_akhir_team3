package com.example.finalproject_chilicare.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.example.finalproject_chilicare.ui.home.forum.DetailPostForumActivity
import com.example.finalproject_chilicare.ui.home.forum.EditPostForumActivity
import com.example.finalproject_chilicare.ui.home.forum.ForumActivity
import com.example.finalproject_chilicare.ui.home.forum.NewPostForumActivity
import com.example.finalproject_chilicare.ui.lms.DetailLMSActivity
import com.example.finalproject_chilicare.ui.lms.MateriLMSActivity
import com.example.finalproject_chilicare.ui.login.LoginActivity
import com.example.finalproject_chilicare.ui.onboarding.OnboardingActivity

object PreferencesHelper {
    private const val PREF_NAME = "chilicare_preference"

    const val KEY_IS_LOGIN = "is_login"
    const val KEY_TOKEN = "token"
    const val KEY_FULLNAME = "fullname"
    const val KEY_EMAIL = "email"
    const val KEY_TOKEN_FILE = "prefs_token_file"
    const val KEY_LOGGED_IN = "onboarding_to_login"
    const val KEY_REGIST_IN = "onboarding_to_regist"
    const val IMAGE_REQUEST = 102
    const val KEY_ID = 81

    fun defaultPrefs(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun customPrefs(context: LoginActivity, name: String = PREF_NAME): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customPrefsHome(context: HomeActivity, name: String = PREF_NAME): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customPrefOnboarding(context: OnboardingActivity, name: String = PREF_NAME): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customPrefForum (context: ForumActivity, name: String = PREF_NAME) : SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customAddForum (context: NewPostForumActivity, name: String = PREF_NAME) : SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customEditForum (context: EditPostForumActivity, name: String = PREF_NAME) : SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customDetailForum (context: DetailPostForumActivity, name: String = PREF_NAME) : SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customMateriLms (context: MateriLMSActivity, name: String = PREF_NAME) : SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customDetailMateriLms (context: DetailLMSActivity, name: String = PREF_NAME) : SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    fun customForumHome (context: Context, name: String = PREF_NAME) : SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customDeleteForum (context: NewPostForumActivity, name: String = PREF_NAME) : SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customListMateri (context: MateriLMSActivity, name: String = PREF_NAME) : SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }



    /**
     * puts a value for the given [key].
     */
    operator fun SharedPreferences.set(key: String, value: Any?) = when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

    /**
     * finds a preference based on the given [key].
     * [T] is the type of value
     * @param defaultValue optional defaultValue - will take a default defaultValue if it is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T = when (T::class) {
        String::class -> getString(key, defaultValue as? String ?: "") as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}
package rekayasaagromarin.ews3swj.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(val context: Context?) {

    private val preferences: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, 0)
    private val editor: SharedPreferences.Editor? = preferences?.edit()

    fun setLogin(isLogin: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }

    fun setId(id: Int) {
        editor?.putInt(ID_USER, id)
        editor?.commit()
    }

    fun setName(name : String){
        editor?.putString(EMAIL, name)
        editor?.commit()
    }

    fun setEmail(email : String){
        editor?.putString(EMAIL, email)
        editor?.commit()
    }

    fun setImage(image : String){
        editor?.putString(EMAIL, image)
        editor?.commit()
    }

    fun isLogin(): Boolean? {
        return preferences?.getBoolean(IS_LOGIN, false)
    }

    fun getId(): Int? {
        return preferences?.getInt(ID_USER, 0)
    }

    fun getName(): String? {
        return preferences?.getString(NAME, "")
    }

    fun getEmail(): String? {
        return preferences?.getString(EMAIL, "")
    }

    fun getImage(): String? {
        return preferences?.getString(IMAGE, "")
    }

    fun removeData() {
        editor?.clear()
        editor?.commit()
    }

    companion object {
        private const val PREF_NAME = "SharedPreferences"
        private const val IS_LOGIN = "isLogin"
        private const val ID_USER = "idUser"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val IMAGE = "image"
    }
}
package rekayasaagromarin.ews3swj.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.AuthUser
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _authUser = MutableLiveData<AuthUser>()
    val authUser: LiveData<AuthUser> = _authUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun connectUser(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(email, password)
        client.enqueue(object : Callback<AuthUser> {
            override fun onResponse(call: Call<AuthUser>, response: Response<AuthUser>) {
                val loginResponse = response.body()
                if (response.isSuccessful) {
                    _authUser.value = loginResponse!!
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<AuthUser>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
            }

        })
    }
}
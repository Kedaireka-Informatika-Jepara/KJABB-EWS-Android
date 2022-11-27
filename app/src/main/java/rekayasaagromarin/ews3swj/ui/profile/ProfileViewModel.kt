package rekayasaagromarin.ews3swj.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val user = MutableLiveData<User>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _image = MutableLiveData<String>()
    val image: LiveData<String> = _image

    fun setUser(id: Int) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUser(id)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.size!! > 0) {
                        user.value = response.body()?.get(0)
                    } else {
                        user.value = User()
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }

        })
    }

    fun getUser(): LiveData<User> {
        return user
    }

    fun uploadPhoto(email: RequestBody, image: MultipartBody.Part) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().uploadPhoto(email, image)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }
}
package rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActionViewModel : ViewModel() {
    private val user = MutableLiveData<User>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _deleteMessage = MutableLiveData<String>()
    val deleteMessage: LiveData<String> = _deleteMessage

    private val _activateMessage = MutableLiveData<String>()
    val activateMessage: LiveData<String> = _activateMessage

    private val _deactivateMessage = MutableLiveData<String>()
    val deactivateMessage: LiveData<String> = _deactivateMessage

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun setUser(id: Int) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUser(id)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
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

    fun deleteUser(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().deleteUser(id)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _deleteMessage.value = response.body()?.message
                    _isDelete.value = true
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isDelete.value = false
                _deleteMessage.value = t.message
            }
        })
    }

    fun activateUser(id: Int) {
        val client = ApiConfig.getApiService().activateUser(id)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _activateMessage.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _activateMessage.value = t.message
            }
        })
    }

    fun deactivateUser(id: Int) {
        val client = ApiConfig.getApiService().deactivateUser(id)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _deactivateMessage.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _deactivateMessage.value = t.message
            }
        })
    }
}
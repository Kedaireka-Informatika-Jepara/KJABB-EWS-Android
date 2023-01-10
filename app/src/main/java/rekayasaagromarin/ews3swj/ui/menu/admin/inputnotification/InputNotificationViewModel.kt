package rekayasaagromarin.ews3swj.ui.menu.admin.inputnotification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.Notification
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputNotificationViewModel : ViewModel() {
    private val listDataUser = MutableLiveData<ArrayList<User>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> = _isSuccess

    fun setReceiver() {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getListDataUser()
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    _isSuccess.value = response.code()
                    listDataUser.postValue(response.body() as ArrayList<User>)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _isSuccess.value = 0
                _message.value = t.message
            }
        })
    }

    fun getListUser(): LiveData<ArrayList<User>> {
        return listDataUser
    }

    fun sendNotification(notification: Notification) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().sendNotification(
            notification.senderId,
            notification.receiverId,
            notification.title,
            notification.message,
            notification.dateCreated,
            notification.isRead
        )
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isSuccess.value = response.body()?.status
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
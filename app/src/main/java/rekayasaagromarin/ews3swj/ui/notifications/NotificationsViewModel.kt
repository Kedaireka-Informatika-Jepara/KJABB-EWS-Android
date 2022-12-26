package rekayasaagromarin.ews3swj.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.Notification
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsViewModel: ViewModel() {
    private var listNotification = MutableLiveData<ArrayList<Notification>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> = _isSuccess

    fun setListNotification(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListNotification()
        client.enqueue(object : Callback<List<Notification>> {
            override fun onResponse(
                call: Call<List<Notification>>,
                response: Response<List<Notification>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isSuccess.value = 1
                    _message.value = ""
                    listNotification.postValue(response.body() as ArrayList<Notification>)
                }
            }

            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = 0
                _message.value = t.message
            }
        })
    }

    fun getListNotification(): LiveData<ArrayList<Notification>> {
        return listNotification
    }
}
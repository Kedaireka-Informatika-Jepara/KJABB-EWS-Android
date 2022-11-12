package rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.Result
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultHistoryViewModel : ViewModel() {
    private val result = MutableLiveData<Result>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun setResultStation(stationId: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getResultStation(stationId)
        client.enqueue(object : Callback<List<Result>> {
            override fun onResponse(
                call: Call<List<Result>>,
                response: Response<List<Result>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    result.postValue(response.body()?.get(0))
                }
            }

            override fun onFailure(call: Call<List<Result>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun getResultStation(): LiveData<Result> {
        return result
    }
}
package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.MainAbiotic
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAbioticActionViewModel : ViewModel() {
    private val mainAbiotic = MutableLiveData<MainAbiotic>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _deleteMessage = MutableLiveData<String>()
    val deleteMessage: LiveData<String> = _deleteMessage

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    fun setMainAbiotic(id: Int) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getMainAbiotic(id)
        client.enqueue(object : Callback<List<MainAbiotic>> {
            override fun onResponse(
                call: Call<List<MainAbiotic>>,
                response: Response<List<MainAbiotic>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    if (response.body()?.size!! > 0) {
                        mainAbiotic.value = response.body()?.get(0)
                    } else {
                        mainAbiotic.value = MainAbiotic()
                    }
                }
            }

            override fun onFailure(call: Call<List<MainAbiotic>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }

        })
    }

    fun getMainAbiotic(): LiveData<MainAbiotic> {
        return mainAbiotic
    }

    fun deleteMainAbiotic(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().deleteMainAbiotic(id)
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
}
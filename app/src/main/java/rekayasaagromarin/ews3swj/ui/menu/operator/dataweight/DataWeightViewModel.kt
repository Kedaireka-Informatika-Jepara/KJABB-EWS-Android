package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.ParameterResponse
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataWeightViewModel : ViewModel() {
    private val listIndexBiotic = MutableLiveData<ArrayList<ParameterResponse>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun setIndexBiotic() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getListIndexBiotic()
        client.enqueue(object : Callback<List<ParameterResponse>> {
            override fun onResponse(
                call: Call<List<ParameterResponse>>,
                response: Response<List<ParameterResponse>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    listIndexBiotic.postValue(response.body() as ArrayList<ParameterResponse>)
                }
            }

            override fun onFailure(call: Call<List<ParameterResponse>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun setAdditionalAbiotic() {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getListAdditionalAbiotic()
        client.enqueue(object : Callback<List<ParameterResponse>> {
            override fun onResponse(
                call: Call<List<ParameterResponse>>,
                response: Response<List<ParameterResponse>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    listIndexBiotic.postValue(response.body() as ArrayList<ParameterResponse>)
                }
            }

            override fun onFailure(call: Call<List<ParameterResponse>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun setMainBiotic() {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getListMainBiotic()
        client.enqueue(object : Callback<List<ParameterResponse>> {
            override fun onResponse(
                call: Call<List<ParameterResponse>>,
                response: Response<List<ParameterResponse>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    listIndexBiotic.postValue(response.body() as ArrayList<ParameterResponse>)
                }
            }

            override fun onFailure(call: Call<List<ParameterResponse>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun getListIndexBiotic(): LiveData<ArrayList<ParameterResponse>> {
        return listIndexBiotic
    }
}
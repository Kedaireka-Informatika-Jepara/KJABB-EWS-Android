package rekayasaagromarin.ews3swj.ui.parameter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.GeographicalZone
import rekayasaagromarin.ews3swj.model.Parameter
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.model.TypeOfWater
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddParameterViewModel: ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> = _isSuccess

    fun addGeographicalZone(geographicalZone: GeographicalZone){
        _isLoading.value = true
        val client = ApiConfig.getApiService().addGeographicalZone(geographicalZone.zone)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _message.value = response.body()?.message
                    _isLoading.value = false
                    _isSuccess.value = response.body()?.status
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }

        })
    }

    fun addTypeOfWater(typeOfWater: TypeOfWater){
        _isLoading.value = true
        val client = ApiConfig.getApiService().addTypeWater(typeOfWater.water)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _message.value = response.body()?.message
                    _isLoading.value = false
                    _isSuccess.value = response.body()?.status
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }

        })
    }

    fun addParameter(parameter: Parameter){
        _isLoading.value = true
        val client = ApiConfig.getApiService().addParameter(
            parameter.name,
            parameter.type,
            parameter.description
        )
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val result = response.body()
                    if (result != null) {
                        _isSuccess.value = result.status
                        _message.value = result.message
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
            }
        })
    }
}
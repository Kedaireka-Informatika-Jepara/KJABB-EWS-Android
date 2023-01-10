package rekayasaagromarin.ews3swj.ui.menu.main.parameter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.Parameter
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParameterViewModel : ViewModel() {
    private val listParameter = MutableLiveData<ArrayList<Parameter>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    fun setListParameter(){
        _isLoading.value = true
        val parameter = ArrayList<Parameter>()
        val client = ApiConfig.getApiService().getParameter()
        client.enqueue(object : Callback<List<Parameter>> {
            override fun onResponse(
                call: Call<List<Parameter>>,
                response: Response<List<Parameter>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val result = response.body()
                    if (result != null) {
                        parameter.addAll(result)
                        listParameter.postValue(parameter)
                    }
                }
            }

            override fun onFailure(call: Call<List<Parameter>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
            }
        })
    }

    fun getListParameter(): LiveData<ArrayList<Parameter>> {
        return listParameter
    }

    fun deleteParameter(id: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().deleteParameter(id)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(
                call: Call<ResponseApi>,
                response: Response<ResponseApi>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val result = response.body()
                    if (result != null) {
                        _message.value = result.message
                        _isLoading.value = false
                        _isDeleted.value = true
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
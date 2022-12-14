package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.IndexBiotic
import rekayasaagromarin.ews3swj.model.Parameter
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddIndexBioticViewModel : ViewModel() {
    private var listParamIndexBiotic = MutableLiveData<ArrayList<String>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> = _isSuccess

    fun setParamIndexBiotic(){
        _isLoading.value = true
        val paramIndexBiotic = ArrayList<String>()
        val client = ApiConfig.getApiService().getParameter()
        client.enqueue(object : Callback<List<Parameter>> {
            override fun onResponse(
                call: Call<List<Parameter>>,
                response: Response<List<Parameter>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val roleList = response.body()
                    if (roleList != null) {
                        roleList.forEach {
                            if (it.type == 1){
                                paramIndexBiotic.add(it.name)
                            }
                        }
                        listParamIndexBiotic.postValue(paramIndexBiotic)
                    }
                }
            }

            override fun onFailure(call: Call<List<Parameter>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
            }
        })
    }

    fun getParamIndexBiotic(): LiveData<ArrayList<String>> {
        return listParamIndexBiotic
    }

    fun addIndexBiotic(indexBiotic: IndexBiotic) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addIndexBiotic(
            indexBiotic.name,
            indexBiotic.initialValue,
            indexBiotic.finalValue,
            indexBiotic.weight
        )
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
}
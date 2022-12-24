package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.AdditionalAbiotic
import rekayasaagromarin.ews3swj.model.Parameter
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAdditionalAbioticViewModel : ViewModel() {
    private var listParamAdditionalAbiotic = MutableLiveData<ArrayList<String>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> = _isSuccess

    fun setParamAdditionalAbiotic(){
        _isLoading.value = true
        val paramAdditionalAbiotic = ArrayList<String>()
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
                            if (it.type == 5){
                                paramAdditionalAbiotic.add(it.name)
                            }
                        }
                        listParamAdditionalAbiotic.postValue(paramAdditionalAbiotic)
                    }
                }
            }

            override fun onFailure(call: Call<List<Parameter>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
            }
        })
    }

    fun getParamAdditionalAbiotic(): LiveData<ArrayList<String>> {
        return listParamAdditionalAbiotic
    }

    fun addAdditionalAbiotic(additionalAbiotic: AdditionalAbiotic) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addAdditionalAbiotic(
            additionalAbiotic.name,
            additionalAbiotic.initialValue,
            additionalAbiotic.finalValue,
            additionalAbiotic.weight
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
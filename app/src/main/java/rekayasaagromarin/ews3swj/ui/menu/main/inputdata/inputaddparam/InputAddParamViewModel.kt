package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputaddparam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.FamilyBiotic
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputAddParamViewModel : ViewModel() {
    private val listFamilyBiotic = MutableLiveData<ArrayList<FamilyBiotic>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun setFamilyBiotic() {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getListFamilyBiotic()
        client.enqueue(object : Callback<List<FamilyBiotic>> {
            override fun onResponse(
                call: Call<List<FamilyBiotic>>,
                response: Response<List<FamilyBiotic>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    listFamilyBiotic.postValue(response.body() as ArrayList<FamilyBiotic>)
                }
            }

            override fun onFailure(call: Call<List<FamilyBiotic>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun getListFamilyBiotic(): LiveData<ArrayList<FamilyBiotic>> {
        return listFamilyBiotic
    }
}
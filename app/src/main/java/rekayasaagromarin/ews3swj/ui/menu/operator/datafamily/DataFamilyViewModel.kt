package rekayasaagromarin.ews3swj.ui.menu.operator.datafamily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.FamilyBiotic
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataFamilyViewModel : ViewModel() {
    private val listFamilyBiotic = MutableLiveData<ArrayList<FamilyBiotic>>()

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

    fun deleteFamilyBiotic(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().deleteFamilyBiotic(id)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isDelete.value = true
                    _deleteMessage.value = response.body()?.message
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
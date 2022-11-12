package rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.FamilyBiotic
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditFamilyBioticViewModel : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> = _isSuccess

    fun editFamilyBiotic(familyBiotic: FamilyBiotic) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().editFamilyBiotic(
            familyBiotic.id,
            familyBiotic.family,
            familyBiotic.weight
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
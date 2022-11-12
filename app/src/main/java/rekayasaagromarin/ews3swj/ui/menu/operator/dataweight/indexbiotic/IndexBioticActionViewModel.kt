package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.IndexBiotic
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndexBioticActionViewModel : ViewModel() {
    private val indexBiotic = MutableLiveData<IndexBiotic>()

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

    fun setIndexBiotic(id: Int) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getIndexBiotic(id)
        client.enqueue(object : Callback<List<IndexBiotic>> {
            override fun onResponse(
                call: Call<List<IndexBiotic>>,
                response: Response<List<IndexBiotic>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    if (response.body()?.size!! > 0) {
                        indexBiotic.value = response.body()?.get(0)
                    } else {
                        indexBiotic.value = IndexBiotic()
                    }
                }
            }

            override fun onFailure(call: Call<List<IndexBiotic>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }

        })
    }

    fun deleteIndexBiotic(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().deleteIndexBiotic(id)
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

    fun getIndexBiotic(): LiveData<IndexBiotic> {
        return indexBiotic
    }
}
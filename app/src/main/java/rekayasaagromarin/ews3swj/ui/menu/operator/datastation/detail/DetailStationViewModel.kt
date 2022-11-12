package rekayasaagromarin.ews3swj.ui.menu.operator.datastation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.*
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStationViewModel : ViewModel() {
    private val listSpecies = MutableLiveData<ArrayList<Species>>()
    private val indexAdd = MutableLiveData<IndexAddStation>()
    private val mainAbiotic = MutableLiveData<MainAbioticStation>()
    private val result = MutableLiveData<Result>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isDeleted = MutableLiveData<Int>()
    val isDeleted: LiveData<Int> = _isDeleted

    fun setDataSpecies(stationId: String) {
        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getDataSpecies(stationId)
        client.enqueue(object : Callback<List<Species>> {
            override fun onResponse(call: Call<List<Species>>, response: Response<List<Species>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    listSpecies.postValue(response.body() as ArrayList<Species>)
                }
            }

            override fun onFailure(call: Call<List<Species>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun setIndexAddStation(stationId: String) {
        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getIndexAddStation(stationId)
        client.enqueue(object : Callback<List<IndexAddStation>> {
            override fun onResponse(
                call: Call<List<IndexAddStation>>,
                response: Response<List<IndexAddStation>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    indexAdd.postValue(response.body()?.get(0))
                }
            }

            override fun onFailure(call: Call<List<IndexAddStation>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun setMainAbioticStation(stationId: String) {
        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getMainAbioticStation(stationId)
        client.enqueue(object : Callback<List<MainAbioticStation>> {
            override fun onResponse(
                call: Call<List<MainAbioticStation>>,
                response: Response<List<MainAbioticStation>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    mainAbiotic.postValue(response.body()?.get(0))
                }
            }

            override fun onFailure(call: Call<List<MainAbioticStation>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun setResultStation(stationId: String) {
        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getResultStation(stationId)
        client.enqueue(object : Callback<List<Result>> {
            override fun onResponse(
                call: Call<List<Result>>,
                response: Response<List<Result>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    result.postValue(response.body()?.get(0))
                }
            }

            override fun onFailure(call: Call<List<Result>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun deleteStation(stationId: String){
        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().deleteDataStation(stationId)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(
                call: Call<ResponseApi>,
                response: Response<ResponseApi>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = response.body()?.message
                    _isDeleted.value = response.body()?.status
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun getListSpecies(): LiveData<ArrayList<Species>> {
        return listSpecies
    }

    fun getIndexAddStation(): LiveData<IndexAddStation> {
        return indexAdd
    }

    fun getMainAbioticStation(): LiveData<MainAbioticStation> {
        return mainAbiotic
    }

    fun getResultStation(): LiveData<Result> {
        return result
    }
}
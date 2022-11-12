package rekayasaagromarin.ews3swj.ui.menu.main.inputhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.DataStation
import rekayasaagromarin.ews3swj.model.Species
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputHistoryViewModel : ViewModel() {
    private val listDataStation = MutableLiveData<ArrayList<DataStation>>()
    private val listSpecies = MutableLiveData<ArrayList<Species>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun setListTaxaIndicator(userId: Int) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getTaxaIndicatorById(userId)
        client.enqueue(object : Callback<List<Species>> {
            override fun onResponse(
                call: Call<List<Species>>,
                response: Response<List<Species>>
            ) {
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


    fun setListDataStation(userId: Int) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getListDataStationById(userId)
        client.enqueue(object : Callback<List<DataStation>> {
            override fun onResponse(
                call: Call<List<DataStation>>,
                response: Response<List<DataStation>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    listDataStation.postValue(response.body() as ArrayList<DataStation>)
                }
            }

            override fun onFailure(call: Call<List<DataStation>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun getListTaxaIndicator(): LiveData<ArrayList<Species>> {
        return listSpecies
    }

    fun getListStation(): LiveData<ArrayList<DataStation>> {
        return listDataStation
    }
}
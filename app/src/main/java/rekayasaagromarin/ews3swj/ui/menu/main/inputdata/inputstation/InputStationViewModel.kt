package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.GeographicalZone
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.model.TypeOfWater
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputStationViewModel : ViewModel() {

    private var listGeoZone = MutableLiveData<ArrayList<String>>()
    private var listWater = MutableLiveData<ArrayList<String>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isAvailableStationId = MutableLiveData<ResponseApi>()
    val isAvailableStationId: LiveData<ResponseApi> = _isAvailableStationId


    fun setGeographicalZone() {
        _isLoading.value = true
        _isError.value = false
        val geographicalZone = ArrayList<String>()
        val client = ApiConfig.getApiService().getGeographicalZone()
        client.enqueue(object : Callback<List<GeographicalZone>> {
            override fun onResponse(
                call: Call<List<GeographicalZone>>,
                response: Response<List<GeographicalZone>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    val roleList = response.body()
                    if (roleList != null) {
                        roleList.forEach {
                            geographicalZone.add(it.name)
                        }
                        geographicalZone.removeLast()
                        listGeoZone.postValue(geographicalZone)
                    }
                }
            }

            override fun onFailure(call: Call<List<GeographicalZone>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
                _isError.value = true
            }

        })
    }

    fun getGeographicalZone(): LiveData<ArrayList<String>> {
        return listGeoZone
    }

    fun setTypeOfWater() {
        _isLoading.value = true
        val typeOfWater = ArrayList<String>()
        val client = ApiConfig.getApiService().getTypeOfWater()
        client.enqueue(object : Callback<List<TypeOfWater>> {
            override fun onResponse(
                call: Call<List<TypeOfWater>>,
                response: Response<List<TypeOfWater>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val roleList = response.body()
                    if (roleList != null) {
                        roleList.forEach {
                            typeOfWater.add(it.name)
                        }
                        typeOfWater.removeLast()
                        listWater.postValue(typeOfWater)
                    }
                }
            }

            override fun onFailure(call: Call<List<TypeOfWater>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
            }

        })
    }

    fun getTypeOfWater(): LiveData<ArrayList<String>> {
        return listWater
    }

    fun stationCheck(stationId: String) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().stationCheck(stationId)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _isAvailableStationId.value = response.body()
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
                _isError.value = true
            }

        })
    }
}
package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.*
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMainAbioticViewModel : ViewModel() {
    private var listParamMainAbiotic = MutableLiveData<ArrayList<String>>()
    private var listGeoZone = MutableLiveData<ArrayList<String>>()
    private var listWater = MutableLiveData<ArrayList<String>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> = _isSuccess

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun addMainAbiotic(mainAbiotic: MainAbiotic) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addMainAbiotic(
            mainAbiotic.name,
            mainAbiotic.geographicalZoneId,
            mainAbiotic.typeOfWaterId,
            mainAbiotic.initialValue,
            mainAbiotic.finalValue,
            mainAbiotic.weight
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

    fun setGeographicalZone() {
        _isLoading.value = true
        val geographicalZone = ArrayList<String>()
//        val client = ApiConfig.getApiService().getGeographicalZone()
//        client.enqueue(object : Callback<List<GeographicalZone>> {
//            override fun onResponse(
//                call: Call<List<GeographicalZone>>,
//                response: Response<List<GeographicalZone>>
//            ) {
//                if (response.isSuccessful) {
//                    _isLoading.value = false
//                    val roleList = response.body()
//                    if (roleList != null) {
//                        roleList.forEach {
//                            geographicalZone.add(it.zone)
//                        }
//                        listGeoZone.postValue(geographicalZone)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<GeographicalZone>>, t: Throwable) {
//                _message.value = t.message
//                _isLoading.value = false
//            }
//
//        })
        val client = ApiConfig.getApiService().getParameter()
        client.enqueue(object : Callback<List<Parameter>> {
            override fun onResponse(
                call: Call<List<Parameter>>,
                response: Response<List<Parameter>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    val roleList = response.body()
                    if (roleList != null) {
                        roleList.forEach {
                            if (it.type == 4) {
                                geographicalZone.add(it.name)
                            }
                        }
                        listGeoZone.postValue(geographicalZone)
                    }
                }
            }

            override fun onFailure(call: Call<List<Parameter>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
                _isError.value = true
            }

        })
    }

    fun setParamMainAbiotic(){
        _isLoading.value = true
        val paramMainAbiotic = ArrayList<String>()
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
                            if (it.type == 2){
                                paramMainAbiotic.add(it.name)
                            }
                        }
                        listParamMainAbiotic.postValue(paramMainAbiotic)
                    }
                }
            }

            override fun onFailure(call: Call<List<Parameter>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
            }
        })
    }

    fun getGeographicalZone(): LiveData<ArrayList<String>> {
        return listGeoZone
    }

    fun getParamMainAbiotic(): LiveData<ArrayList<String>> {
        return listParamMainAbiotic
    }

    fun setTypeOfWater() {
        _isLoading.value = true
        val typeOfWater = ArrayList<String>()
//        val client = ApiConfig.getApiService().getTypeOfWater()
//        client.enqueue(object : Callback<List<TypeOfWater>> {
//            override fun onResponse(
//                call: Call<List<TypeOfWater>>,
//                response: Response<List<TypeOfWater>>
//            ) {
//                if (response.isSuccessful) {
//                    _isLoading.value = false
//                    val roleList = response.body()
//                    if (roleList != null) {
//                        roleList.forEach {
//                            typeOfWater.add(it.water)
//                        }
//                        listWater.postValue(typeOfWater)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<TypeOfWater>>, t: Throwable) {
//                _message.value = t.message
//                _isLoading.value = false
//            }
//
//        })

        val client = ApiConfig.getApiService().getParameter()
        client.enqueue(object : Callback<List<Parameter>> {
            override fun onResponse(
                call: Call<List<Parameter>>,
                response: Response<List<Parameter>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    val roleList = response.body()
                    if (roleList != null) {
                        roleList.forEach {
                            if (it.type == 3) {
                                typeOfWater.add(it.name)
                            }
                        }
                        listWater.postValue(typeOfWater)
                    }
                }
            }

            override fun onFailure(call: Call<List<Parameter>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
                _isError.value = true
            }

        })
    }

    fun getTypeOfWater(): LiveData<ArrayList<String>> {
        return listWater
    }
}
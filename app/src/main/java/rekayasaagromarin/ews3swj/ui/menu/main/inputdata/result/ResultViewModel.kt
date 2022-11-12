package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.*
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel : ViewModel() {
    private val indexSum = MutableLiveData<CountResponse>()
    private val additionalSum = MutableLiveData<CountResponse>()
    private val mainSum = MutableLiveData<CountResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isSuccessIndexCount = MutableLiveData<Boolean>()
    val isSuccessIndexCount: LiveData<Boolean> = _isSuccessIndexCount

    private val _isSuccessAddCount = MutableLiveData<Boolean>()
    val isSuccessAddCount: LiveData<Boolean> = _isSuccessAddCount

    private val _isSuccessMainCount = MutableLiveData<Boolean>()

    private val _isSavedStation = MutableLiveData<Boolean>()
    val isSavedStation: LiveData<Boolean> = _isSavedStation

    private val _isSavedSpecies = MutableLiveData<Boolean>()
    val isSavedSpecies: LiveData<Boolean> = _isSavedSpecies

    private val _isDeletedSpecies = MutableLiveData<Boolean>()
    val isDeletedSpecies: LiveData<Boolean> = _isDeletedSpecies

    private val _isSavedIndexAdd = MutableLiveData<Boolean>()
    val isSavedIndexAdd: LiveData<Boolean> = _isSavedIndexAdd

    private val _isSavedMain = MutableLiveData<Boolean>()
    val isSavedMain: LiveData<Boolean> = _isSavedMain

    private val _isSavedResult = MutableLiveData<Boolean>()
    val isSavedResult: LiveData<Boolean> = _isSavedResult

    fun indexBioticCount(indexAddStation: IndexAddStation) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().indexBioticCount(
            indexAddStation.diversity,
            indexAddStation.dominance,
            indexAddStation.similarity,
            indexAddStation.numberSpecies,
            indexAddStation.taxaIndicator
        )

        client.enqueue(object : Callback<List<CountResponse>> {
            override fun onResponse(
                call: Call<List<CountResponse>>,
                response: Response<List<CountResponse>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _isSuccessIndexCount.value = true
                    indexSum.postValue(response.body()?.get(0))
                }
            }

            override fun onFailure(call: Call<List<CountResponse>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _isSuccessIndexCount.value = false
                _message.value = t.message
            }
        })
    }

    fun additionalAbioticCount(indexAddStation: IndexAddStation) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().additionalAbioticCount(
            indexAddStation.conductivity,
            indexAddStation.rationCn,
            indexAddStation.turbidity,
            indexAddStation.clay,
            indexAddStation.sand,
            indexAddStation.silt,
        )

        client.enqueue(object : Callback<List<CountResponse>> {
            override fun onResponse(
                call: Call<List<CountResponse>>,
                response: Response<List<CountResponse>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _isSuccessAddCount.value = true
                    additionalSum.postValue(response.body()?.get(0))
                }
            }

            override fun onFailure(call: Call<List<CountResponse>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _isSuccessIndexCount.value = false
                _message.value = t.message
            }

        })
    }

    fun mainAbioticCount(mainAbioticStation: MainAbioticStation) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().mainAbioticCount(
            mainAbioticStation.temperature,
            mainAbioticStation.salinity,
            mainAbioticStation.doParam,
            mainAbioticStation.ph,
            mainAbioticStation.typeOfWater,
            mainAbioticStation.geographicalZone
        )

        client.enqueue(object : Callback<List<CountResponse>> {
            override fun onResponse(
                call: Call<List<CountResponse>>,
                response: Response<List<CountResponse>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _isSuccessMainCount.value = true
                    mainSum.postValue(response.body()?.get(0))
                }
            }

            override fun onFailure(call: Call<List<CountResponse>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _isSuccessIndexCount.value = false
                _message.value = t.message
            }

        })
    }

    fun saveStation(station: Station) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().saveStation(
            stationId = station.stationId,
            geographicalZone = station.geographicalZoneId,
            typeOfWater = station.typeOfWaterId,
            userId = station.userId
        )

        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.status == 200) {
                        _isSavedStation.value = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = false
                _isSavedStation.value = false
                _message.value = t.message
            }

        })
    }

    fun editStation(station: Station) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().editStation(
            stationId = station.stationId,
            geographicalZone = station.geographicalZoneId,
            typeOfWater = station.typeOfWaterId,
        )

        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.status == 200) {
                        _isSavedStation.value = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = false
                _isSavedStation.value = false
                _message.value = t.message
            }

        })
    }

    fun saveSpecies(species: ArrayList<Species>) {
        _isLoading.value = true

        for (i in 0 until species.size) {
            val client = ApiConfig.getApiService().saveSpecies(
                family = species[i].family,
                species = species[i].species,
                abundance = species[i].abundance,
                taxaIndicator = species[i].taxaIndicator,
                userId = species[i].userId,
                stationId = species[i].stationId
            )

            client.enqueue(object : Callback<ResponseApi> {
                override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        _isError.value = false
                        if (response.body()?.status == 200 && i == species.size - 1) {
                            _isSavedSpecies.value = true
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                    _isLoading.value = false
                    _isError.value = false
                    _isSavedSpecies.value = false
                    _message.value = t.message
                }

            })
        }
    }

    fun deleteSpecies(species: ArrayList<Species>) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().deleteSpecies(species[0].stationId)

        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.status == 200) {
                        _isDeletedSpecies.value = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = false
                _isSavedSpecies.value = false
                _message.value = t.message
            }

        })
    }

    fun saveIndexAdd(indexAddStation: IndexAddStation) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().saveIndexAddStation(
            similarity = indexAddStation.similarity,
            dominance = indexAddStation.dominance,
            diversity = indexAddStation.diversity,
            totalAbundance = indexAddStation.totalAbundance,
            numberSpecies = indexAddStation.numberSpecies,
            taxaIndicator = indexAddStation.taxaIndicator,
            conductivity = indexAddStation.conductivity,
            ratioCn = indexAddStation.rationCn,
            turbidity = indexAddStation.turbidity,
            clay = indexAddStation.clay,
            sand = indexAddStation.sand,
            silt = indexAddStation.silt,
            stationId = indexAddStation.stationId,
            userId = indexAddStation.userId
        )

        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.status == 200) {
                        _isSavedIndexAdd.value = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = false
                _isSavedIndexAdd.value = false
                _message.value = t.message
            }

        })
    }

    fun editIndexAdd(indexAddStation: IndexAddStation) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().editIndexAddStation(
            similarity = indexAddStation.similarity,
            dominance = indexAddStation.dominance,
            diversity = indexAddStation.diversity,
            totalAbundance = indexAddStation.totalAbundance,
            numberSpecies = indexAddStation.numberSpecies,
            taxaIndicator = indexAddStation.taxaIndicator,
            conductivity = indexAddStation.conductivity,
            ratioCn = indexAddStation.rationCn,
            turbidity = indexAddStation.turbidity,
            clay = indexAddStation.clay,
            sand = indexAddStation.sand,
            silt = indexAddStation.silt,
            stationId = indexAddStation.stationId,
        )

        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.status == 200) {
                        _isSavedIndexAdd.value = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = false
                _isSavedIndexAdd.value = false
                _message.value = t.message
            }

        })
    }

    fun saveMain(mainAbioticStation: MainAbioticStation) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().saveMainStation(
            temperature = mainAbioticStation.temperature,
            salinity = mainAbioticStation.salinity,
            doParam = mainAbioticStation.doParam,
            ph = mainAbioticStation.ph,
            typeOfWater = mainAbioticStation.typeOfWater,
            geographicalZone = mainAbioticStation.geographicalZone,
            stationId = mainAbioticStation.stationId,
            userId = mainAbioticStation.userId,
        )

        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.status == 200) {
                        _isSavedMain.value = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = false
                _isSavedMain.value = false
                _message.value = t.message
            }

        })
    }

    fun editMain(mainAbioticStation: MainAbioticStation) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().editMainStation(
            temperature = mainAbioticStation.temperature,
            salinity = mainAbioticStation.salinity,
            doParam = mainAbioticStation.doParam,
            ph = mainAbioticStation.ph,
            typeOfWater = mainAbioticStation.typeOfWater,
            geographicalZone = mainAbioticStation.geographicalZone,
            stationId = mainAbioticStation.stationId,
        )

        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.status == 200) {
                        _isSavedMain.value = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = false
                _isSavedMain.value = false
                _message.value = t.message
            }

        })
    }

    fun saveResult(result: Result) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().saveResult(
            result = result.result,
            status = result.status,
            conclusion = result.conclusion,
            recommendation = result.recommendation,
            stationId = result.stationId,
            userId = result.userId
        )

        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.status == 200) {
                        _isSavedResult.value = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = false
                _isSavedResult.value = false
                _message.value = t.message
            }

        })
    }

    fun editResult(result: Result) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().editResult(
            result = result.result,
            status = result.status,
            conclusion = result.conclusion,
            recommendation = result.recommendation,
            stationId = result.stationId,
        )

        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    if (response.body()?.status == 200) {
                        _isSavedResult.value = true
                    }
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = false
                _isSavedResult.value = false
                _message.value = t.message
            }

        })
    }

    fun getIndexCount(): LiveData<CountResponse> {
        return indexSum
    }

    fun getAdditionalCount(): LiveData<CountResponse> {
        return additionalSum
    }

    fun getMainCount(): LiveData<CountResponse> {
        return mainSum
    }
}
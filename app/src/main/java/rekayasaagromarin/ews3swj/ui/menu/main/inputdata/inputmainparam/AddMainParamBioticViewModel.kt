package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.FamilyBiotic
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMainParamBioticViewModel : ViewModel() {
    private var listFamilyName = MutableLiveData<ArrayList<String>>()
    private var listFamilyWeight = MutableLiveData<ArrayList<Double>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun setFamily() {
        _isLoading.value = true
        _isError.value = false
        _isSuccess.value = false
        val familyName = ArrayList<String>()
        val familyWeight = ArrayList<Double>()
        val client = ApiConfig.getApiService().getListFamilyBiotic()
        client.enqueue(object : Callback<List<FamilyBiotic>> {
            override fun onResponse(
                call: Call<List<FamilyBiotic>>,
                response: Response<List<FamilyBiotic>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    val roleList = response.body()
                    if (roleList != null) {
                        roleList.forEach {
                            familyName.add(it.family)
                            familyWeight.add(it.weight)
                        }
                        listFamilyName.postValue(familyName)
                        listFamilyWeight.postValue(familyWeight)
                        _isSuccess.value = true
                    }
                }
            }

            override fun onFailure(call: Call<List<FamilyBiotic>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
                _isError.value = true
            }

        })
    }

    fun getFamilyName(): LiveData<ArrayList<String>> {
        return listFamilyName
    }

    fun getFamilyWeight(): LiveData<ArrayList<Double>> {
        return listFamilyWeight
    }
}
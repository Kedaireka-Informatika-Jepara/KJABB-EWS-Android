package rekayasaagromarin.ews3swj.ui.auth.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int> = _status

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun uploadPayment(emailUser: RequestBody, image: MultipartBody.Part){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().uploadPayment(emailUser, image)
        client.enqueue(object : Callback<ResponseApi>{
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }

        })
    }

    fun emailCheck(email : String){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().emailCheck(email)
        client.enqueue(object : Callback<ResponseApi>{
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    _isError.value = false
                    if(response.body()?.status == 1){
                        _status.value = response.body()?.status
                    }else{
                        _message.value = response.body()?.message
                        _status.value = response.body()?.status
                    }

                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }

        })
    }
}
package rekayasaagromarin.ews3swj.ui.menu.admin.datapayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.Payment
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataPaymentViewModel : ViewModel() {
    private val listPayment = MutableLiveData<ArrayList<Payment>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun setListPayment() {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getListPayment()
        client.enqueue(object : Callback<List<Payment>> {
            override fun onResponse(call: Call<List<Payment>>, response: Response<List<Payment>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    listPayment.postValue(response.body() as ArrayList<Payment>)
                }
            }

            override fun onFailure(call: Call<List<Payment>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }
        })
    }

    fun getListUser(): LiveData<ArrayList<Payment>> {
        return listPayment
    }
}
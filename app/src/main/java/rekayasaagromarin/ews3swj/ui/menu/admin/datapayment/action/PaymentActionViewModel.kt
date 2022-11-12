package rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.action

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.Payment
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActionViewModel : ViewModel() {
    private val payment = MutableLiveData<Payment>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _deleteMessage = MutableLiveData<String>()
    val deleteMessage: LiveData<String> = _deleteMessage

    private val _confirmMessage = MutableLiveData<String>()
    val confirmMessage: LiveData<String> = _confirmMessage

    private val _unconfirmMessage = MutableLiveData<String>()
    val unconfirmMessage: LiveData<String> = _unconfirmMessage

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun setPayment(id: Int) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getPayment(id)
        client.enqueue(object : Callback<List<Payment>> {
            override fun onResponse(call: Call<List<Payment>>, response: Response<List<Payment>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _isError.value = false
                    _message.value = ""
                    if (response.body()?.size!! > 0) {
                        payment.value = response.body()?.get(0)
                    } else {
                        payment.value = Payment()
                    }
                }
            }

            override fun onFailure(call: Call<List<Payment>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message
            }

        })
    }

    fun getPayment(): LiveData<Payment> {
        return payment
    }

    fun confirmPayment(id: Int) {
        val client = ApiConfig.getApiService().confirmPayment(id)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _confirmMessage.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _confirmMessage.value = t.message
            }
        })
    }

    fun unconfirmPayment(id: Int) {
        val client = ApiConfig.getApiService().unconfirmPayment(id)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    _unconfirmMessage.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _unconfirmMessage.value = t.message
            }
        })
    }

    fun deletePayment(id: Int, proof: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().deletePayment(id, proof)
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
}
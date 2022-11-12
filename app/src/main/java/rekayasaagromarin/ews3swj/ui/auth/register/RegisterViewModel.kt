package rekayasaagromarin.ews3swj.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.Membership
import rekayasaagromarin.ews3swj.model.AuthUser
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private var listMembership = MutableLiveData<ArrayList<String>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(name: String, email: String, password: String, membership_id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().registerUser(name, email, password, membership_id)
        client.enqueue(object : Callback<AuthUser> {
            override fun onResponse(call: Call<AuthUser>, response: Response<AuthUser>) {
                val loginResponse = response.body()
                if (response.isSuccessful) {
                    _message.value = loginResponse?.message
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<AuthUser>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }

        })
    }

    fun setMembership() {
        _isLoading.value = true
        val membership = ArrayList<String>()
        val client = ApiConfig.getApiService().getMembership()
        client.enqueue(object : Callback<List<Membership>> {
            override fun onResponse(
                call: Call<List<Membership>>,
                response: Response<List<Membership>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val membershipList = response.body()
                    if (membershipList != null) {
                        membershipList.forEach { member ->
                            membership.add(member.status)
                        }
                        listMembership.postValue(membership)
                    }
                }
            }

            override fun onFailure(call: Call<List<Membership>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
            }

        })
    }

    fun getMembership(): LiveData<ArrayList<String>> {
        return listMembership
    }
}
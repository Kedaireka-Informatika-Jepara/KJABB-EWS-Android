package rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.Membership
import rekayasaagromarin.ews3swj.model.ResponseApi
import rekayasaagromarin.ews3swj.model.Role
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditUserViewModel : ViewModel() {
    private var listRole = MutableLiveData<ArrayList<String>>()
    private var listMembership = MutableLiveData<ArrayList<String>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> = _isSuccess

    fun editUser(id: Int, name: String, email: String, membership_id: Int, role_id: Int) {
        _isLoading.value = true
        val client =
            ApiConfig.getApiService().editUser(id, name, email, membership_id, role_id)
        client.enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                val loginResponse = response.body()
                if (response.isSuccessful) {
                    _message.value = loginResponse?.message
                    _isLoading.value = false
                    _isSuccess.value = loginResponse?.status
                }
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }

        })
    }


    fun setRole() {
        _isLoading.value = true
        val role = ArrayList<String>()
        val client = ApiConfig.getApiService().getRole()
        client.enqueue(object : Callback<List<Role>> {
            override fun onResponse(
                call: Call<List<Role>>,
                response: Response<List<Role>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val roleList = response.body()
                    if (roleList != null) {
                        roleList.forEach {
                            role.add(it.role)
                        }
                        listRole.postValue(role)
                    }
                }
            }

            override fun onFailure(call: Call<List<Role>>, t: Throwable) {
                _message.value = t.message
                _isLoading.value = false
            }

        })
    }

    fun getRole(): LiveData<ArrayList<String>> {
        return listRole
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
                    val memberList = response.body()
                    if (memberList != null) {
                        memberList.forEach {
                            membership.add(it.status)
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
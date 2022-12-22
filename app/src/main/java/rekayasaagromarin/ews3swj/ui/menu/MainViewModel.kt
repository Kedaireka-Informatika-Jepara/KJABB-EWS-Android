package rekayasaagromarin.ews3swj.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val user = MutableLiveData<User>()

    fun setUser(id: Int){
        val client = ApiConfig.getApiService().getUser(id)
        client.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    user.postValue(response.body()?.get(0) ?: User())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            }
        })
    }

    fun getUser() : LiveData<User>{
        return user
    }
}
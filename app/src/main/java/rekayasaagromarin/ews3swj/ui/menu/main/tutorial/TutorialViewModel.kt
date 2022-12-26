package rekayasaagromarin.ews3swj.ui.menu.main.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TutorialViewModel : ViewModel() {
    private val listTutorial = ArrayList<String>()
    private val listUrl = ArrayList<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setListTutorial(){
        listTutorial.add("Data User")
        listTutorial.add("Data Payment")
        listTutorial.add("Data Weight")
        listTutorial.add("Data Family Biotic")
        listTutorial.add("Data Station")
        listTutorial.add("Input Data")
        listTutorial.add("Input History")
    }

    fun getListTutorial(): ArrayList<String>{
        return listTutorial
    }

    fun setListUrl(){
        listUrl.add("https://firebasestorage.googleapis.com/v0/b/ews-3swj.appspot.com/o/example.mp4?alt=media&token=d27ceea0-48b2-405e-84d6-babf86561371")
        listUrl.add("https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1")
        listUrl.add("https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1")
        listUrl.add("https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1")
    }

    fun getUrl(index: Int): String{
        return listUrl[index]
    }
}
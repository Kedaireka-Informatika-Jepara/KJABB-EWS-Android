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
        listUrl.add("https://firebasestorage.googleapis.com/v0/b/ews-3swj.appspot.com/o/tutorial%20mobile.mp4?alt=media&token=697bf9fe-afe5-426e-80c7-d6caa89489ad")
        listUrl.add("https://firebasestorage.googleapis.com/v0/b/ews-3swj.appspot.com/o/tutorial%20mobile.mp4?alt=media&token=697bf9fe-afe5-426e-80c7-d6caa89489ad")
        listUrl.add("https://firebasestorage.googleapis.com/v0/b/ews-3swj.appspot.com/o/tutorial%20mobile.mp4?alt=media&token=697bf9fe-afe5-426e-80c7-d6caa89489ad")
        listUrl.add("https://firebasestorage.googleapis.com/v0/b/ews-3swj.appspot.com/o/tutorial%20mobile.mp4?alt=media&token=697bf9fe-afe5-426e-80c7-d6caa89489ad")
        listUrl.add("https://firebasestorage.googleapis.com/v0/b/ews-3swj.appspot.com/o/tutorial%20mobile.mp4?alt=media&token=697bf9fe-afe5-426e-80c7-d6caa89489ad")
        listUrl.add("https://firebasestorage.googleapis.com/v0/b/ews-3swj.appspot.com/o/tutorial%20mobile.mp4?alt=media&token=697bf9fe-afe5-426e-80c7-d6caa89489ad")
        listUrl.add("https://firebasestorage.googleapis.com/v0/b/ews-3swj.appspot.com/o/tutorial%20mobile.mp4?alt=media&token=697bf9fe-afe5-426e-80c7-d6caa89489ad")
    }

    fun getUrl(index: Int): String{
        return listUrl[index]
    }
}
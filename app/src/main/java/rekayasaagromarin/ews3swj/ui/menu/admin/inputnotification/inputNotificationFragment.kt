package rekayasaagromarin.ews3swj.ui.menu.admin.inputnotification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rekayasaagromarin.ews3swj.R

class inputNotificationFragment : Fragment() {

    private lateinit var inputNotificationViewModel: InputNotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input_notification, container, false)
    }

}
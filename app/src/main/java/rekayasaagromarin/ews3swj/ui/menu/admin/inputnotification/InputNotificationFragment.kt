package rekayasaagromarin.ews3swj.ui.menu.admin.inputnotification

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.FragmentInputNotificationBinding
import rekayasaagromarin.ews3swj.model.Notification
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.menu.MainActivity

class InputNotificationFragment : Fragment() {
    private val inputNotificationViewModel: InputNotificationViewModel by viewModels()
    private var _binding: FragmentInputNotificationBinding? = null
    private val binding get() = _binding!!
    private val preferencesManager: PreferencesManager by lazy { PreferencesManager(context) }

    private var listReceiverId = arrayListOf<Int>()
    private var listReceiverName = arrayListOf<String>()
    private var receiverId = 0
    private var receiverName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputNotificationBinding.inflate(inflater, container, false)

        getReceiver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSendNotification.setOnClickListener {
            sendNotification()
        }
    }

    private fun getReceiver(){
        with(inputNotificationViewModel){
            setReceiver()
            getListUser().observe(viewLifecycleOwner) {
                listReceiverId.clear()
                listReceiverName.clear()
                listReceiverId.addAll(listOf(-1, -2, -3, -4))
                listReceiverName.addAll(listOf("Admin","Member","Operator","Semua User"))

                for (i in it.indices){
                    listReceiverId.add(it[i].userId)
                    listReceiverName.add(it[i].name)
                }
                initReceiver()
            }
        }
    }

    private fun initReceiver() {
        val spinnerArrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_text,
            listReceiverName
        )

        (binding.tilReceiver.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownReceiver.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            receiverId = listReceiverId[position]
            receiverName = listReceiverName[position]

            Toast.makeText(
                context,
                "Receiver ID: $receiverId, Receiver Name: $receiverName",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun sendNotification() {
        if (binding.edtTitle.text.toString() == ""){
            binding.edtTitle.error = "Judul tidak boleh kosong"
        } else if (binding.edtMessage.text.toString() == ""){
            binding.edtMessage.error = "Pesan tidak boleh kosong"
        } else {
            when(receiverId){
                -1 -> { // Admin
                    inputNotificationViewModel.getListUser().observe(viewLifecycleOwner){
                        for (i in it.indices){
                            if(it[i].roleId == 1){
                                Toast.makeText(context, it[i].name, Toast.LENGTH_SHORT).show()
                                val notification = Notification(
                                    senderId = preferencesManager.getId()!!,
                                    receiverId = it[i].userId,
                                    title = binding.edtTitle.text.toString(),
                                    message = binding.edtMessage.text.toString(),
                                    isRead = 0
                                )
                                inputNotificationViewModel.sendNotification(notification)
                            }
                        }
                    }
//                    val adminUsers =
//                    val notification = Notification(
//                        senderId = preferencesManager.getId()!!,
//                        receiverId = receiverId,
//                        title = binding.edtTitle.text.toString(),
//                        message = binding.edtMessage.text.toString(),
//                        isRead = 0
//                    )
//                    inputNotificationViewModel.sendNotification(notification)
                }
                -2 -> { //member
                    inputNotificationViewModel.getListUser().observe(viewLifecycleOwner){
                        for (i in it.indices){
                            if(it[i].roleId == 2){
                                val notification = Notification(
                                    senderId = preferencesManager.getId()!!,
                                    receiverId = it[i].userId,
                                    title = binding.edtTitle.text.toString(),
                                    message = binding.edtMessage.text.toString(),
                                    isRead = 0
                                )
                                inputNotificationViewModel.sendNotification(notification)
                            }
                        }
                    }
                }
                -3 -> { //operator
                    inputNotificationViewModel.getListUser().observe(viewLifecycleOwner){
                        for (i in it.indices){
                            if(it[i].roleId == 3){
                                val notification = Notification(
                                    senderId = preferencesManager.getId()!!,
                                    receiverId = it[i].userId,
                                    title = binding.edtTitle.text.toString(),
                                    message = binding.edtMessage.text.toString(),
                                    isRead = 0
                                )
                                inputNotificationViewModel.sendNotification(notification)
                            }
                        }
                    }
                }
                -4 -> { // All User
                    inputNotificationViewModel.getListUser().observe(viewLifecycleOwner){
                        for (i in it.indices){
//                            if(it[i].roleId == 3){
                                val notification = Notification(
                                    senderId = preferencesManager.getId()!!,
                                    receiverId = it[i].userId,
                                    title = binding.edtTitle.text.toString(),
                                    message = binding.edtMessage.text.toString(),
                                    isRead = 0
                                )
                                inputNotificationViewModel.sendNotification(notification)
//                            }
                        }
                    }
                }
                else -> {
                    val notification = Notification(
                        senderId = preferencesManager.getId()!!,
                        receiverId = receiverId,
                        title = binding.edtTitle.text.toString(),
                        message = binding.edtMessage.text.toString(),
                        isRead = 0
                    )
                    inputNotificationViewModel.sendNotification(notification)
                }
            }
        }
        with(inputNotificationViewModel){
            message.observe(viewLifecycleOwner) {
                showMessage(it)
            }

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            isSuccess.observe(viewLifecycleOwner) {
                if (it == 200){
                    binding.edtTitle.setText("")
                    binding.edtMessage.setText("")
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingInputNotification.root.visibility = View.VISIBLE
        } else {
            binding.loadingInputNotification.root.visibility = View.GONE
        }
    }
}
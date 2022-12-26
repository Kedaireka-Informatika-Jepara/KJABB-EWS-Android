package rekayasaagromarin.ews3swj.ui.notifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rekayasaagromarin.ews3swj.adapter.NotificationAdapter
import rekayasaagromarin.ews3swj.databinding.ActivityNotificationsBinding
import rekayasaagromarin.ews3swj.model.Notification
import rekayasaagromarin.ews3swj.ui.menu.MainActivity

class NotificationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationsBinding
    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private var listNotification = ArrayList<Notification>()
    private val notificationAdapter: NotificationAdapter by lazy { NotificationAdapter() }
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        userId = intent.getIntExtra(MainActivity.EXTRA_ID, 0)
        Toast.makeText(this, userId.toString(), Toast.LENGTH_SHORT).show()

        initNotifications()
    }

    private fun initNotifications(){
        notificationsViewModel.setListNotification()
        notificationsViewModel.getListNotification().observe(this@NotificationsActivity){ list ->
            listNotification.clear()

            list.forEach {
                if(it.receiverId == userId){
                    listNotification.add(it)
                }
            }
            notificationAdapter.setNotification(listNotification)

            binding.rvNotifications.apply {
                layoutManager = LinearLayoutManager(this@NotificationsActivity)
                adapter = notificationAdapter
            }

            notificationAdapter.setOnItemClickCallback(object : NotificationAdapter.OnItemClickCallback{
                override fun onItemClicked(notification:  Notification) {
                    Toast.makeText(this@NotificationsActivity, notification.id.toString(), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@NotificationsActivity, DetailNotificationActivity::class.java).apply {
                        putExtra(DetailNotificationActivity.EXTRA_NOTIFICATION, notification)
                    }
                    startActivity(intent)
                }
            })
        }
    }
}
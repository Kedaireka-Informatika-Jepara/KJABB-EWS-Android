package rekayasaagromarin.ews3swj.ui.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.databinding.ActivityDetailNotificationBinding
import rekayasaagromarin.ews3swj.model.Notification
import java.text.SimpleDateFormat
import java.util.*

class DetailNotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNotificationBinding
    private val detailNotificationViewModel: DetailNotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNotificationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val notification = intent.getParcelableExtra<Notification>(EXTRA_NOTIFICATION)

        if (notification != null) {
            Toast.makeText(this, notification.id.toString(), Toast.LENGTH_SHORT).show()

            detailNotificationViewModel.setUser(notification.senderId)
            detailNotificationViewModel.getUser().observe(this@DetailNotificationActivity){ user ->
                binding.tvSender.text = user.name

                val sdf = SimpleDateFormat("HH:mm:ss, MM/dd/yyyy")
                val netDate = Date(notification.dateCreated.toLong() * 1000)
                binding.tvDate.text = sdf.format(netDate)

                binding.tvTitle.text = notification.title
                binding.tvMessage.text = notification.message
            }
        }
    }

    companion object {
        const val EXTRA_NOTIFICATION = "extra_notification"
    }
}
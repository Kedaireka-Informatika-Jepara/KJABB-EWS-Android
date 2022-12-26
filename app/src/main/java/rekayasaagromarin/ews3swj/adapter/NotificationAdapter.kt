package rekayasaagromarin.ews3swj.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.model.Notification
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private lateinit var notifications: ArrayList<Notification>
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setNotification(notifications: ArrayList<Notification>) {
        this.notifications = notifications
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvMessage: TextView = view.findViewById(R.id.tv_message)
        val tvDate: TextView = view.findViewById(R.id.tv_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]

        holder.tvTitle.text = notification.title
        holder.tvMessage.text = notification.message

        val sdf = SimpleDateFormat("HH:mm:ss, MM/dd/yyyy")
        val netDate = Date(notification.dateCreated.toLong() * 1000)
        holder.tvDate.text = sdf.format(netDate)

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(notification)
        }
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(notification: Notification)
    }
}
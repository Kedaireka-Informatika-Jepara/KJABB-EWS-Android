package rekayasaagromarin.ews3swj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Notification(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("sender_id")
    val senderId: Int = 0,

    @field:SerializedName("receiver_id")
    val receiverId: Int = 0,

    @field:SerializedName("title")
    val title: String = "",

    @field:SerializedName("message")
    val message: String = "",

    @field:SerializedName("date_created")
    val dateCreated: String = "",

    @field:SerializedName("is_read")
    val isRead: Int = 0,
): Parcelable
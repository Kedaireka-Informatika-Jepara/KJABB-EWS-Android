package rekayasaagromarin.ews3swj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("email")
    val email: String = "",

    @field:SerializedName("user_id")
    val userId: Int = 0,

    @field:SerializedName("role")
    val role: String = "",

    @field:SerializedName("role_id")
    val roleId: Int = 0,

    @field:SerializedName("membership")
    val membership: String = "",

    @field:SerializedName("membership_id")
    val membershipId: Int = 0,

    @field:SerializedName("is_active")
    val isActive: Int = 0,

    @field:SerializedName("image")
    val image: String = "",

    @field:SerializedName("password")
    val password: String = "",

    @field:SerializedName("date_created")
    val dateCreated: String = ""

) : Parcelable
package rekayasaagromarin.ews3swj.model

import com.google.gson.annotations.SerializedName

data class AuthUser(
    @field:SerializedName("error")
    val isSuccess: Boolean,

    @field:SerializedName("message")
    val message: String = "",

    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("email")
    val email: String = "",

    @field:SerializedName("image")
    val image: String = "",

    @field:SerializedName("is_active")
    val isActive : Int = 0
)
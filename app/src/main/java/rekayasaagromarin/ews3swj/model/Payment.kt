package rekayasaagromarin.ews3swj.model

import com.google.gson.annotations.SerializedName

data class Payment(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("user_email")
    val userEmail: String = "",

    @field:SerializedName("status")
    val status: Int = 0,

    @field:SerializedName("datetime")
    val date: String = "",

    @field:SerializedName("bukti")
    val proof: String = "",

)
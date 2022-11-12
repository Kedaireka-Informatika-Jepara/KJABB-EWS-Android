package rekayasaagromarin.ews3swj.model

import com.google.gson.annotations.SerializedName

data class Role(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("role")
    val role: String = ""
)
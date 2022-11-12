package rekayasaagromarin.ews3swj.model

import com.google.gson.annotations.SerializedName

data class ResponseApi(
    @field:SerializedName("status")
    val status: Int = 0,

    @field:SerializedName("message")
    val message: String = ""
)
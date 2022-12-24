package rekayasaagromarin.ews3swj.model

import com.google.gson.annotations.SerializedName

class Parameter (
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("type")
    val type: Int = 0,

    @field:SerializedName("description")
    val description: String = ""
)
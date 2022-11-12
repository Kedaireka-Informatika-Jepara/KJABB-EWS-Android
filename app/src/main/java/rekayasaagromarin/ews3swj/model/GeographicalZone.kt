package rekayasaagromarin.ews3swj.model

import com.google.gson.annotations.SerializedName

data class GeographicalZone(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("name")
    val name: String = "",
)
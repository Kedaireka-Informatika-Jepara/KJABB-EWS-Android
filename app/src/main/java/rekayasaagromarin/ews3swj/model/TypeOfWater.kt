package rekayasaagromarin.ews3swj.model

import com.google.gson.annotations.SerializedName

data class TypeOfWater(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("water")
    val water: String = "",
)
package rekayasaagromarin.ews3swj.model

import com.google.gson.annotations.SerializedName

data class ParameterResponse(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("nilai_awal")
    val initialValue: Double = 0.00,

    @field:SerializedName("nilai_akhir")
    val finalValue: Double = 0.00,
)
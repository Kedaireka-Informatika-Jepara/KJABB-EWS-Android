package rekayasaagromarin.ews3swj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Species(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("family")
    val family: String = "",

    @field:SerializedName("species")
    val species: String = "",

    @field:SerializedName("abundance")
    val abundance: Int = 0,

    @field:SerializedName("taxa_indicator")
    val taxaIndicator: Double = 0.00,

    @field:SerializedName("station_id")
    val stationId: String = "",

    @field:SerializedName("user_id")
    val userId: Int = 0,
) : Parcelable
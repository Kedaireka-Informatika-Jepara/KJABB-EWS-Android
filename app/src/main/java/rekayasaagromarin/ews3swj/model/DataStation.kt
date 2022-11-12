package rekayasaagromarin.ews3swj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataStation(
    @field:SerializedName("station_id")
    val stationId: String ="",

    @field:SerializedName("value")
    val value: Double = 0.0,

    @field:SerializedName("type_of_water")
    val typeOfWater: String = "",

    @field:SerializedName("type_of_water_id")
    val typeOfWaterId: Int = 0,

    @field:SerializedName("geographical_zone")
    val geographicalZone: String = "",

    @field:SerializedName("geographical_zone_id")
    val geographicalZoneId: Int = 0,

    @field:SerializedName("created_by")
    val createdBy: String = "",
) : Parcelable
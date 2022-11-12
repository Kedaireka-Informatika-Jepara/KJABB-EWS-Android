package rekayasaagromarin.ews3swj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Station(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("station_id")
    val stationId: String = "",

    val typeOfWater: String = "",

    @field:SerializedName("type_of_water")
    val typeOfWaterId: Int = 0,

    val geographicalZone: String = "",

    @field:SerializedName("geographical_zone")
    val geographicalZoneId: Int = 0,

    @field:SerializedName("user_id")
    val userId: Int = 0,
) : Parcelable
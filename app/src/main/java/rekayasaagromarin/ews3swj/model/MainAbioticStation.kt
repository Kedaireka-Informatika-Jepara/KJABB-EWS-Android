package rekayasaagromarin.ews3swj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainAbioticStation(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("salinity")
    val salinity: Double = 0.0,

    @field:SerializedName("bobot_salinity")
    val wSalinity: Double = 0.0,

    @field:SerializedName("temperature")
    val temperature: Double = 0.0,

    @field:SerializedName("bobot_temperature")
    val wTemperature: Double = 0.0,

    @field:SerializedName("do")
    val doParam: Double = 0.0,

    @field:SerializedName("bobot_do")
    val wDoParam: Double = 0.0,

    @field:SerializedName("ph")
    val ph: Double = 0.0,

    @field:SerializedName("bobot_ph")
    val wPh: Double = 0.0,

    @field:SerializedName("type_of_water")
    val typeOfWater: Int = 0,

    @field:SerializedName("geographical_zone")
    val geographicalZone: Int = 0,

    @field:SerializedName("user_id")
    val userId: Int = 0,

    @field:SerializedName("station_id")
    val stationId: String = "",
) : Parcelable
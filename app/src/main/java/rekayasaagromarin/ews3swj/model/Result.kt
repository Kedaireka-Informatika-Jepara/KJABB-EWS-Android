package rekayasaagromarin.ews3swj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("result")
    val result: Double = 0.0,

    @field:SerializedName("status")
    val status: String = "",

    @field:SerializedName("conclusion")
    val conclusion: String = "",

    @field:SerializedName("recommendation")
    val recommendation: String = "",

    @field:SerializedName("station_id")
    val stationId: String = "",

    @field:SerializedName("user_id")
    val userId: Int = 0,

) : Parcelable
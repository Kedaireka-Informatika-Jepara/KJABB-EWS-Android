package rekayasaagromarin.ews3swj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FamilyBiotic(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("family")
    val family: String = "",

    @field:SerializedName("bobot")
    val weight: Double = 0.00,
) : Parcelable
package rekayasaagromarin.ews3swj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IndexAddStation(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("similarity")
    val similarity: Double = 0.0,

    @field:SerializedName("bobot_similarity")
    val wSimilarity: Double = 0.0,

    @field:SerializedName("dominance")
    val dominance: String = "",

    @field:SerializedName("bobot_dominance")
    val wDominance: Double = 0.0,

    @field:SerializedName("diversity")
    val diversity: String = "",

    @field:SerializedName("bobot_diversity")
    val wDiversity: Double = 0.0,

    @field:SerializedName("total_abundance")
    val totalAbundance: Double = 0.0,

    @field:SerializedName("bobot_total_abundance")
    val wTotalAbundance: Double = 0.0,

    @field:SerializedName("number_species")
    val numberSpecies: Int = 0,

    @field:SerializedName("bobot_number_species")
    val wNumberSpecies: Int = 0,

    @field:SerializedName("taxa_indicator")
    val taxaIndicator: Double = 0.0,

    @field:SerializedName("bobot_taxa_indicator") //gada
    val wTaxaIndicator: Double = 0.0,

    @field:SerializedName("conductivity")
    val conductivity: String = "",

    @field:SerializedName("bobot_conductivity")
    val wConductivity: Double = 0.0,

    @field:SerializedName("ratiocn")
    val rationCn: String = "",

    @field:SerializedName("bobot_ratiocn")
    val wRationCn: Double = 0.0,

    @field:SerializedName("turbidity")
    val turbidity: Double = 0.0,

    @field:SerializedName("bobot_turbidity")
    val wTurbidity: Double = 0.0,

    @field:SerializedName("sand")
    val sand: String = "",

    @field:SerializedName("bobot_sand")
    val wSand: Double = 0.0,

//    @field:SerializedName("clay")
//    val clay: Double = 0.0,

    @field:SerializedName("clay")
    val clay: String = "",

    @field:SerializedName("bobot_clay")
    val wClay: Double = 0.0,

    @field:SerializedName("silt")
    val silt: Double = 0.0,

    @field:SerializedName("bobot_silt")
    val wSilt: Double = 0.0,

    @field:SerializedName("station_id")
    val stationId: String = "",

    @field:SerializedName("user_id")
    val userId: Int = 0,
) : Parcelable
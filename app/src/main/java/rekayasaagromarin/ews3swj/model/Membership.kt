package rekayasaagromarin.ews3swj.model

import com.google.gson.annotations.SerializedName

data class Membership(
	@field:SerializedName("membership_id")
	val membershipId: Int = 0,

	@field:SerializedName("status")
	val status: String = ""
)

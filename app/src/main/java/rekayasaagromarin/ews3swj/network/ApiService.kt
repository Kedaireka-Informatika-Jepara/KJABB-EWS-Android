package rekayasaagromarin.ews3swj.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import rekayasaagromarin.ews3swj.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/api/v1/auth/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthUser>

    @FormUrlEncoded
    @POST("/api/v1/auth/register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("membership_id") membership_id: Int
    ): Call<AuthUser>

    @GET("/api/v1/membership")
    fun getMembership(): Call<List<Membership>>

    @GET("/api/v1/role")
    fun getRole(): Call<List<Role>>

    @GET("/api/v1/user/{id}")
    fun getUser(
        @Path("id") id: Int
    ): Call<List<User>>

    @GET("/api/v1/user/email_check/{email}")
    fun emailCheck(
        @Path("email") email: String
    ): Call<ResponseApi>

    @GET("/api/v1/data_user")
    fun getListDataUser(): Call<List<User>>

    @GET("/api/v1/notification")
    fun getListNotification(): Call<List<Notification>>

    @GET("/api/v1/user/delete/{id}")
    fun deleteUser(
        @Path("id") id: Int
    ): Call<ResponseApi>

    @GET("/api/v1/user/activate/{id}")
    fun activateUser(
        @Path("id") id: Int
    ): Call<ResponseApi>

    @GET("/api/v1/user/deactivate/{id}")
    fun deactivateUser(
        @Path("id") id: Int
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/user/add")
    fun addUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("membership_id") membership_id: Int,
        @Field("role_id") role_id: Int
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/user/edit/{id}")
    fun editUser(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("membership_id") membership_id: Int,
        @Field("role_id") role_id: Int
    ): Call<ResponseApi>

    @GET("/api/v1/data_payment")
    fun getListPayment(): Call<List<Payment>>

    @GET("/api/v1/payment/{id}")
    fun getPayment(
        @Path("id") id: Int
    ): Call<List<Payment>>

    @GET("/api/v1/payment/confirm/{id}")
    fun confirmPayment(
        @Path("id") id: Int
    ): Call<ResponseApi>

    @GET("/api/v1/payment/unconfirm/{id}")
    fun unconfirmPayment(
        @Path("id") id: Int
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/payment/delete/{id}")
    fun deletePayment(
        @Path("id") id: Int,
        @Field("image_name") proof: String
    ): Call<ResponseApi>

    @GET("/api/v1/index_biotic")
    fun getListIndexBiotic(): Call<List<ParameterResponse>>

    @GET("/api/v1/index_biotic/{id}")
    fun getIndexBiotic(
        @Path("id") id: Int
    ): Call<List<IndexBiotic>>

    @FormUrlEncoded
    @POST("/api/v1/index_biotic/add")
    fun addIndexBiotic(
        @Field("name") name: String,
        @Field("nilai_awal") initialValue: Double,
        @Field("nilai_akhir") finalValue: Double,
        @Field("bobot") weight: Double,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/index_biotic/edit/{id}")
    fun editIndexBiotic(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("nilai_awal") initialValue: Double,
        @Field("nilai_akhir") finalValue: Double,
        @Field("bobot") weight: Double,
    ): Call<ResponseApi>

    @GET("/api/v1/index_biotic/delete/{id}")
    fun deleteIndexBiotic(
        @Path("id") id: Int
    ): Call<ResponseApi>

    @GET("/api/v1/main_abiotic")
    fun getListMainBiotic(): Call<List<ParameterResponse>>

    @GET("/api/v1/main_abiotic/{id}")
    fun getMainAbiotic(
        @Path("id") id: Int
    ): Call<List<MainAbiotic>>

//    add geographical zone
    @FormUrlEncoded
    @POST("/api/v1/geographical_zone/add")
    fun addGeographicalZone(
        @Field("zone") zone: String
    ): Call<ResponseApi>

    @GET("/api/v1/geographical_zone")
    fun getGeographicalZone(): Call<List<GeographicalZone>>

    @FormUrlEncoded
    @POST("/api/v1/type_water/add")
    fun addTypeWater(
        @Field("water") water: String
    ): Call<ResponseApi>

    @GET("/api/v1/type_water")
    fun getTypeOfWater(): Call<List<TypeOfWater>>

    //    add parameter
    @FormUrlEncoded
    @POST("/api/v1/parameter/add")
    fun addParameter(
        @Field("name") name: String,
        @Field("type") type: Int,
        @Field("description") description: String
    ): Call<ResponseApi>

    @GET("/api/v1/parameter")
    fun getParameter(): Call<List<Parameter>>

    @FormUrlEncoded
    @POST("/api/v1/main_abiotic/add")
    fun addMainAbiotic(
        @Field("name") name: String,
        @Field("geographical_zone_id") geographicalZone: Int,
        @Field("type_water_id") typeOfWater: Int,
        @Field("nilai_awal") initialValue: Double,
        @Field("nilai_akhir") finalValue: Double,
        @Field("bobot") weight: Double,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/main_abiotic/edit/{id}")
    fun editMainAbiotic(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("geographical_zone_id") geographicalZoneId: Int,
        @Field("type_water_id") typeOfWaterId: Int,
        @Field("nilai_awal") initialValue: Double,
        @Field("nilai_akhir") finalValue: Double,
        @Field("bobot") weight: Double,
    ): Call<ResponseApi>

    @GET("/api/v1/main_abiotic/delete/{id}")
    fun deleteMainAbiotic(
        @Path("id") id: Int
    ): Call<ResponseApi>

    @GET("/api/v1/additional_abiotic")
    fun getListAdditionalAbiotic(): Call<List<ParameterResponse>>

    @GET("/api/v1/additional_abiotic/{id}")
    fun getAdditionalAbiotic(
        @Path("id") id: Int
    ): Call<List<AdditionalAbiotic>>

    @FormUrlEncoded
    @POST("/api/v1/additional_abiotic/add")
    fun addAdditionalAbiotic(
        @Field("name") name: String,
        @Field("nilai_awal") initialValue: Double,
        @Field("nilai_akhir") finalValue: Double,
        @Field("bobot") weight: Double,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/additional_abiotic/edit/{id}")
    fun editAdditionalAbiotic(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("nilai_awal") initialValue: Double,
        @Field("nilai_akhir") finalValue: Double,
        @Field("bobot") weight: Double,
    ): Call<ResponseApi>

    @GET("/api/v1/additional_abiotic/delete/{id}")
    fun deleteAdditionalAbiotic(
        @Path("id") id: Int
    ): Call<ResponseApi>

    @GET("/api/v1/family_biotic")
    fun getListFamilyBiotic(): Call<List<FamilyBiotic>>

    @FormUrlEncoded
    @POST("/api/v1/family_biotic/add")
    fun addFamilyBiotic(
        @Field("family") name: String,
        @Field("bobot") initialValue: Double,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/family_biotic/edit/{id}")
    fun editFamilyBiotic(
        @Path("id") id: Int,
        @Field("family") name: String,
        @Field("bobot") initialValue: Double,
    ): Call<ResponseApi>

    @GET("/api/v1/family_biotic/delete/{id}")
    fun deleteFamilyBiotic(
        @Path("id") id: Int
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_data/index_biotic/count")
    fun indexBioticCount(
        @Field("diversity") diversity: String,
        @Field("dominance") dominance: String,
        @Field("similarity") similarity: String,
        @Field("number_species") numberSpecies: Int,
        @Field("total_abundance") numberAbundance: Double,
    ): Call<List<CountResponse>>

    @FormUrlEncoded
    @POST("/api/v1/input_data/additional_abiotic/count")
    fun additionalAbioticCount(
        @Field("conductivity") conductivity: String,
        @Field("ratiocn") ratiocn: String,
        @Field("turbidity") turbidity: String,
        @Field("clay") clay: String,
        @Field("sand") sand: String,
        @Field("silt") silt: String,
    ): Call<List<CountResponse>>

    @FormUrlEncoded
    @POST("/api/v1/input_data/main_abiotic/count")
    fun mainAbioticCount(
        @Field("temperature") temperature: String,
        @Field("salinity") salinity: String,
        @Field("do") doParam: String,
        @Field("ph") ph: String,
        @Field("type_water") typeOfWater: Int,
        @Field("geographical_zone") geographicalZone: Int,
    ): Call<List<CountResponse>>

    @FormUrlEncoded
    @POST("/api/v1/station/id_check")
    fun stationCheck(
        @Field("station_id") stationId: String,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_data/station/save")
    fun saveStation(
        @Field("station_id") stationId: String,
        @Field("type_water") typeOfWater: Int,
        @Field("geographical_zone") geographicalZone: Int,
        @Field("user_id") userId: Int,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_history/station/edit/{station_id}")
    fun editStation(
        @Path("station_id") stationId: String,
        @Field("type_water") typeOfWater: Int,
        @Field("geographical_zone") geographicalZone: Int,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_data/species/save")
    fun saveSpecies(
        @Field("family") family: String,
        @Field("species") species: String,
        @Field("abundance") abundance: Int,
        @Field("taxa_indicator") taxaIndicator: Double,
        @Field("user_id") userId: Int,
        @Field("station_id") stationId: String,
    ): Call<ResponseApi>

    @GET("/api/v1/input_history/species/delete/{station_id}")
    fun deleteSpecies(
        @Path("station_id") stationId: String,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_data/index_add_station/save")
    fun saveIndexAddStation(
        @Field("similarity") similarity: String,
        @Field("dominance") dominance: String,
        @Field("diversity") diversity: String,
        @Field("total_abundance") totalAbundance: Double,
        @Field("number_species") numberSpecies: Int,
        @Field("taxa_indicator") taxaIndicator: Double,
        @Field("conductivity") conductivity: String,
        @Field("ratiocn") ratioCn: String,
        @Field("turbidity") turbidity: String,
        @Field("clay") clay: String,
        @Field("sand") sand: String,
        @Field("silt") silt: String,
        @Field("station_id") stationId: String,
        @Field("user_id") userId: Int,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_history/index_add/edit/{station_id}")
    fun editIndexAddStation(
        @Path("station_id") stationId: String,
        @Field("similarity") similarity: String,
        @Field("dominance") dominance: String,
        @Field("diversity") diversity: String,
        @Field("total_abundance") totalAbundance: Double,
        @Field("number_species") numberSpecies: Int,
        @Field("taxa_indicator") taxaIndicator: Double,
        @Field("conductivity") conductivity: String,
        @Field("ratiocn") ratioCn: String,
        @Field("turbidity") turbidity: String,
        @Field("clay") clay: String,
        @Field("sand") sand: String,
        @Field("silt") silt: String,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_data/main_station/save")
    fun saveMainStation(
        @Field("temperature") temperature: String,
        @Field("salinity") salinity: String,
        @Field("do") doParam: String,
        @Field("ph") ph: String,
        @Field("type_water") typeOfWater: Int,
        @Field("geographical_zone") geographicalZone: Int,
        @Field("station_id") stationId: String,
        @Field("user_id") userId: Int,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_history/main_abiotic/edit/{station_id}")
    fun editMainStation(
        @Path("station_id") stationId: String,
        @Field("temperature") temperature: String,
        @Field("salinity") salinity: String,
        @Field("do") doParam: String,
        @Field("ph") ph: String,
        @Field("type_water") typeOfWater: Int,
        @Field("geographical_zone") geographicalZone: Int,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_data/result/save")
    fun saveResult(
        @Field("result") result: Double,
        @Field("status") status: String,
        @Field("conclusion") conclusion: String,
        @Field("recommendation") recommendation: String,
        @Field("station_id") stationId: String,
        @Field("user_id") userId: Int,
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/input_history/result/edit/{station_id}")
    fun editResult(
        @Path("station_id") stationId: String,
        @Field("result") result: Double,
        @Field("status") status: String,
        @Field("conclusion") conclusion: String,
        @Field("recommendation") recommendation: String,
    ): Call<ResponseApi>

    @GET("/api/v1/data_station")
    fun getListDataStation(): Call<List<DataStation>>

    @GET("/api/v1/data_station/species/{station_id}")
    fun getDataSpecies(
        @Path("station_id") stationId: String
    ): Call<List<Species>>

    @GET("/api/v1/data_station/index_add/{station_id}")
    fun getIndexAddStation(
        @Path("station_id") stationId: String
    ): Call<List<IndexAddStation>>

    @GET("/api/v1/data_station/main_abiotic/{station_id}")
    fun getMainAbioticStation(
        @Path("station_id") stationId: String
    ): Call<List<MainAbioticStation>>

    @GET("/api/v1/data_station/result/{station_id}")
    fun getResultStation(
        @Path("station_id") stationId: String
    ): Call<List<Result>>

    @GET("/api/v1/data_station/delete/{station_id}")
    fun deleteDataStation(
        @Path("station_id") stationId: String
    ): Call<ResponseApi>

    @GET("/api/v1/input_history/taxa_indicator/{user_id}")
    fun getTaxaIndicatorById(
        @Path("user_id") userId: Int
    ): Call<List<Species>>

    @GET("/api/v1/input_history/station/{user_id}")
    fun getListDataStationById(
        @Path("user_id") userId: Int
    ): Call<List<DataStation>>

    @Multipart
    @POST("/api/v1/data_payment/upload_payment")
    fun uploadPayment(
        @Part("user_email") userEmail: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseApi>

    @Multipart
    @POST("/api/v1/profile/upload_photo")
    fun uploadPhoto(
        @Part("user_email") userEmail: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/profile/edit_profile/{id}")
    fun editProfile(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("email") email: String
    ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("/api/v1/profile/edit_password/{id}")
    fun changePassword(
        @Path("id") id: Int,
        @Field("current_password") currentPassword: String,
        @Field("new_password") newPassword: String
    ): Call<ResponseApi>
}
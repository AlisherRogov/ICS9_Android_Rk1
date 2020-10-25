package ru.bmstu.iu9.rk1

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

private const val BASE_URL = "https://min-api.cryptocompare.com/"

val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ExampleApiService {
    @GET("data/v2/histoday")
    fun getDailyData(
        @Query("fsym") fsym: String,
        @Query("tsym") tsym: String,
        @Query("limit") limit: Int
    ): Call<ExchangeResponse>

    @GET("data/v2/histohour")
    fun getHourlyData(
        @Query("fsym") fsym: String,
        @Query("tsym") tsym: String,
        @Query("limit") limit: Int,
        @Query("toTs") toTs: String
    ): Call<ExchangeResponse>
}

data class ExchangeResponse(
    val Response: String,
    val Message: String,
    val HasWarning: Boolean,
    val Type: Int,
    val Data: ExchangeData
)

data class ExchangeData(
    val Aggregated: Boolean,
    val TimeFrom: String,
    val TimeTo: String,
    val Data: List<ExchangeDataItem>
)

data class ExchangeDataItem(
    val time: String,
    val high: Float,
    val low: Float,
    val open: Float,
    val volumefrom: Float,
    val volumeto: Float,
    val close: Float,
    val conversionType: String,
    val conversionSymbol: String
)
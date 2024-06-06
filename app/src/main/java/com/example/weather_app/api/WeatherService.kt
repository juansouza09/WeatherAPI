package com.example.weather_app.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String,
        @Query("lang") language: String = "pt"
    ): Call<WeatherResponse>
}

data class WeatherResponse(val current: Current)
data class Current(val temp_c: Float, val condition: Condition)
data class Condition(val text: String)

package com.example.weather_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.api.WeatherResponse
import com.example.weather_app.api.WeatherService
import com.example.weather_app.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)

        binding.buttonUpdate.setOnClickListener {
            val cityName = binding.editTextCity.text.toString()
            if (cityName.isNotEmpty()) {
                val call = service.getCurrentWeather(cityName, "2890048f121a40ac9d8144228240606")
                call.enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                        if (response.isSuccessful) {
                            val weatherResponse = response.body()
                            val temperature = weatherResponse?.current?.temp_c
                            val condition = weatherResponse?.current?.condition?.text
                            binding.textViewTemperature.text = "Temperatura: $temperature°C\nCondição climática: $condition"
                        } else {
                            binding.textViewTemperature.text = "Error: ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        binding.textViewTemperature.text = "Failed to load data"
                    }
                })
            } else {
                binding.textViewTemperature.text = "Digite uma cidade:"
            }
        }
    }
}

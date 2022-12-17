package com.example.individualproject.networking

import com.example.individualproject.database_3.diseases.DiseaseEntity_3
import com.example.individualproject.models.Disease
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/main_connection/")
    fun getDiseases(): Call<List<Disease>>
}
package com.example.st_nobrainerquizapp

import retrofit2.Call
import retrofit2.http.GET

interface REST_API {

    @GET("/api/guide")
    fun getGuide(): Call<UserGuide>
}
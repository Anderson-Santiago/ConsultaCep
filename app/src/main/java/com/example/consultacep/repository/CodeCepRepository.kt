package com.example.consultacep.repository

import com.example.consultacep.domain.MyDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("{cep}/json")
    fun getData(@Path("cep") cep: String): Call <MyDataItem>
}
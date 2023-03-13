package com.example.moviesexampleapp.model.entities.rest

import com.example.moviesexampleapp.model.entities.rest.rest_entities.main_list.MoviesListDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepo: Callback<MoviesListDTO> {
    val api: MoviesApi by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(MoviesApi::class.java)
    }

    override fun onResponse(call: Call<MoviesListDTO>, response: Response<MoviesListDTO>) {}
    override fun onFailure(call: Call<MoviesListDTO>, t: Throwable) {}
}
package com.example.moviesexampleapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moviesexampleapp.model.entities.rest.rest_entities.main_list.MoviesListDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object MoviesListLoader {
    fun loadMoviesList(country: Int, genre: Int, type: String): MoviesListDTO? {
        try {
            val uri = URL("https://kinopoiskapiunofficial.tech/api/v2.2/films?countries=${country}&genres=${genre}&order=RATING&type=${type}&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000&page=1")

            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = "GET"
                    addRequestProperty(
                        "X-API-KEY", "09b396a2-9c2e-4baa-aec7-809851f46a7d"
                    )
                    readTimeout = 10000
                }
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, MoviesListDTO::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }

        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}
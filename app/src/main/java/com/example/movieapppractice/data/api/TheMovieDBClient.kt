package com.example.movieapppractice.data.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


//we dont need to instantiate it when we use it


//add constant variables
const val API_KEY = "56d4f22d94835618754648f89dfea0b6"
const val BASE_URL = "http://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
const val FIRST_PAGE= 1
const val POST_PER_PAGE = 20

//http://api.themoviedb.org/3/movie/popular?api_key=56d4f22d94835618754648f89dfea0b6&page=1
//http://api.themoviedb.org/3/movie/299534?api_key=56d4f22d94835618754648f89dfea0b6
//this could be the base url for retrofit //http://api.themoviedb.org/3/
// to get poster url : //https://image.tmdb.org/t/p/w342/or06FN3Dka5tukK1e9sl16pB3iy.jpg

object TheMovieDBClient {

    //we will us an intercepter to put our API key in the url

    fun getClient(): TheMovieDBInterface{
        /* Interceptor take only one argument which is a lambda function so parenthesis can be omitted */
        val requestInterceptor = Interceptor { chain ->

            val url : HttpUrl = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request : Request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)//explicitly return a value from whit @ annotation. lambda always returns the value of the lambda

        }


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)

    }


}
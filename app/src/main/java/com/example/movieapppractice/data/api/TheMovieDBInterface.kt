package com.example.movieapppractice.data.api

import com.example.movieapppractice.data.valObject.MovieDetails
import com.example.movieapppractice.data.valObject.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {
//

    //http://api.themoviedb.org/3/movie/popular?api_key=56d4f22d94835618754648f89dfea0b6&page=1
    //http://api.themoviedb.org/3/movie/299534?api_key=56d4f22d94835618754648f89dfea0b6
    //this could be the base url for retrofit //http://api.themoviedb.org/3/


    //TO USE RETROFIT USE RETROFIT ANNOTATION
    // tell reftrofit that this id is the movie id using annotion @path movie id
    //it returns a type of MovieDetails; Single is a type of observable in reactivex
    //there are two key componenets in reactivex observe and observable
    //observer does is the counter part of obsservable it recieves the data emited by observable
    //observable does some work and emits data
    //we are using Single because we do not need multiple data just a single
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id: Int): Single<MovieDetails>


    /*
    tell retrofit that the page is the page in the link by using @Query
    and it will return a  Single Observable of type MovieResponse

     */
    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>


}
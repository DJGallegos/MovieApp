package com.example.movieapppractice.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.example.movieapppractice.data.api.TheMovieDBInterface
import com.example.movieapppractice.data.repository.MovieDetailsNetworkDataSource
import com.example.movieapppractice.data.repository.NetworkState
import com.example.movieapppractice.data.valObject.MovieDetails
import io.reactivex.disposables.CompositeDisposable

//apiService is the movie db interface
//this is where you cash live data in local storage

class MovieDetailsRepository (private val apiService: TheMovieDBInterface){

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails>{

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMoveResponse //return live data
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }



}
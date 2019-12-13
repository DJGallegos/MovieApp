package com.example.movieapppractice.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapppractice.data.repository.NetworkState
import com.example.movieapppractice.data.valObject.MovieDetails
import io.reactivex.disposables.CompositeDisposable
/*
    it extends view model
    2.5 video
 */

class SingleMovieViewModel (private val movieRepository :MovieDetailsRepository, movieId:Int):ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }
//onCleared is called when the activity or fragment is destroyed
    override fun onCleared(){
        super.onCleared()
        compositeDisposable.dispose()
    }
}
package com.example.movieapppractice.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapppractice.data.api.TheMovieDBInterface
import com.example.movieapppractice.data.valObject.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


//here we call our api using RxJava
//our api will return the movie details
//then we will add movie details in live data
//CompositeDisposable will dispose our api calls
//when we want to dispose our rxjava thread we can use CompositeDisposable

class MovieDetailsNetworkDataSource  (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable){


    //
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState       //with this get , no need to implement get function to get network state

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMoveResponse: LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int){
        _networkState.postValue(NetworkState.LOADING)

        try{//will return a single observable
            //Schedular is a observer
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io()).subscribe(
                {_downloadedMovieDetailsResponse.postValue(it)
                    _networkState.postValue(NetworkState.LOADED)},
                {_networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDetailsDataSource", it.message)}
            ))
        }
        catch(e: Exception){
            Log.e("MovieDetailsDataSource", e.message)
        }
    }

}
package com.example.movieapppractice.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieapppractice.data.api.TheMovieDBInterface
import com.example.movieapppractice.data.valObject.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable

class MovieDataSourceFactory(private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create():DataSource<Int, Movie>{
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue((movieDataSource))
        return movieDataSource
    }




}

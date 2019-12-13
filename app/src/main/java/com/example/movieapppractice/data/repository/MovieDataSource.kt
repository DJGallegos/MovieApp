package com.example.movieapppractice.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movieapppractice.data.api.FIRST_PAGE
import com.example.movieapppractice.data.api.TheMovieDBInterface
import com.example.movieapppractice.data.valObject.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/*
we use PageKeyDataSource to load data based on Page number

apiService : TheMovieDBInterface is used to pull the information and compositeDisposable will get rid of any information no longer in use
 */

class MovieDataSource(private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable)
    :PageKeyedDataSource<Int, Movie>(){

    private var page = FIRST_PAGE

    val networkState : MutableLiveData<NetworkState> = MutableLiveData()







    /*
    loadIntial is to load the initial data
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue((NetworkState.LOADING))
        compositeDisposable.add(
            apiService.getPopularMovie(page).subscribeOn(Schedulers.io()).subscribe(
                {
                    callback.onResult(it.movieList, null, page+1)
                    networkState.postValue(NetworkState.LOADED)
                }, {
                    networkState.postValue((NetworkState.ERROR))
                    Log.e("MovieDataSource", it.message)
                }
            )
        )

    }
    /*
    loadAfter is to load the next page
     */

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        networkState.postValue((NetworkState.LOADING))
        compositeDisposable.add(
            apiService.getPopularMovie(params.key).subscribeOn(Schedulers.io()).subscribe(
                {
                   if(it.totalPages >= params.key)
                   {
                        callback.onResult(it.movieList, params.key+1)
                       networkState.postValue(NetworkState.LOADED)
                   }else{
                        networkState.postValue(NetworkState.ENDOFLIST)
                   }
                }, {
                    networkState.postValue((NetworkState.ERROR))
                    Log.e("MovieDataSource", it.message)
                }
            )
        )
    }
    //to load the previous page but we dont need to do anything here because the recycler view hold our previous data
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }


}
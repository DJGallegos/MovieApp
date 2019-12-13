package com.example.movieapppractice.data.repository

enum class Status{  // Enumerations in Kotlin are data types that hold a set of constants.
    RUNNING,
    SUCCESS,
    FAILED
}



class NetworkState (val status: Status, val msg: String) {
    //you used companion object when you want something to be static
    companion object{
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST:NetworkState

/*
tells us the status of the network
 */

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")

            LOADING = NetworkState(Status.RUNNING, "Running")

            ERROR = NetworkState(Status.FAILED, "Something went wrong")

            ENDOFLIST= NetworkState(Status.FAILED, "you have reached the end")

        }
    }




}
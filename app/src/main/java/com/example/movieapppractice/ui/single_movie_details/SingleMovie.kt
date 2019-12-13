package com.example.movieapppractice.ui.single_movie_details

/*
Daniel J. Gallegos
Accessed:
12/5/2019
Oxcode tutorial Moview app
https://https://www.youtube.com/watch?v=eAsniVT8lXs&list=PLRRNzqzbPLd906bPH-xFz9Oy2IcjqVWCH&index=2
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Dataset
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapppractice.R
import com.example.movieapppractice.data.api.POSTER_BASE_URL
import com.example.movieapppractice.data.api.TheMovieDBClient
import com.example.movieapppractice.data.api.TheMovieDBInterface
import com.example.movieapppractice.data.repository.NetworkState
import com.example.movieapppractice.data.valObject.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel:SingleMovieViewModel
    private lateinit var movieRepository : MovieDetailsRepository




    lateinit var myDataset : Array<String>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)




        val movieId:Int = intent.getIntExtra("id", 1)
    //This where the api Call is done
        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)
        //this is where it is stored

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
         bindUI(it)//updates the ui when any changes happen to the live data
        })

        viewModel.networkState.observe(this, Observer{
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    fun bindUI (it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + "minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)

        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
            Glide.with(this)//used to show poster into our image view
                .load(moviePosterURL)
                .into(iv_movie_poster)

    }

    private fun getViewModel(movieId: Int):SingleMovieViewModel{
        return ViewModelProviders.of(this, object: ViewModelProvider.Factory{
            override fun <T: ViewModel?>create(modelClass: Class<T>):T{
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]

    }
}

package com.mobg5.filmbox.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobg5.filmbox.R
import com.mobg5.filmbox.data.model.ResultsMovies
import com.mobg5.filmbox.data.model.UserListMovies

/*
Grid of movies in the Explore section
 */
@BindingAdapter("listMovie")
fun bindExploreRecyclerView(recyclerView: RecyclerView, data: List<ResultsMovies>?) {
    val adapter = recyclerView.adapter as ExploreMoviesGridAdapter
    adapter.submitList(data)
}

/*
Grid for the movies list in the profile section
 */
@BindingAdapter("listMovies")
fun bindingUserMoviesList(recyclerView: RecyclerView, data: List<ResultsMovies>?) {
    val adapter = recyclerView.adapter as UserMoviesListAdapter
    adapter.submitList(data)
}

/*
Horizontal grid for the "Upcoming Movies" category
 */
@BindingAdapter("listUpcoming")
fun bindHomeRecyclerView(recyclerView: RecyclerView, data: List<ResultsMovies>?) {
    val adapter = recyclerView.adapter as UpcomingMovieGridAdapter
    adapter.submitList(data)
}

/*
Horizontal grid for the "Popular Movies" category
 */
@BindingAdapter("listPopular")
fun bindHomePopularRecyclerView(recyclerView: RecyclerView, data: List<ResultsMovies>?) {
    val adapter = recyclerView.adapter as PopularMovieGridAdapter
    adapter.submitList(data)
}

/*
Horizontal grid for the "Top Rated" category
 */
@BindingAdapter("listTopRated")
fun bindHomeTopRatedRecyclerView(recyclerView: RecyclerView, data: List<ResultsMovies>?) {
    val adapter = recyclerView.adapter as TopRatedMovieGridAdapter
    adapter.submitList(data)
}

/*
Horizontal grid for the "Now In Theater" category
 */
@BindingAdapter("listNowPlaying")
fun bindHomeNowPlayingRecyclerView(recyclerView: RecyclerView, data: List<ResultsMovies>?) {
    val adapter = recyclerView.adapter as NowPlayingMovieGridAdapter
    adapter.submitList(data)
}

/*
Grid of results from the search bar
 */
@BindingAdapter("listResults")
fun bindSearchResultsRecyclerView(recyclerView: RecyclerView, data: List<ResultsMovies>?) {
    val adapter = recyclerView.adapter as SearchResultsGridAdapter
    adapter.submitList(data)
}

/*
Grid of Lists of movies lists in the profile section
 */
@BindingAdapter("userLists")
fun bindUserAllMovieListRecyclerView(recyclerView: RecyclerView, data: List<UserListMovies>?) {
    val adapter = recyclerView.adapter as UserAllMovieListAdapter
    adapter.submitList(data)
}

/*
Grid of clickable lists
 */
@BindingAdapter("checkboxLists")
fun bindCheckboxListsRecyclerView(recyclerView: RecyclerView, data: List<UserListMovies>?) {
    val adapter = recyclerView.adapter as AddToListGridAdapter
    adapter.submitList(data)
}

/*
For each movie poster in the application
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.movie_image_loading)
                .error(R.drawable.movie_image_loading))
            .into(imgView)
    }
}
package com.mobg5.filmbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobg5.filmbox.data.model.ResultsMovies
import com.mobg5.filmbox.databinding.HorizontalScrollPopularBinding

class PopularMovieGridAdapter(private val onClickListener: OnClickListener):
    ListAdapter<ResultsMovies, PopularMovieGridAdapter.ResultsMoviesViewHolder>(DiffCallback) {

    class ResultsMoviesViewHolder(private var binding: HorizontalScrollPopularBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultsMovies: ResultsMovies) {
            binding.popular = resultsMovies
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ResultsMovies>() {
        override fun areItemsTheSame(oldItem: ResultsMovies, newItem: ResultsMovies): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ResultsMovies, newItem: ResultsMovies): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ResultsMoviesViewHolder {
        return ResultsMoviesViewHolder(HorizontalScrollPopularBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ResultsMoviesViewHolder, position: Int) {
        val resultsMovies = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(resultsMovies)
        }
        holder.bind(resultsMovies)
    }

    class OnClickListener(val clickListener: (resultsMovies: ResultsMovies) -> Unit) {
        fun onClick(resultsMovies: ResultsMovies) = clickListener(resultsMovies)
    }

}

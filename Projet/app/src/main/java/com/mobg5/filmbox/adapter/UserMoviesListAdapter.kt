package com.mobg5.filmbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobg5.filmbox.data.model.ResultsMovies
import com.mobg5.filmbox.databinding.ListOfMoviesBinding

class UserMoviesListAdapter(private val onClickListener: OnClickListener):
    ListAdapter<ResultsMovies, UserMoviesListAdapter.ResultsMoviesViewHolder>(DiffCallback) {

    class ResultsMoviesViewHolder(private var binding: ListOfMoviesBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: ResultsMovies) {
            binding.movie = movie
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
        return ResultsMoviesViewHolder(ListOfMoviesBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ResultsMoviesViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(movie)
    }

    class OnClickListener(val clickListener: (movies: ResultsMovies) -> Unit) {
        fun onClick(movie: ResultsMovies) = clickListener(movie)
    }

}

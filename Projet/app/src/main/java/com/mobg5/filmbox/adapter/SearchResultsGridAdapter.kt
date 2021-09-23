package com.mobg5.filmbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobg5.filmbox.data.model.ResultsMovies
import com.mobg5.filmbox.databinding.ItemSearchResultsBinding

class SearchResultsGridAdapter(private val onClickListener: OnClickListener):
    ListAdapter<ResultsMovies, SearchResultsGridAdapter.ResultsMoviesViewHolder>(DiffCallback) {

    class ResultsMoviesViewHolder(private var binding: ItemSearchResultsBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultsMovies: ResultsMovies) {
            binding.results = resultsMovies
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
        return ResultsMoviesViewHolder(ItemSearchResultsBinding.inflate(LayoutInflater.from(parent.context)))
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

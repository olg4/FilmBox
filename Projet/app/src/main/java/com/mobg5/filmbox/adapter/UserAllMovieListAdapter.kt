package com.mobg5.filmbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobg5.filmbox.data.model.UserListMovies
import com.mobg5.filmbox.databinding.ListOfMovieListBinding


class UserAllMovieListAdapter(private val onClickListener: OnClickListener):
    ListAdapter<UserListMovies, UserAllMovieListAdapter.UserListMoviesViewHolder>(DiffCallback) {

    class UserListMoviesViewHolder(private var binding: ListOfMovieListBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: UserListMovies) {
            binding.list = list
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<UserListMovies>() {
        override fun areItemsTheSame(oldItem: UserListMovies, newItem: UserListMovies): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserListMovies, newItem: UserListMovies): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListMoviesViewHolder {
        return UserListMoviesViewHolder(ListOfMovieListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserListMoviesViewHolder, position: Int) {
        val list = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(list)
        }
        holder.bind(list)
    }

    class OnClickListener(val clickListener: (list: UserListMovies) -> Unit) {
        fun onClick(list: UserListMovies) = clickListener(list)
    }
}
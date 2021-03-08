package com.dicoding.helfani.mymovielist.ui.favorite.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.databinding.ItemsFavoriteMovieBinding
import com.dicoding.helfani.mymovielist.ui.detail.DetailMovieActivity

class FavoriteMovieAdapter(private val callback: FavoriteMovieFragmentCallback) : PagedListAdapter<MovieEntity, FavoriteMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsFavoriteMovieBinding = ItemsFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsFavoriteMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    fun getSwipeData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    inner class MovieViewHolder(private val binding: ItemsFavoriteMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                tvTitle.text = movie.title
                tvGenre.text = movie.genre
                tvDirector.text = movie.director
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie.movieId)
                    itemView.context.startActivity(intent)
                }
                imgShare.setOnClickListener { callback.onShareClick(movie) }
                Glide.with(itemView.context)
                        .load(movie.imagePath)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error((R.drawable.ic_error))
                        .into(imagePoster)
            }

        }
    }
}
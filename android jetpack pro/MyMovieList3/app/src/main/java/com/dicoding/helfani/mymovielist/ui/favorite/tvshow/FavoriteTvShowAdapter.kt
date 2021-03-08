package com.dicoding.helfani.mymovielist.ui.favorite.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.databinding.ItemsFavoriteTvshowBinding
import com.dicoding.helfani.mymovielist.ui.detail.DetailTvShowActivity

class FavoriteTvShowAdapter(private val callback: FavoriteTVShowFragmentCallback) : PagedListAdapter<TvShowEntity, FavoriteTvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.tvShowId == newItem.tvShowId
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsFavoriteTvshowBinding = ItemsFavoriteTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsFavoriteTvshowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    fun getSwipeData(swipedPosition: Int): TvShowEntity? = getItem(swipedPosition)

    inner class TvShowViewHolder(private val binding: ItemsFavoriteTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShow.title
                tvGenre.text = tvShow.genre
                tvCreator.text = tvShow.creator
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvShowActivity::class.java)
                    intent.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, tvShow.tvShowId)
                    itemView.context.startActivity(intent)
                }
                imgShare.setOnClickListener { callback.onShareClick(tvShow) }
                Glide.with(itemView.context)
                    .load(tvShow.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                            .error((R.drawable.ic_error))
                    .into(imagePoster)
            }
        }
    }
}
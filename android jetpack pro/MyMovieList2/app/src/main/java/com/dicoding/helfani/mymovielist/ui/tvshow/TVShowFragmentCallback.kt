package com.dicoding.helfani.mymovielist.ui.tvshow

import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity

interface TVShowFragmentCallback {
    fun onShareClick(tvShow: TvShowEntity)

}

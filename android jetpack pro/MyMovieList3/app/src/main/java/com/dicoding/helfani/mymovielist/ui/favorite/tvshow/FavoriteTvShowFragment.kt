package com.dicoding.helfani.mymovielist.ui.favorite.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.databinding.FragmentFavoriteTvShowBinding
import com.dicoding.helfani.mymovielist.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoriteTvShowFragment : Fragment(), FavoriteTVShowFragmentCallback {

    private var _fragmentFavoriteTvShowBinding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _fragmentFavoriteTvShowBinding

    private lateinit var viewModel: FavoriteTvShowViewModel
    private lateinit var adapter: FavoriteTvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentFavoriteTvShowBinding = FragmentFavoriteTvShowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteTvShow)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteTvShowViewModel::class.java]

            adapter = FavoriteTvShowAdapter(this)
            binding?.progressBar?.visibility = View.VISIBLE
            viewModel.getFavorites().observe(this, { tvShows ->
                binding?.progressBar?.visibility = View.GONE
                adapter.submitList(tvShows)
            })

            binding?.rvFavoriteTvShow?.layoutManager = LinearLayoutManager(context)
            binding?.rvFavoriteTvShow?.setHasFixedSize(true)
            binding?.rvFavoriteTvShow?.adapter = adapter
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val tvShowEntity = adapter.getSwipeData(swipedPosition)
                tvShowEntity?.let { viewModel.setFavorite(it)}

                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v ->
                    tvShowEntity?.let { viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }

    })

    override fun onShareClick(tvShow: TvShowEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setType(mimeType)
                    .setChooserTitle("Bagikan aplikasi ini sekarang.")
                    .setText(resources.getString(R.string.share_text, tvShow.title))
                    .startChooser()
        }
    }
}
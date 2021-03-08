package com.dicoding.helfani.mymovielist.ui.favorite.movie

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
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.databinding.FragmentFavoriteMovieBinding
import com.dicoding.helfani.mymovielist.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoriteMovieFragment : Fragment(), FavoriteMovieFragmentCallback {

    private var _fragmentFavoriteMovieBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _fragmentFavoriteMovieBinding

    private lateinit var viewModel: FavoriteMovieViewModel
    private lateinit var adapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentFavoriteMovieBinding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteMovie)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

            adapter = FavoriteMovieAdapter(this)
            binding?.progressBar?.visibility = View.VISIBLE
            viewModel.getFavorites().observe(this, { movies ->
                binding?.progressBar?.visibility = View.GONE
                adapter.submitList(movies)
            })

            binding?.rvFavoriteMovie?.layoutManager = LinearLayoutManager(context)
            binding?.rvFavoriteMovie?.setHasFixedSize(true)
            binding?.rvFavoriteMovie?.adapter = adapter

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
                val movieEntity = adapter.getSwipeData(swipedPosition)
                movieEntity?.let { viewModel.setFavorite(it)}

                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_SHORT)
                snackbar.setAction(R.string.message_ok) { v ->
                    movieEntity?.let { viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }
    })

    override fun onShareClick(movie: MovieEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setType(mimeType)
                    .setChooserTitle("Bagikan aplikasi ini sekarang.")
                    .setText(resources.getString(R.string.share_text, movie.title))
                    .startChooser()
        }
    }

}
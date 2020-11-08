package com.example.perfect_movie

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfect_movie.redux.AppState
import com.example.perfect_movie.tmdb_api.TMDBMovie
import kotlinx.android.synthetic.main.fragment_upcoming.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import org.reduxkotlin.StoreSubscription

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpcomingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpcomingFragment : Fragment() {
    private lateinit var storeSubscription: StoreSubscription
    private var adapter = MoviesAdapter()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_upcoming, container, false)
        adapter.clickListener = { view: View, movie: TMDBMovie ->
            val intent = Intent(activity, MovieDetailActivity::class.java)

            intent.putExtra(MovieDetailActivity.EXTRA_PARAM_MOVIE, movie)

            val p1 = Pair.create<View, String>(
                view.backdropIV,
                MovieDetailActivity.IMAGE_ANIMATION_NAME
            )
            val p2 = Pair.create<View, String>(
                view.titleTV,
                MovieDetailActivity.TITLE_ANIMATION_NAME
            )
            val activityOptions: ActivityOptions =
                ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    p1, p2
                )

            startActivity(intent, activityOptions.toBundle())
        }
        _view.upcomingRV.layoutManager = LinearLayoutManager(activity)
        _view.upcomingRV.setHasFixedSize(true)
        _view.upcomingRV.adapter = adapter

        storeSubscription = store.subscribe { render(store.state) }
        render(store.state)
        return _view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpcomingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpcomingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun render(state: AppState) {
        if (state.isUpcomingLoadingItems) showLoading()
        else hideLoading()

        adapter.submitList(state.upcomingMovies)
    }

    private fun hideLoading() {
        activity?.runOnUiThread {
            view?.upcomingPB?.visibility = View.INVISIBLE
        }
    }

    private fun showLoading() {
        activity?.runOnUiThread {
            view?.upcomingPB?.visibility = View.VISIBLE
        }
    }
}
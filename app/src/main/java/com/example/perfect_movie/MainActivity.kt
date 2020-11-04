package com.example.perfect_movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfect_movie.redux.AppState
import com.example.perfect_movie.redux.MoviesCategory
import com.example.perfect_movie.redux.NetworkThunks
import com.example.perfect_movie.redux.reducer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import org.reduxkotlin.*

val networkThunks = NetworkThunks(Dispatchers.IO)
val store by lazy {
    createThreadSafeStore(
        reducer, AppState.INITIAL_STATE,
        compose(
            listOf(//presenterEnhancer(Dispatchers.Main),
                applyMiddleware(createThunkMiddleware())
            )
        )
    )
//                //uiMiddleware(networkThunks, Dispatchers.Main),
//                //navigationMiddleware(navigator),
//                //loggerMiddleware,
//                //settingsMiddleware(localStorageSettingsRepository, networkContext)))))
}

class MainActivity : AppCompatActivity() {
    private lateinit var storeSubscription: StoreSubscription
    private var adapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesRV.layoutManager = LinearLayoutManager(this)
        moviesRV.setHasFixedSize(true)
        moviesRV.adapter = adapter

        storeSubscription = store.subscribe { render(store.state) }
        store.dispatch(networkThunks.fetchItems(MoviesCategory.UPCOMING, 1));
        render(store.state)
    }

    private fun render(state: AppState) {
        adapter.submitList(state.upcomingMovies)
    }
}
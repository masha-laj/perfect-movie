package com.example.perfect_movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.perfect_movie.redux.AppState
import com.example.perfect_movie.redux.MoviesCategory
import com.example.perfect_movie.redux.NetworkThunks
import com.example.perfect_movie.redux.reducer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.compose
import org.reduxkotlin.createThreadSafeStore
import org.reduxkotlin.createThunkMiddleware


val networkThunks = NetworkThunks(Dispatchers.IO)
val store by lazy {
    createThreadSafeStore(
        reducer, AppState.INITIAL_STATE,
        compose(
            listOf(
                applyMiddleware(createThunkMiddleware())
            )
        )
    )
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigation, navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        store.dispatch(networkThunks.fetchItems(MoviesCategory.RATED, 1));
        store.dispatch(networkThunks.fetchItems(MoviesCategory.UPCOMING, 1));
    }
}
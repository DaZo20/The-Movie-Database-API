package com.example.themoviedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.themoviedatabase.databinding.ActivityMainBinding
import com.example.themoviedatabase.movie.presentation.view.favorite.FavoriteMoviesFragment
import com.example.themoviedatabase.movie.presentation.view.popular.MovieFragment
import com.example.themoviedatabase.movie.presentation.viewmodel.MoviesViewModel
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    lateinit var mainBinding: ActivityMainBinding
    private val movieFragment: Fragment by lazy { MovieFragment.newInstance() }
    private val movieFavoriteFragment: Fragment by lazy { FavoriteMoviesFragment.newInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        initViews(savedState = savedInstanceState)
        mainBinding.bottomNavView.itemIconTintList = null
    }

    private fun initViews(savedState: Bundle?) {
        with(mainBinding.bottomNavView) {
            setOnItemSelectedListener(this@MainActivity)
            selectedItemId = savedState?.getInt("opened_fragment") ?: R.id.navigation_popular_movies
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("opened_fragment", mainBinding.bottomNavView.selectedItemId)
    }
    private fun replaceWithMovieFragment() {
        supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.fragment_view, movieFragment).commit()
    }
    private fun replaceWithFavoriteMovieFragment() {
        supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.fragment_view, movieFavoriteFragment).commit()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navigation_popular_movies -> {
                item.setIcon(R.drawable.popular_selector)
                replaceWithMovieFragment()
                return true
            }
            R.id.navigation_favorite_movies -> {
                item.setIcon(R.drawable.favorite_selector)
                replaceWithFavoriteMovieFragment()
                return true
            }
        }
        return false
    }


}
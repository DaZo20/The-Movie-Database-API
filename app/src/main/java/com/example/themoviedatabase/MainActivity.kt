package com.example.themoviedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.themoviedatabase.databinding.ActivityMainBinding
import com.example.themoviedatabase.movie.data.api.MovieService
import com.example.themoviedatabase.movie.presentation.view.MovieFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    private val movieFragment: Fragment by lazy { MovieFragment.newInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        onReplaceFragment(MovieFragment())

    }
    private fun onReplaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_view, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
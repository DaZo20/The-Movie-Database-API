package com.example.themoviedatabase.movie.data.repository

import android.util.Log
import androidx.room.PrimaryKey
import com.example.themoviedatabase.movie.data.datasource.MovieDataSource
import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.data.model.MovieDto
import com.example.themoviedatabase.movie.data.model.MoviesDto
import com.example.themoviedatabase.movie.domain.model.Movies
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

//
// Created by DaZo20 on 05/03/2023.
//

private const val DEFAULT_INT_VALUE = 0
private const val DEFAULT_DOUBLE_VALUE = 0.0
private const val DEFAULT_STRING_VALUE = "_"

class TMDBMovieRepositoryTest {

    private lateinit var sut: TMDBMovieRepository

    @Before
    fun setUp() {
        sut = TMDBMovieRepository.apply {
            moviesRemoteDataSource = mock(MovieDataSource.Remote::class.java)
            moviesLocalDataSource = mock(MovieDataSource.Local::class.java)
        }
    }

    @Test
    fun `When the 'FavoriteMovies' are requested to the database, 'FavoriteMovies' are returned`() =
        runTest {
            //Given
            `when`(sut.moviesLocalDataSource.fetchAllFavoriteMovies()).thenReturn(getDummyFavoriteMoviesList())
            //When
            val list: List<FavoriteMovieEntity> = sut.getFavoriteMovies()
            //Then
            assertTrue(list.isNotEmpty())
        }

    @Test
    fun `When the 'API' returns all the content of the page, the following page is obtained`() =
        runTest {
            //Given
            `when`(sut.moviesRemoteDataSource.getAllPopularMoviesNextPage(1)).thenReturn(
                getDummyMoviesDtoResult()
            )
            //When
            val result: Result<Movies> = sut.getPopularMoviesNextPage()
            //Then
            assertTrue(result.isSuccess && result.getOrNull()?.results?.isNotEmpty() == true)
        }

    @Test
    fun `When movie is searched, and the API response is successful a list of 'Movie' is returned`() =
        runTest {
            //Given
            `when`(sut.moviesRemoteDataSource.getSearchedMovies("Test")).thenReturn(
                getDummyMoviesDtoResult()
            )
            //When
            val result: Result<Movies> = sut.getPopularMoviesByName("Test")
            //Then
            assertTrue(result.isSuccess && result.getOrNull()?.results?.isNotEmpty() == true)
        }

    @Test
    fun `When all popular movies are requested, and the API response is successful a list of 'Popular Movies' is returned`() =
        runTest {
            //Given
            `when`(sut.moviesRemoteDataSource.getAllPopularMovies()).thenReturn(
                getDummyMoviesDtoResult()
            )
            //When
            val result: Result<Movies> = sut.getAllPopularMovies()
            //Then
            assertTrue(result.isSuccess && result.getOrNull()?.results?.isNotEmpty() == true)
        }

    private fun getDummyMoviesDtoResult(): Result<MoviesDto?> =
        Result.success(getDummyMoviesDto())

    private fun getDummyMoviesDto(): MoviesDto =
        MoviesDto(
            page = DEFAULT_INT_VALUE,
            results = getDummyMoviesDtoList(),
            total_pages = DEFAULT_INT_VALUE,
            total_results = DEFAULT_INT_VALUE,
        )

    private fun getDummyMoviesDtoList(): List<MovieDto> = listOf(getDummyMovieDto())

    private fun getDummyMovieDto(): MovieDto =
        MovieDto(
            adult = false,
            backdrop_path = DEFAULT_STRING_VALUE,
            genre_ids = listOf(DEFAULT_INT_VALUE),
            id = DEFAULT_INT_VALUE,
            original_language = DEFAULT_STRING_VALUE,
            original_title = DEFAULT_STRING_VALUE,
            overview = DEFAULT_STRING_VALUE,
            popularity = DEFAULT_DOUBLE_VALUE,
            poster_path = DEFAULT_STRING_VALUE,
            release_date = DEFAULT_STRING_VALUE,
            title = DEFAULT_STRING_VALUE,
            video = false,
            vote_average = DEFAULT_DOUBLE_VALUE,
            vote_count = DEFAULT_INT_VALUE
        )

    private fun getDummyFavoriteMoviesList(): List<FavoriteMovieEntity> =
        listOf(
            FavoriteMovieEntity(
                id = DEFAULT_INT_VALUE,
                title = DEFAULT_STRING_VALUE,
                poster_path = DEFAULT_STRING_VALUE,
                vote_average = DEFAULT_STRING_VALUE,
                release_date = DEFAULT_STRING_VALUE,
                overview = DEFAULT_STRING_VALUE,
            )
        )
}
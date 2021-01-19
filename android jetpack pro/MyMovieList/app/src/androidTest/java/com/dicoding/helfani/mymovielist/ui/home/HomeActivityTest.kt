package com.dicoding.helfani.mymovielist.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.utils.DataMovies
import org.junit.Before
import org.junit.Test

class HomeActivityTest {
    private val dataMovie = DataMovies.generateMovieList()
    private val dataTvShow = DataMovies.generateTvShowList()

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataMovie.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title)).check(matches(withText(dataMovie[0].title)))
        onView(withId(R.id.tv_date)).check(matches(withText(dataMovie[0].date)))
        onView(withId(R.id.tv_genre)).check(matches(withText(dataMovie[0].genre)))
        onView(withId(R.id.tv_director)).check(matches(withText(dataMovie[0].director)))
        onView(withId(R.id.tv_overview)).check(matches(withText(dataMovie[0].overview)))
    }

    @Test
    fun loadTvShow() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataTvShow.size))
    }
}
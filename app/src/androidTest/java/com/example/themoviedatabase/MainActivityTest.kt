package com.example.themoviedatabase

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

//
// Created by DaZo20 on 05/03/2023.
//
class MainActivityTest {

    private lateinit var activityScenario : ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun whenButtonIsClicked_aToastIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.fav_button))
            .perform(ViewActions.click())
    }
}
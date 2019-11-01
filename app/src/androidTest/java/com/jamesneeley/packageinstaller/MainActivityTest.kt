package com.jamesneeley.packageinstaller

import org.junit.After
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.action.ViewActions.click

import org.junit.Before
import org.junit.Rule
import org.junit.Test

import androidx.test.rule.ActivityTestRule


class MainActivityTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private var mainActivity: MainActivity? = null

    @Before
    fun setUp() {
        mainActivity = mActivityRule.activity
    }

    @Test
    fun test_install_package_button_click() {
        onView(withId(R.id.mainActivityDownloadPackagesButton)).perform(click())
        onView(withId(R.id.mainActivityDownloadPackagesButton)).perform(click())
        onView(withId(R.id.mainActivityDownloadPackagesButton)).perform(click())
        onView(withId(R.id.mainActivityDownloadPackagesButton)).perform(click())
        onView(withId(R.id.mainActivityDownloadPackagesButton)).perform(click())
    }

    @After
    fun tearDown() {
    }
}
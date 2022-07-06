package com.example.navigationdrawercompose.ui.screen

import com.example.navigationdrawercompose.R

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {

    object Home : NavDrawerItem("home", R.drawable.ic_baseline_home_24, "Home")
    object Music : NavDrawerItem("music", R.drawable.ic_baseline_queue_music_24, "Music")
    object Movies : NavDrawerItem("movies", R.drawable.ic_baseline_movie_24, "Movies")
    object Books : NavDrawerItem("books", R.drawable.ic_baseline_menu_book_24, "Books")
    object Profile : NavDrawerItem("profile", R.drawable.ic_baseline_person_2_24, "Profile")
    object Settings : NavDrawerItem("settings", R.drawable.ic_baseline_settings_24, "Settings")
}
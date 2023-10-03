package com.ostreet.cmp.koin.navigation

interface NavController {
    fun navigate(route: String)
    fun onBackPressed()
}

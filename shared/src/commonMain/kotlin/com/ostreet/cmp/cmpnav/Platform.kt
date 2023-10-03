package com.ostreet.cmp.cmpnav

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
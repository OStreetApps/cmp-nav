package com.ostreet.cmp.cmpnav.di

import com.ostreet.cmp.cmpnav.repo.SettingsRepo
import com.ostreet.cmp.cmpnav.repo.SettingsRepoImpl
import com.ostreet.cmp.cmpnav.ui.AppViewModel
import com.ostreet.cmp.cmpnav.ui.route.logged_in.friends.FriendsNested
import com.ostreet.cmp.cmpnav.ui.route.logged_in.friends.FriendsNestedViewModel
import com.ostreet.cmp.cmpnav.ui.route.logged_in.friends.FriendsScreen
import com.ostreet.cmp.cmpnav.ui.route.logged_in.friends.FriendsViewModel
import com.ostreet.cmp.cmpnav.ui.route.logged_in.home.HomeNested
import com.ostreet.cmp.cmpnav.ui.route.logged_in.home.HomeNestedViewModel
import com.ostreet.cmp.cmpnav.ui.route.logged_in.home.HomeScreen
import com.ostreet.cmp.cmpnav.ui.route.logged_in.home.HomeViewModel
import com.ostreet.cmp.cmpnav.ui.route.logged_in.settings.SettingsScreen
import com.ostreet.cmp.cmpnav.ui.route.logged_in.settings.SettingsViewModel
import com.ostreet.cmp.cmpnav.ui.route.logged_out.login.LoginScreen
import com.ostreet.cmp.cmpnav.ui.route.logged_out.login.LoginViewModel
import com.ostreet.cmp.cmpnav.ui.route.logged_out.sign_up.SignUpScreen
import com.ostreet.cmp.cmpnav.ui.route.logged_out.sign_up.SignUpViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedModule = module {

    singleOf(::SettingsRepoImpl) { bind<SettingsRepo>() }

    single { AppViewModel(get()) }

    scope<LoginScreen> { scoped { LoginViewModel(get()) } }
    scope<SignUpScreen> { scoped { SignUpViewModel(get()) } }

    scope<HomeScreen> { scoped { HomeViewModel(get()) } }
    scope<HomeNested> { scoped { HomeNestedViewModel(get()) } }
    scope<FriendsScreen> { scoped { FriendsViewModel(get()) } }
    scope<FriendsNested> { scoped { FriendsNestedViewModel(get()) } }
    scope<SettingsScreen> { scoped { SettingsViewModel(get()) } }

}
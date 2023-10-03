package com.ostreet.cmp.koin.navigation

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

abstract class ScopedComponent : KoinScopeComponent {
    override val scope: Scope by lazy { createScope(this) }
}
package com.tab.countries.plugin

import com.tab.countries.MainDispatcherRule
import org.junit.Rule

abstract class BaseTestPlugin {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val testDispatcher get() = mainDispatcherRule.testDispatcher
}
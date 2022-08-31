package com.rrvieira.trendyt.ui

import com.rrvieira.trendyt.rules.MainDispatcherRule
import org.junit.Rule

open class BaseViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
}

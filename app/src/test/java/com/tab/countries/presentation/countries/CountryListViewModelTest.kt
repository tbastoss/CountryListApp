package com.tab.countries.presentation.countries

import app.cash.turbine.test
import com.tab.countries.domain.CountryRepository
import com.tab.countries.domain.model.Country
import com.tab.countries.plugin.BaseTestPlugin
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CountryListViewModelTest : BaseTestPlugin() {

    private lateinit var repository: CountryRepository

    private lateinit var viewModel: CountryListViewModel

    @Before
    fun setUp() {
        repository = mockk<CountryRepository>()
        viewModel = CountryListViewModel(countryRepository = repository)
    }

    @Test
    fun `test call to getCountryList on subscription to viewModel state and repository call failed`() = runTest {
        // Given
        val error = Exception()
        coEvery { repository.getCountries() } returns Result.failure(error)

        // When
        viewModel.state.test {
            //Then
            val viewState = awaitItem()
            Assert.assertFalse(viewState.isLoading)
            Assert.assertTrue(viewState.isError)
            coVerify(exactly = 1) { repository.getCountries() }
        }
    }

    @Test
    fun `test call to getCountryList on subscription to viewModel state`() = runTest {
        // Given
        val countryList = listOf(mockk<Country>())
        val expectedViewState = viewModel.state.value.copy(
            isLoading = false,
            isError = false,
            countryList = countryList
        )
        coEvery { repository.getCountries() } returns Result.success(countryList)

        // When
        viewModel.state.test {
            //Then
            Assert.assertEquals(expectedViewState, awaitItem())
            coVerify(exactly = 1) { repository.getCountries() }
        }
    }
}
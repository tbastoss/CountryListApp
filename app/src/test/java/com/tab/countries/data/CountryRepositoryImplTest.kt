package com.tab.countries.data

import com.tab.countries.data.mapper.CountryDataMapper
import com.tab.countries.data.remote.model.CountryResponse
import com.tab.countries.data.remote.service.CountryService
import com.tab.countries.domain.CountryRepository
import com.tab.countries.domain.model.Country
import com.tab.countries.plugin.BaseTestPlugin
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CountryRepositoryImplTest : BaseTestPlugin() {

    private lateinit var repository: CountryRepository

    private lateinit var service: CountryService

    private lateinit var mapper: CountryDataMapper

    @Before
    fun setUp() {
        service = mockk<CountryService>()
        mapper = mockk<CountryDataMapper>()
        repository = CountryRepositoryImpl(
            countryService = service,
            countryDataMapper = mapper,
            dispatcher = testDispatcher,
        )
    }

    @Test
    fun `test getCountries given server request fails`() = runTest {
        // Given
        val expectedError = Exception()
        coEvery { service.fetchCountries() } throws expectedError

        // When
        val result = repository.getCountries()

        // Then
        Assert.assertTrue(result.isFailure)
        coVerify { service.fetchCountries() }
        verify(exactly = 0) { mapper.fromModel(any()) }
    }

    @Test
    fun `test getCountries given mapper throws Exception`() = runTest {
        // Given
        val expectedError = Exception()
        val apiResponse = listOf(mockk<CountryResponse>())
        coEvery { service.fetchCountries() } returns apiResponse
        every { mapper.fromModel(apiResponse) } throws expectedError

        // When
        val result = repository.getCountries()

        // Then
        Assert.assertTrue(result.isFailure)
        coVerify { service.fetchCountries() }
        verify { mapper.fromModel(apiResponse) }
    }

    @Test
    fun `test getCountries given mapper is successful`() = runTest {
        // Given
        val apiResponse = listOf(mockk<CountryResponse>())
        val countryList = listOf(mockk<Country>())
        coEvery { service.fetchCountries() } returns apiResponse
        every { mapper.fromModel(apiResponse) } returns countryList

        // When
        val result = repository.getCountries()

        // Then
        Assert.assertTrue(result.isSuccess)
        Assert.assertTrue(result.getOrNull() == countryList)
        coVerify { service.fetchCountries() }
        verify { mapper.fromModel(apiResponse) }
    }
}
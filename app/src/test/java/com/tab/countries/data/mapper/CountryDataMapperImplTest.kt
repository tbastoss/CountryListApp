package com.tab.countries.data.mapper

import com.tab.countries.data.remote.model.CountryResponse
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

class CountryDataMapperImplTest {

    private lateinit var mapper: CountryDataMapper

    @Before
    fun setUp() {
        mapper = CountryDataMapperImpl()
    }

    @Test
    fun `test fromModel given country name is null`() {
        // Given
        val countryResponseItem = CountryResponse(
            name = null,
            region = "region",
            code = "code",
            capital = "capital",
            flag = "flag",
            currency = mockk(),
            language = mockk(),
        )

        // Then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            // When
            mapper.fromModel(listOf(countryResponseItem))
        }
    }

    @Test
    fun `test fromModel given country region is null`() {
        // Given
        val countryResponseItem = CountryResponse(
            name = "name",
            region = null,
            code = "code",
            capital = "capital",
            flag = "flag",
            currency = mockk(),
            language = mockk(),
        )

        // Then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            // When
            mapper.fromModel(listOf(countryResponseItem))
        }
    }

    @Test
    fun `test fromModel given country code is null`() {
        // Given
        val countryResponseItem = CountryResponse(
            name = "name",
            region = "region",
            code = null,
            capital = "capital",
            flag = "flag",
            currency = mockk(),
            language = mockk(),
        )

        // Then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            // When
            mapper.fromModel(listOf(countryResponseItem))
        }
    }

    @Test
    fun `test fromModel given country capital is null`() {
        // Given
        val countryResponseItem = CountryResponse(
            name = "name",
            region = "region",
            code = "code",
            capital = null,
            flag = "flag",
            currency = mockk(),
            language = mockk(),
        )

        // Then
        Assert.assertThrows(IllegalArgumentException::class.java) {
            // When
            mapper.fromModel(listOf(countryResponseItem))
        }
    }

    @Test
    fun `test fromModel given empty list`() {
        // Given
        val list = emptyList<CountryResponse>()

        // When
        val result = mapper.fromModel(list)

        //Then
        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun `test fromModel given api response list`() {
        // Given
        val countryResponseItem = CountryResponse(
            name = "name",
            region = "region",
            code = "code",
            capital = "capital",
            flag = "flag",
            currency = mockk(),
            language = mockk(),
        )
        val list = listOf(countryResponseItem)

        // When
        val result = mapper.fromModel(list)

        //Then
        Assert.assertEquals(countryResponseItem.name, result[0].name)
        Assert.assertEquals(countryResponseItem.region, result[0].region)
        Assert.assertEquals(countryResponseItem.code, result[0].code)
        Assert.assertEquals(countryResponseItem.capital, result[0].capital)
    }
}
package com.tab.countries.domain

import com.tab.countries.domain.model.Country

interface CountryRepository {
    suspend fun getCountries(): Result<List<Country>>
}
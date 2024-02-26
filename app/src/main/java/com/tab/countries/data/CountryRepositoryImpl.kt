package com.tab.countries.data

import com.tab.countries.data.mapper.CountryDataMapper
import com.tab.countries.data.remote.service.CountryService
import com.tab.countries.domain.CountryRepository
import com.tab.countries.domain.model.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CountryRepositoryImpl(
    private val countryService: CountryService,
    private val countryDataMapper: CountryDataMapper,
    private val dispatcher: CoroutineDispatcher,
) : CountryRepository {

    override suspend fun getCountries(): Result<List<Country>> {
        return withContext(dispatcher) {
            try {
                val countryResponseList = countryService.fetchCountries()
                val countryList = countryDataMapper.fromModel(countryResponseList)
                Result.success(countryList)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
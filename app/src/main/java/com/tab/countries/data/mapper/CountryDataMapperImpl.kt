package com.tab.countries.data.mapper

import com.tab.countries.data.remote.model.CountryResponse
import com.tab.countries.domain.model.Country
import kotlin.jvm.Throws

class CountryDataMapperImpl : CountryDataMapper {

    @Throws(IllegalArgumentException::class)
    override fun fromModel(modelList: List<CountryResponse>): List<Country> {
        return modelList.map { fromModel(it) }
    }

    @Throws(IllegalArgumentException::class)
    private fun fromModel(model: CountryResponse): Country {
        return Country(
            name = model.name ?: throw IllegalArgumentException("name cannot be null"),
            region = model.region ?: throw IllegalArgumentException("region cannot be null"),
            code = model.code ?: throw IllegalArgumentException("code cannot be null"),
            capital = model.capital ?: throw IllegalArgumentException("capital cannot be null"),
        )
    }
}
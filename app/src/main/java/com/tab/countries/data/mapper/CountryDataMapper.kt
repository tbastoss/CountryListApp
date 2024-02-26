package com.tab.countries.data.mapper

import com.tab.countries.data.remote.model.CountryResponse
import com.tab.countries.domain.model.Country
import kotlin.jvm.Throws

interface CountryDataMapper {
    @Throws(IllegalArgumentException::class)
    fun fromModel(modelList: List<CountryResponse>): List<Country>
}
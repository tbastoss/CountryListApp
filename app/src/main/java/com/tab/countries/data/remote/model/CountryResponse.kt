package com.tab.countries.data.remote.model

data class CountryResponse(
    val capital: String?,
    val code: String?,
    val currency: CurrencyResponse?,
    val flag: String?,
    val language: LanguageResponse?,
    val name: String?,
    val region: String?,
)

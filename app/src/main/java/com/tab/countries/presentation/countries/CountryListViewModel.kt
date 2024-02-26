package com.tab.countries.presentation.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tab.countries.domain.CountryRepository
import com.tab.countries.domain.model.Country
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val countryRepository: CountryRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> = _state.asStateFlow()
        .onSubscription { getCountryList() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ViewState())

    private var getCountryListJob: Job? = null
    fun getCountryList() {
        getCountryListJob?.cancel()
        getCountryListJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isError = false) }
            countryRepository.getCountries()
                .onSuccess { newCountryList ->
                    _state.update { viewState ->
                        viewState.copy(
                            isLoading = false,
                            countryList = newCountryList,
                            isError = false
                        )
                    }
                }
                .onFailure {
                    _state.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val countryList: List<Country> = emptyList(),
        val isError: Boolean = false,
    )
}
package com.tab.countries.presentation.countries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tab.countries.R
import com.tab.countries.databinding.ViewCountryListItemBinding
import com.tab.countries.domain.model.Country

class CountryListAdapter : ListAdapter<Country, CountryListAdapter.ViewHolder>(CountryDiffUtil()) {

    class CountryDiffUtil : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewCountryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = currentList[position]
        holder.bind(country)
    }

    inner class ViewHolder(
        private val binding: ViewCountryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            with(binding) {
                setCountryAndRegionText(country.name, country.region)
                countryCode.text = country.code
                countryCapital.text = country.capital
            }
        }

        private fun setCountryAndRegionText(countryName: String, region: String) {
            with(binding) {
                val countryAndRegionText = root.context.getString(
                    R.string.country_name_and_region,
                    countryName,
                    region
                )
                countryNameAndRegion.text = countryAndRegionText
            }
        }
    }
}
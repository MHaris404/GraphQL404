package com.mharis.grapql404.data

import com.apollographql.apollo3.ApolloClient
import com.mharis.CountriesQuery
import com.mharis.CountryQuery
import com.mharis.grapql404.domain.CountryClient
import com.mharis.grapql404.domain.DetailedCountry
import com.mharis.grapql404.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
): CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}
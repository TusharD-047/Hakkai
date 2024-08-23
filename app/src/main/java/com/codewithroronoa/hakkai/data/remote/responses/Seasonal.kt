package com.codewithroronoa.hakkai.data.remote.responses

data class Seasonal(
    val data: List<Data>,
    val paging: Paging,
    val season: Season
)
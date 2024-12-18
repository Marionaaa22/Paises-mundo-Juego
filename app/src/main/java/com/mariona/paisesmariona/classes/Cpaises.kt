package com.mariona.paisesmariona.classes

data class Paises (
    val countries: List<cPaises>
)

data class cPaises(
    val name_en: String,
    val name_es: String,
    val continent_en: String,
    val continent_es: String,
    val capital_en: String = "",
    val capital_es: String = "",
    val dial_code: String = "",
    val code_2: String,
    val code_3: String,
    val tld: String,
    val km2: Double,
    val emoji: String,
    var favoritos: Boolean = false
)

enum class ContinentEn {
    Africa,
    Antarctica,
    Asia,
    Europe,
    NorthAmerica,
    Oceania,
    SouthAmerica
}

enum class ContinentEs {
    AméricaDelNorte,
    AméricaDelSur,
    Antártida,
    Asia,
    Europa,
    Oceanía,
    África
}

package com.grumpyshoe.beertasticcmp.data.source.network.beer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BeerDto(
    val id: Int? = null,
    val name: String? = null,
    val tagline: String? = null,
    @SerialName("first_brewed") val firstBrewed: String? = null,
    val description: String? = null,
    @SerialName("image") val imageId: String? = null,
    val abv: Double? = null,
    val ibu: Double? = null,
    @SerialName("target_fg") val targetFg: Double? = null,
    @SerialName("target_og") val targetOg: Double? = null,
    val ebc: Double? = null,
    val srm: Double? = null,
    val ph: Double? = null,
    @SerialName("attenuation_level") val attenuationLevel: Double? = null,
    val volume: VolumeDto? = null,
    @SerialName("boil_volume") val boilVolume: VolumeDto? = null,
    val method: MethodDto? = null,
    val ingredients: IngredientsDto? = null,
    @SerialName("food_pairing") val foodPairing: List<String>? = null,
    @SerialName("brewers_tips") val brewersTips: String? = null,
    @SerialName("contributed_by") val contributedBy: String? = null,
)

@Serializable
data class VolumeDto(val value: Int? = null, val unit: String? = null)

@Serializable
data class MethodDto(
    @SerialName("mash_temp") val mashTemp: List<MethodItemDto>? = null,
    val fermentation: FermentationDto? = null,
    val twist: String? = null,
)

@Serializable
data class MethodItemDto(val temp: ValueDto? = null, val duration: Int? = null)

@Serializable
data class FermentationDto(val temp: ValueDto? = null)

@Serializable
data class ValueDto(val value: Double? = null, val unit: String? = null)

@Serializable
data class IngredientsDto(
    val malt: List<IngredientsItemDto>? = null,
    val hops: List<IngredientsItemDto>? = null,
    val yeast: String? = null,
)

@Serializable
data class IngredientsItemDto(
    val name: String? = null,
    val amount: ValueDto? = null,
    val add: String? = null,
    val attribute: String? = null,
)

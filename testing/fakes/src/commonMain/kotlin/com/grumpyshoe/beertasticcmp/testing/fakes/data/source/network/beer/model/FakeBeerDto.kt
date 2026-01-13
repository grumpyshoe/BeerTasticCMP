package com.grumpyshoe.beertasticcmp.testing.fakes.data.source.network.beer.model

import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.BeerDto
import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.FermentationDto
import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.IngredientsDto
import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.IngredientsItemDto
import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.MethodDto
import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.MethodItemDto
import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.ValueDto
import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.VolumeDto

val fakeBeerDto = getFakeBeerDto()

fun getFakeBeerDto(id: Int = 0): BeerDto = BeerDto(
    id = id,
    name = "Beer_$id",
    tagline = "Tag_$id",
    firstBrewed = "2023-05-23",
    description = "Beer description $id",
    imageId = "dummyUrl_$id",
    abv = 6.0,
    ibu = 60.0,
    targetFg = 1010.0,
    targetOg = 1056.0,
    ebc = 17.0,
    srm = 8.5,
    ph = 4.4,
    attenuationLevel = 82.14,
    volume =
        VolumeDto(
            value = 20,
            unit = "liters",
        ),
    boilVolume =
    VolumeDto(
        value = 25,
        unit = "liters",
    ),
    method =
        MethodDto(
            mashTemp =
                listOf(
                    MethodItemDto(
                        temp =
                            ValueDto(
                                value = 65.0,
                                unit = "celsius",
                            ),
                        duration = 75,
                    ),
                ),
            fermentation =
                FermentationDto(
                    temp =
                        ValueDto(
                            value = 19.0,
                            unit = "celsius",
                        ),
                ),
            twist = "twist",
        ),
    ingredients =
        IngredientsDto(
            malt =
                listOf(
                    IngredientsItemDto(
                        name = "Extra Pale",
                        amount =
                            ValueDto(
                                value = 5.3,
                                unit = "kilograms",
                            ),
                    ),
                ),
            hops =
                listOf(
                    IngredientsItemDto(
                        name = "Ahtanum",
                        amount =
                            ValueDto(
                                value = 17.5,
                                unit = "grams",
                            ),
                    ),
                ),
            yeast = "Wyeast 1056 - American Ale™",
        ),
    foodPairing =
    listOf(
        "Spicy carne asada with a pico de gallo sauce",
        "Shredded chicken tacos with a mango chilli lime salsa",
        "Cheesecake with a passion fruit swirl sauce",
    ),
    brewersTips = "brewer tips",
    contributedBy = "Sam Mason <samjbmason>",
)

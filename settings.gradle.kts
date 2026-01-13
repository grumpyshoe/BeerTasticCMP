rootProject.name = "BeerTasticCMP"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")

include(":data:repository")
include(":data:source:network")
include(":data:source:preferences")

include(":domain:beer")

include(":presentation:common:resources")
include(":presentation:features:details")
include(":presentation:features:home")
include(":presentation:features:splashscreen")
include(":testing:fakes")

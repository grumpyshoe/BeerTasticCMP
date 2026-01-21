package com.grumpyshoe.beertasticcmp.data.source.preferences

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


interface SharedPreferenceService {
    suspend fun checkIfFavorite(beerId: Int): Boolean

    suspend fun setIsBeerFavorite(beerId: Int, isFavorite: Boolean)

    val favorites: StateFlow<List<Int>>

    companion object {
        const val KEY_FAVORITES = "favorites"
    }
}


@OptIn(DelicateCoroutinesApi::class)
class PreferenceServiceImpl(
    private val storage: PrefsDataStore,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    SharedPreferenceService {

    private val _favorites = MutableStateFlow(emptyList<Int>())
    override val favorites: StateFlow<List<Int>> = _favorites

    init {
        GlobalScope.launch(dispatcher) {
            getFavorites().let { favorites ->
                _favorites.update {
                    favorites
                }
            }
        }
    }

    override suspend fun checkIfFavorite(beerId: Int): Boolean {
        return storage.data.map { pref ->
            pref[KEY_FAVORITES]
        }.firstOrNull()
            ?.let { favorites ->
                favorites.split(",").contains(beerId.toString())
            } ?: false
    }

    override suspend fun setIsBeerFavorite(beerId: Int, isFavorite: Boolean) {
        val favoriteList = getFavorites().toMutableList()

        val updatedList =
            favoriteList.apply {
                if (isFavorite) {
                    add(beerId)
                } else {
                    remove(beerId)
                }
            }

        storage.edit { pref ->
            pref[KEY_FAVORITES] = updatedList.joinToString(separator = ",")
        }
        _favorites.tryEmit(updatedList)
    }

    private suspend fun getFavorites(): List<Int> {
        return storage.data.map { pref ->
            pref[KEY_FAVORITES]
        }.firstOrNull()
            ?.split(",")
            ?.mapNotNull {
                try {
                    it.toInt()
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
    }

    companion object {
        private val KEY_FAVORITES = stringPreferencesKey(SharedPreferenceService.KEY_FAVORITES)
    }
}
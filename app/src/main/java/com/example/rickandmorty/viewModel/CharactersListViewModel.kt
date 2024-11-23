package com.example.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.ApiResponse
import com.example.rickandmorty.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class CharactersListViewModel : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    // Initialisation automatique de la récupération des données
    init {
        fetchAllCharacters()
    }

    fun fetchAllCharacters() {
        viewModelScope.launch {
            try {
                val response: Response<ApiResponse> = RetrofitInstance.retrofitService.getCharacters(1)
                if (response.isSuccessful) {
                    _characters.value = (response.body()?.results ?: emptyList())
                } else {
                    _errorMessage.value = "Erreur: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Une erreur s'est produite: ${e.message}"
            }

            // Pour le débogage : journalisation des réponses
            try {
                val response = RetrofitInstance.retrofitService.getCharacters(1)
                Log.d("NetworkTest", "Response: ${response.body()}")
            } catch (e: Exception) {
                Log.e("NetworkTest", "Error: ${e.message}")
            }
        }
    }
}
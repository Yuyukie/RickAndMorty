package com.example.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.viewModel.CharactersListViewModel
import com.example.rickandmorty.model.Character



class CharactersListActivity : ComponentActivity() {

    private val viewModel: CharactersListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CharactersListScreen(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersListScreen(viewModel: CharactersListViewModel) {

    // Observer les données du ViewModel
    val characters = viewModel.characters.observeAsState(emptyList())
    val errorMessage = viewModel.errorMessage.observeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current

        TopAppBar(
            title = { Text("Rick and Morty") },
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Si une erreur est présente, on affiche un message
            errorMessage.value?.let {
                Toast.makeText(LocalContext.current, it, Toast.LENGTH_LONG).show()
            }

            // Afficher la liste des personnages
            LazyColumn {
                items(characters.value) { character ->
                    CharacterItem(character)
                }
            }
        }
    }
}


@Composable
fun CharacterItem(
    character: Character
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affichage de l'image
        Image(
            painter = rememberAsyncImagePainter(character.image), // Utilisation de l'URL de l'image depuis le modèle
            contentDescription = "Character image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
                .clickable {
                    val intent = Intent(context, CharacterDetailsActivity::class.java).apply {
                        putExtra("character", character) // Transmet l'objet complet
                    }
                    context.startActivity(intent)
                }

        )

        // Affichage du nom et prénom
        Text("Nom: ${character.name}")

    }

}

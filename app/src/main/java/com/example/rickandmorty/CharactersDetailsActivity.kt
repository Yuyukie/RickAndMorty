package com.example.rickandmorty

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.model.Character

class CharacterDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val character = intent.getSerializableExtra("character") as? Character
        if (character != null) {
            setContent {
                CharacterDetailScreen(character)
            }
        } else {
            finish() // Termine l'activité si l'objet est null
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(character: Character) {
    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current

        // Barre de navigation avec un bouton retour
        TopAppBar(
            title = { Text(character.name) },
            navigationIcon = {
                IconButton(onClick = {
                    // Retour à la liste des personnages
                    val intent = Intent(context, CharactersListActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                }
            }
        )

        // Contenu principal : image et informations du personnage
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            // Image du personnage
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = "Image du personnage",
                modifier = Modifier
                    .size(150.dp)
                    .padding(end = 16.dp)
            )

            // Informations détaillées sur le personnage
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text("Statut: ${character.status ?: "Inconnu"}")
                Text("Espèce: ${character.species ?: "Inconnu"}")
                Text("Type: ${character.type ?: "Inconnu"}")
                Text("Genre: ${character.gender ?: "Inconnu"}")
                Text("Origine: ${character.originName?.name ?: "Inconnu"}")
                Text("Localisation: ${character.locationName?.name ?: "Inconnu"}")
            }
        }
    }
}
package com.example.fiulostandfound.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fiulostandfound.data.Item   // adjust if your Item is elsewhere
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
fun AddItemForm(
    onSubmit: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    var description by rememberSaveable { mutableStateOf("") }
    var imageUrl    by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (description.isNotBlank() && imageUrl.isNotBlank()) {
                    onSubmit(Item(
                        image_url    = imageUrl,
                        description = description))
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Item")
        }
    }
}

@Composable
fun ItemCard(item: Item) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .size(120.dp)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.description, color = Color.DarkGray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    lostItems: List<Item>,
    foundItems: List<Item>,
    onLostClick: () -> Unit,
    onFoundClick: () -> Unit,
    onReportLostClick: () -> Unit,   // ← new
    onReportFoundClick: () -> Unit   // ← new
) {
    Scaffold(
        topBar = { /* … */ }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Lost Items", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            LazyRow { /* … show lostItems … */ }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onLostClick, modifier = Modifier.weight(1f)) {
                    Text("View All Lost")
                }
                Button(onClick = onReportLostClick, modifier = Modifier.weight(1f)) {
                    Text("Report Lost")
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Found Items", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            LazyRow { /* … show foundItems … */ }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onFoundClick, modifier = Modifier.weight(1f)) {
                    Text("View All Found")
                }
                Button(onClick = onReportFoundClick, modifier = Modifier.weight(1f)) {
                    Text("Report Found")
                }
            }
        }
    }
}
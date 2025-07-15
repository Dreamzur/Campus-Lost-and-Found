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



@Composable
fun AddItemForm(
    onSubmit: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    var description by remember { mutableStateOf("") }
    var imageUrl   by remember { mutableStateOf("") }

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
                    onSubmit(Item(description = description, imageUrl = imageUrl))
                    description = ""
                    imageUrl = ""
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
    onReportLostClick: () -> Unit = {},
    onReportFoundClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FIU Lost & Found") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor   = MaterialTheme.colorScheme.primary,
                    titleContentColor= MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Lost Items", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(lostItems.take(10)) { item -> ItemCard(item) }
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = onLostClick, Modifier.fillMaxWidth()) {
                Text("View All Lost Items")
            }
            Button(onClick = onReportLostClick) { Text("Report Lost") }

            Spacer(Modifier.height(16.dp))

            Text("Found Items", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(foundItems.take(10)) { item -> ItemCard(item) }
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = onFoundClick, Modifier.fillMaxWidth()) {
                Text("View All Found Items")
            }
            Button(onClick = onReportFoundClick) { Text("Report Found") }
        }
    }
}
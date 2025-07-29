package com.example.fiulostandfound.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import coil.compose.AsyncImage


@Composable
fun AddItemForm(
    onSubmit: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    var description by rememberSaveable { mutableStateOf("") }
    var imageUrl   by rememberSaveable { mutableStateOf("") }
    var location   by rememberSaveable { mutableStateOf("") }
    var title      by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
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
                    onSubmit(
                        Item(
                            imageUrl = imageUrl,
                            description = description,
                            location = location.takeIf { it.isNotBlank() },
                            title = title.takeIf { it.isNotBlank() },
                        )
                    )
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
            item.description?.let { Text(text = it, color = Color.DarkGray) }
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
    onReportLostClick: () -> Unit,
    reportFoundVisible: Boolean,
    onReportFoundClick: () -> Unit,
    onItemClick: (Item) -> Unit,
) {
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(Modifier
                .fillMaxWidth()
                .height(400.dp)
            ) {
                GridScreen(
                    itemsList = lostItems,
                    onItemClick = onItemClick,
                    title = "Latest Lost",
                )
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onLostClick) { Text("View All Lost") }
                Button(onClick = {keyboardController?.hide()
                        focusManager.clearFocus()
                        onReportLostClick()}) { Text("Report Lost")}
            }
        }

        item {
            Box(Modifier
                .fillMaxWidth()
                .height(400.dp)
            ) {
                GridScreen(
                    itemsList = foundItems,
                    onItemClick = onItemClick,
                    title = "Latest Found",
                )
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onFoundClick) { Text("View All Found") }

                if (reportFoundVisible) {


                    Button(onClick = {keyboardController?.hide()
                            focusManager.clearFocus()
                            onReportFoundClick()}) {
                        Text("Report Found")
                    }
                }
            }
        }
    }
}
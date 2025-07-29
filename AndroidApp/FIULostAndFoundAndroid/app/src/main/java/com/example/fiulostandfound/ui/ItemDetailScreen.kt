package com.example.fiulostandfound.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fiulostandfound.data.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    item: Item,
    isAdmin: Boolean,
    onClaim: () -> Unit,
    onRemove: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { item.title?.let { Text(it) } })
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        )
        {
            Log.d("GRID", "Loading image: ${item.imageUrl}")

            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )

            Spacer(Modifier.height(16.dp))
            Text("Description", style = MaterialTheme.typography.titleMedium)
            item.description?.let { Text(it, modifier = Modifier.padding(top = 4.dp)) }
            Spacer(Modifier.height(12.dp))
            Text("Location", style = MaterialTheme.typography.titleMedium)
            item.location?.let { Text(it, modifier = Modifier.padding(top = 4.dp)) }
            Spacer(Modifier.height(24.dp))
            if (isAdmin) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onRemove, modifier = Modifier.weight(1f)) {
                        Text("Remove")
                    }
                    Button(onClick = onClaim, modifier = Modifier.weight(1f)) {
                        Text(if (item.claimed) "Already claimed" else "Claim")
                    }
                }
            } else {
                if (!item.claimed) {
                    Button(onClick = onClaim, modifier = Modifier.fillMaxWidth()) {
                        Text("Claim Item")
                    }
                } else {
                    Text("Already claimed", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
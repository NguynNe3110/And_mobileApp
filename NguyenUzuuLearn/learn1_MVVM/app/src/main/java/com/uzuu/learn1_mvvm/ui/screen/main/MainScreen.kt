package com.uzuu.learn1_mvvm.ui.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen( viewModel: MainViewModel = viewModel()) {
    val text = viewModel.text.value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.changeText() }) {
            Text("Bấm vào tôi")
        }
    }
}

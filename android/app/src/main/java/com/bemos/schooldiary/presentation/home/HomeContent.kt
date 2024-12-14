package com.bemos.schooldiary.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row {
            Column {
                Text(
                    text = "Занятия начнутся"
                )
                Text(
                    text = "через  минут"
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    HomeContent()
}
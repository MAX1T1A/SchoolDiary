package com.bemos.schooldiary.presentation.home.utils.ui.evets_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventItem(
    text: String
) {
    Column {
        Card(
            modifier = Modifier
                .width(165.dp)
                .height(153.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Gray)
            ) {

            }
        }
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        Text(
            text = text
        )
    }

}

@Preview
@Composable
private fun EventItemPreview() {
    EventItem(
        text = "Конкурс чтецов"
    )
}
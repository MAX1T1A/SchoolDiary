package com.bemos.schooldiary.presentation.home.utils.ui.calendar_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class CalendarEvent(
    val startTime: String,
    val endTime: String,
    val title: String,
    val description: String,
    val color: Color
)


@Composable
fun CalendarView(events: List<CalendarEvent>) {
    val timeSlots = (8..12).map { hour -> "${hour}:00" }
    Card {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8F8)) // Фон
        ) {

            items(timeSlots) { timeSlot ->

                val eventsInTimeSlot = events.filter { event ->
                    event.startTime.startsWith(timeSlot)
                }

                if (eventsInTimeSlot.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = timeSlot,
                            modifier = Modifier
                                .width(50.dp)
                                .padding(top = 16.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 12.dp)
                        ) {
                            eventsInTimeSlot.forEach { event ->
                                EventCard(event)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(event: CalendarEvent) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 4.dp)
            .height(60.dp)
            .background(
                color = event.color,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = event.title,
                color = Color.White,
            )
            Text(
                text = event.description,
                color = Color.White,
            )
        }
    }
}

val sampleEvents = listOf(
    CalendarEvent(
        startTime = "8:00",
        endTime = "9:00",
        title = "Русский язык",
        description = "Текст и его план",
        color = Color(0xFF7B61FF)
    ),
    CalendarEvent(
        startTime = "10:00",
        endTime = "11:00",
        title = "Английский язык",
        description = "Текст и его план",
        color = Color(0xFF7B61FF)
    ),
)

@Preview(showBackground = true)
@Composable
private fun CalendarPreview() {
    CalendarView(events = sampleEvents)
}

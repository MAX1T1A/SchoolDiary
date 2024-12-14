package com.bemos.schooldiary.presentation.ui.utils.indicator

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.schooldiary.R

@Composable
fun LoadIndicator(
    isShow: Boolean
) {
    if (isShow) {
        Card(
            shape = RoundedCornerShape(10.dp),
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Successfully",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(
                        id = R.drawable.rounded_check_circle_24
                    ),
                    contentDescription = null,
                    tint = Color(0xFF42C448)
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoadIndicatorPreview() {
    LoadIndicator(isShow = true)
}
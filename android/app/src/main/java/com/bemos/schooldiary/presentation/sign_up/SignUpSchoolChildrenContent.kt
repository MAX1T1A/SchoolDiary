package com.bemos.schooldiary.presentation.sign_up

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.schooldiary.domain.models.User
import com.bemos.schooldiary.presentation.ui.utils.indicator.LoadIndicator
import com.bemos.schooldiary.presentation.ui.utils.text_field.CustomTextField
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.Pattern
import kotlin.time.Duration.Companion.seconds

@Composable
fun SignUpSchoolChildrenContent(
    modifier: Modifier = Modifier,
    onRegisterClick: (User) -> Unit
) {
    var name by remember {
        mutableStateOf("")
    }
    var surname by remember {
        mutableStateOf("")
    }
    var patronymic by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    var isVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                animationSpec = tween(durationMillis = 500)
            ) + slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            LoadIndicator(isShow = true)

            LaunchedEffect(isVisible) {
                if (isVisible) {
                    delay(2.seconds)
                    isVisible = false
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add schoolchildren",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(50.dp))
        CustomTextField(
            text = surname,
            onValueChanged = {
                surname = it
            },
            labelText = "Surname"
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            text = name,
            onValueChanged = {
                name = it
            },
            labelText = "Name"
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            text = patronymic,
            onValueChanged = {
                patronymic = it
            },
            labelText = "Patronymic"
        )
        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(
            text = email,
            onValueChanged = {
                email = it
            },
            labelText = "Email"
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = Patterns.EMAIL_ADDRESS.matcher(email).matches(),
            onClick = {
                    val user = User(
                        surname = surname,
                        name = name,
                        patronymic = patronymic,
                        email = email
                    )
                    onRegisterClick(
                        user
                    )
                    isVisible = true
            }
        ) {
            Text(
                text = "Add"
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun SingUpContentPreview() {
    SignUpSchoolChildrenContent(
        onRegisterClick = {}
    )
}
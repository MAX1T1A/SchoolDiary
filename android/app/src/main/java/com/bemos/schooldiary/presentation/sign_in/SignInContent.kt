package com.bemos.schooldiary.presentation.sign_in

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.schooldiary.domain.models.AuthenticateUser
import com.bemos.schooldiary.presentation.ui.utils.text_field.CustomTextField

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    onSignInClick: (AuthenticateUser) -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(50.dp))

        CustomTextField(
            text = email,
            onValueChanged = {
                email = it
            },
            labelText = "Email"
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            text = password,
            onValueChanged = {
                password = it
            },
            labelText = "Password"
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val user = AuthenticateUser(
                    email = email,
                    password = password
                )
                onSignInClick(
                    user
                )
            }
        ) {
            Text(
                text = "Confirm"
            )
        }
    }
}
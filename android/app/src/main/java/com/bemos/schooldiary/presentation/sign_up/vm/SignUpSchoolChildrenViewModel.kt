package com.bemos.schooldiary.presentation.sign_up.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bemos.schooldiary.domain.models.User
import com.bemos.schooldiary.domain.use_case.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

class SignUpSchoolChildrenViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> get() = _status

    fun registerUser(user: User) = viewModelScope.launch {
        val call = registerUserUseCase.execute(
            user
        )
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("Register", "${response.body()}")
                } else {
                    Log.d("Register", "Registration failed: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }
}
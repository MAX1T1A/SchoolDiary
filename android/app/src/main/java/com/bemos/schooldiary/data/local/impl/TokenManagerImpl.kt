package com.bemos.schooldiary.data.local.impl

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.bemos.schooldiary.domain.repositories.TokenManagerRepository

class TokenManagerImpl(
    private val sharedPreferences: SharedPreferences
) : TokenManagerRepository {

    override fun set(token: String, email: String) {
        sharedPreferences.edit {
            putString(
                email,
                token
            )
        }
        Log.d("SharedSave", get(email))
    }

    private fun get(email: String) : String {
        val sharedToken = sharedPreferences.getString(
            email, ""
        )
        return sharedToken.toString()
    }


}
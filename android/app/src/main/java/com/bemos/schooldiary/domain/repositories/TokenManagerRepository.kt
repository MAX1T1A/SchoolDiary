package com.bemos.schooldiary.domain.repositories

interface TokenManagerRepository {
    fun set(token: String, email: String)
}
package com.rosorio.mindicador.datasources

interface LoginDataSource {
    fun signUp(username: String, password: String)
    fun login(username: String, password: String): Boolean?
    fun logout(username: String)
}
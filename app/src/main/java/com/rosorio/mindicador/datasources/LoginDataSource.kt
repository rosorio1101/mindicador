package com.rosorio.mindicador.datasources

interface LoginDataSource {
    fun login(username: String, password: String): Boolean
    fun logout(username: String)
}
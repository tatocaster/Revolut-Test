package me.tatocaster.revoluttest.data

import me.tatocaster.revoluttest.data.api.ApiService


interface DataComponent {
    fun exposeApiService(): ApiService
}
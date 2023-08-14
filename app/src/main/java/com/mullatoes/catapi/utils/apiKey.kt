package com.mullatoes.catapi.utils

import com.mullatoes.catapi.BuildConfig

class ApiKeyUtil  {

    companion object {
        fun getApiKey() : String {
            return BuildConfig.CAT_API_KEY
        }
    }
}
package com.nerdery.aarbit.notationconversionapi.helper

import java.lang.IllegalStateException

class EnvVarUtil {
    companion object {
        val SECRET_KEY = "SECRET_KEY"
    }
}

fun getEnv(key: String): String {
    return System.getenv(key)?: throw IllegalStateException("Environment variable $key does not exist!")
}
package com.nerdery.aarbit.notationconversionapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class NotationConversionAPIApplication

fun main(args: Array<String>) {
    runApplication<NotationConversionAPIApplication>(*args)
}

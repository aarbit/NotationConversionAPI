package com.nerdery.aarbit.notationconversionapi

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.spring.SpringAutowireConstructorExtension

class ProjectConfig: AbstractProjectConfig() {
    override fun extensions(): List<Extension> =
        listOf(SpringAutowireConstructorExtension)
    override val parallelism = 4
}
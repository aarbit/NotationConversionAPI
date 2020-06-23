package com.nerdery.aarbit.notationconversionapi.service

import com.nerdery.aarbit.notationconversionapi.enum.NotationType

interface NotationService {
    fun determineNotationType(expression: List<String>): NotationType
    fun parseNotationType(type: String): NotationType
}
package com.nerdery.aarbit.notationconversionapi.service

import com.nerdery.aarbit.notationconversionapi.enum.NotationType

interface NotationConversionService {
    fun convert(expression: List<String>, inputNotationType: NotationType, outputNotationType: NotationType): List<String>
}
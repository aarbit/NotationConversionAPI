package com.nerdery.aarbit.notationconversionapi.service

import com.nerdery.aarbit.notationconversionapi.model.ArithmeticGroup

interface ExpressionParsingService {
    fun parseExpression(expression: List<String>): List<ArithmeticGroup>
}
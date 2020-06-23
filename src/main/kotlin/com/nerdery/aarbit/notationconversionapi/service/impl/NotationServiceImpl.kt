package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.enum.NotationType
import com.nerdery.aarbit.notationconversionapi.exception.InvalidExpressionException
import com.nerdery.aarbit.notationconversionapi.exception.InvalidNotationException
import com.nerdery.aarbit.notationconversionapi.helper.isOperator
import com.nerdery.aarbit.notationconversionapi.service.NotationService
import org.springframework.stereotype.Service

@Service
class NotationServiceImpl: NotationService {
    override fun determineNotationType(expression: List<String>): NotationType {
        if(expression.size < 3)
            throw InvalidExpressionException("Expression '${expression.joinToString(" ")}' is too short")
        if(expression[0].isOperator())
            return NotationType.PREFIX
        if(expression[1].isOperator())
            return NotationType.INFIX
        return NotationType.POSTFIX
    }

    override fun parseNotationType(type: String): NotationType {
        return try {
            NotationType.valueOf(type.toUpperCase())
        } catch (e: IllegalArgumentException) {
            throw InvalidNotationException("Invalid notation type")
        }
    }
}
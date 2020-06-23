package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.enum.NotationType
import com.nerdery.aarbit.notationconversionapi.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotationConversionServiceImpl
@Autowired constructor(private val prefixExpressionParsingService: PrefixExpressionParsingService,
                       private val postfixExpressionParsingService: PostfixExpressionParsingService,
                       private val infixExpressionParsingService: InfixExpressionParsingService,
                       private val postfixExpressionExpandingService: PostfixExpressionExpandingService,
                       private val prefixExpressionExpandingService: PrefixExpressionExpandingService,
                       private val infixExpressionExpandingService: InfixExpressionExpandingService
) :NotationConversionService {
    override fun convert(
        expression: List<String>,
        inputNotationType: NotationType,
        outputNotationType: NotationType
    ): List<String> {
        val parsedExpression =
            when(inputNotationType) {
                NotationType.POSTFIX -> postfixExpressionParsingService.parseExpression(expression)
                NotationType.INFIX -> infixExpressionParsingService.parseExpression(expression)
                NotationType.PREFIX -> prefixExpressionParsingService.parseExpression(expression)
            }
        return when(outputNotationType) {
                NotationType.POSTFIX -> postfixExpressionExpandingService.expandExpression(parsedExpression)
                NotationType.INFIX -> infixExpressionExpandingService.expandExpression(parsedExpression)
                NotationType.PREFIX -> prefixExpressionExpandingService.expandExpression(parsedExpression)
            }
    }
}
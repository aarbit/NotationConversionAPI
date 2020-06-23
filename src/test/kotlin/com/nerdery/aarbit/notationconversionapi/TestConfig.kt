package com.nerdery.aarbit.notationconversionapi

import com.nerdery.aarbit.notationconversionapi.service.impl.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TestConfig {

    @Bean
    fun prefixExpressionParsingService(): PrefixExpressionParsingServiceImpl {
        return PrefixExpressionParsingServiceImpl()
    }

    @Bean
    fun postfixExpressionParsingService(): PostfixExpressionParsingServiceImpl {
        return PostfixExpressionParsingServiceImpl()
    }

    @Bean
    fun infixExpressionParsingService(): InfixExpressionParsingServiceImpl {
        return InfixExpressionParsingServiceImpl()
    }

    @Bean
    fun postfixExpressionExpandingService(): PostfixExpressionExpandingServiceImpl {
        return PostfixExpressionExpandingServiceImpl()
    }

    @Bean
    fun prefixExpressionExpandingService(): PrefixExpressionExpandingServiceImpl {
        return PrefixExpressionExpandingServiceImpl()
    }

    @Bean
    fun infixExpressionExpandingService(): InfixExpressionExpandingServiceImpl {
        return InfixExpressionExpandingServiceImpl()
    }

    @Bean
    fun notationConversionService(): NotationConversionServiceImpl {
        return NotationConversionServiceImpl(
            prefixExpressionParsingService(),
            postfixExpressionParsingService(),
            infixExpressionParsingService(),
            postfixExpressionExpandingService(),
            prefixExpressionExpandingService(),
            infixExpressionExpandingService()
        )
    }

    @Bean
    fun notationService(): NotationServiceImpl {
        return NotationServiceImpl()
    }
}
package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.model.ArithmeticGroup
import com.nerdery.aarbit.notationconversionapi.service.InfixExpressionExpandingService
import org.springframework.stereotype.Service

@Service
class InfixExpressionExpandingServiceImpl: InfixExpressionExpandingService() {
    override fun notationExpansion(group: ArithmeticGroup): List<Any> {
        return listOf(group.first, group.operator, group.second)
    }
}
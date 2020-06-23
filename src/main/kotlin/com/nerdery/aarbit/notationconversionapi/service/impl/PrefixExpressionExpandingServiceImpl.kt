package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.model.ArithmeticGroup
import com.nerdery.aarbit.notationconversionapi.service.PrefixExpressionExpandingService
import org.springframework.stereotype.Service

@Service
class PrefixExpressionExpandingServiceImpl: PrefixExpressionExpandingService() {
    override fun notationExpansion(group: ArithmeticGroup): List<Any> {
        return listOf(group.operator, group.first, group.second)
    }
}
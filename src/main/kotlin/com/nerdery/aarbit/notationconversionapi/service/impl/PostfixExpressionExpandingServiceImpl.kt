package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.model.ArithmeticGroup
import com.nerdery.aarbit.notationconversionapi.service.PostfixExpressionExpandingService
import org.springframework.stereotype.Service

@Service
class PostfixExpressionExpandingServiceImpl: PostfixExpressionExpandingService() {
    override fun notationExpansion(group: ArithmeticGroup): List<Any> {
        return listOf(group.first, group.second, group.operator)
    }
}
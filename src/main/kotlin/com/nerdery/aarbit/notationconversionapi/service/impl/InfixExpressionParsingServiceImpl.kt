package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.exception.InvalidExpressionException
import com.nerdery.aarbit.notationconversionapi.helper.Operator
import com.nerdery.aarbit.notationconversionapi.model.ArithmeticGroup
import com.nerdery.aarbit.notationconversionapi.service.InfixExpressionParsingService
import org.springframework.stereotype.Service

@Service
class InfixExpressionParsingServiceImpl: InfixExpressionParsingService {
    override fun parseExpression(expression: List<String>): List<ArithmeticGroup> {
        return parse(expression) as List<ArithmeticGroup>
    }

    private fun parse(exList: List<Any>): List<Any> {
        Operator.operatorList.forEach { op ->
            val indexOfOp = exList.indexOfFirst { el -> el == op }
            if(indexOfOp != -1) {
                if((indexOfOp - 1) !in exList.indices || (indexOfOp + 1) !in exList.indices) {
                    throw InvalidExpressionException("Expression ${exList.joinToString(" ")} is invalid")
                }
                val group = ArithmeticGroup(exList[indexOfOp] as String, exList[indexOfOp - 1], exList[indexOfOp + 1])
                val beforeList = if(indexOfOp - 2 >= 0) {
                    exList.slice(0 until indexOfOp - 1)
                } else {
                    emptyList()
                }
                val afterList = if(indexOfOp + 2 in exList.indices) {
                    exList.slice(indexOfOp + 2 until exList.size)
                } else {
                    emptyList()
                }
                return parse(beforeList + listOf(group) + afterList)
            }
        }
        return exList
    }
}
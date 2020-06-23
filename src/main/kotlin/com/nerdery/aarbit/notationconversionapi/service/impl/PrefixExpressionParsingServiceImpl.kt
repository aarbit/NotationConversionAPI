package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.exception.InvalidExpressionException
import com.nerdery.aarbit.notationconversionapi.helper.Operator
import com.nerdery.aarbit.notationconversionapi.model.ArithmeticGroup
import com.nerdery.aarbit.notationconversionapi.service.PrefixExpressionParsingService
import org.springframework.stereotype.Service

@Service
class PrefixExpressionParsingServiceImpl: PrefixExpressionParsingService {
    override fun parseExpression(expression: List<String>): List<ArithmeticGroup> {
        return parse(expression) as List<ArithmeticGroup>
    }

    private fun parse(exList: List<Any>): List<Any> {

        if(exList.size == 1 && exList[0] is ArithmeticGroup) {
            return exList
        }
        val lastOpIndex = exList.indexOfLast { it in Operator.operatorList }
        if(lastOpIndex == -1) {
            throw InvalidExpressionException("Expression '${exList.joinToString(" ")}' is invalid")
        }

        val group = if(isOperatorIndexValid(lastOpIndex, exList.size)) {
            ArithmeticGroup(exList[lastOpIndex] as String, exList[lastOpIndex + 1], exList[lastOpIndex + 2])
        } else {
            throw InvalidExpressionException("Operator ${exList[lastOpIndex]} is at an invalid index of $lastOpIndex")
        }

        val beforeList = if(lastOpIndex != 0) {
            exList.slice(0 until lastOpIndex)
        } else {
            emptyList()
        }

        val afterList = if(lastOpIndex + 3 in exList.indices) {
            exList.slice(lastOpIndex + 3 until exList.size)
        } else {
            emptyList()
        }
        return parse(beforeList + listOf(group) + afterList)
    }

    private fun isOperatorIndexValid(index: Int, size: Int): Boolean {
        return size - index > 2
    }

}
package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.exception.InvalidExpressionException
import com.nerdery.aarbit.notationconversionapi.helper.Operator
import com.nerdery.aarbit.notationconversionapi.model.ArithmeticGroup
import com.nerdery.aarbit.notationconversionapi.service.PostfixExpressionParsingService
import org.springframework.stereotype.Service

@Service
class PostfixExpressionParsingServiceImpl: PostfixExpressionParsingService {

    override fun parseExpression(expression: List<String>): List<ArithmeticGroup> {
        return parse(expression) as List<ArithmeticGroup>
    }

    private fun parse(exList: List<Any>): List<Any> {
        if(exList.size == 1 && exList[0] is ArithmeticGroup) {
            return exList
        }
        val firstOpIndex = exList.indexOfFirst { it in Operator.operatorList }
        if(firstOpIndex == -1) {
            throw InvalidExpressionException("Expression '${exList.joinToString(" ")}' is invalid")
        }

        val group = if(isOperatorIndexValid(firstOpIndex)) {
            ArithmeticGroup(exList[firstOpIndex] as String, exList[firstOpIndex - 2], exList[firstOpIndex - 1])
        } else {
            throw InvalidExpressionException("Operator ${exList[firstOpIndex]} is at an invalid index of $firstOpIndex")
        }

        val beforeList = if(firstOpIndex - 2 >= 0) {
            exList.slice(0 until firstOpIndex - 2)
        } else {
            emptyList()
        }

        val afterList = if(firstOpIndex != exList.lastIndex) {
            exList.slice(firstOpIndex + 1 until exList.size)
        } else {
            emptyList()
        }
        return parse(beforeList + listOf(group) + afterList)
    }

    private fun isOperatorIndexValid(index: Int): Boolean {
        return index >= 2
    }
}
package com.nerdery.aarbit.notationconversionapi.service

import com.nerdery.aarbit.notationconversionapi.model.ArithmeticGroup

abstract class ExpressionExpandingService {
    fun expandExpression(expression: List<ArithmeticGroup>): List<String> {
        return expand(expression) as List<String>
    }

    private fun expand(exList: List<Any>): List<Any> {
        val lastGroupIndex = exList.indexOfLast { it is ArithmeticGroup }
        if(lastGroupIndex == -1)
            return exList
        val group = exList[lastGroupIndex] as ArithmeticGroup
        val listBefore = if(lastGroupIndex != 0) {
            exList.slice(0 until lastGroupIndex)
        } else {
            emptyList()
        }
        val listAfter = if(lastGroupIndex != exList.lastIndex) {
            exList.slice(lastGroupIndex+1 until exList.size)
        } else {
            emptyList()
        }
        return expand(listBefore + notationExpansion(group) + listAfter)
    }

    abstract fun notationExpansion(group: ArithmeticGroup): List<Any>
}
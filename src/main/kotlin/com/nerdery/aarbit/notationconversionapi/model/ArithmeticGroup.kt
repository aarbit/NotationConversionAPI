package com.nerdery.aarbit.notationconversionapi.model

import com.nerdery.aarbit.notationconversionapi.exception.InvalidOperandException
import com.nerdery.aarbit.notationconversionapi.exception.InvalidOperatorException
import com.nerdery.aarbit.notationconversionapi.helper.isOperator

class ArithmeticGroup(val operator: String, val first: Any, val second: Any) {
    init {
        if(!operator.isOperator()) throw InvalidOperatorException("String '$operator' is not a valid operator type")
        if(!isIntAsString(first) && first !is ArithmeticGroup) throw InvalidOperandException("Incorrect first operand '$first'")
        if (!isIntAsString(second) && second !is ArithmeticGroup) throw InvalidOperandException("Incorrect second operand '$second'")
    }

    private fun isIntAsString(operand: Any): Boolean {
        return operand.toString().matches(Regex("-?\\d+(\\.\\d+)?"))
    }

    override fun toString(): String {
        return "($first $second $operator)"
    }
}
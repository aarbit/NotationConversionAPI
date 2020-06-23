package com.nerdery.aarbit.notationconversionapi.helper

fun String.isOperator(): Boolean {
    return this in Operator.operatorList
}

fun String.splitToNoBlanksList(): List<String> {
    return this.split(" ").filter { it.isNotBlank() }
}
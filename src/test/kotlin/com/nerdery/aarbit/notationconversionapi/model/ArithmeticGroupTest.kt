package com.nerdery.aarbit.notationconversionapi.model

import com.nerdery.aarbit.notationconversionapi.exception.InvalidOperandException
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.StringSpecTestFactoryConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(StringSpecTestFactoryConfiguration::class)])
class ArithmeticGroupTest: StringSpec() {

    init {
        val intGroup1 = ArithmeticGroup("+", 2, 3)
        val intGroup2 = ArithmeticGroup("+", 4, 5)

        "Non-Int or non-ArithmeticGroup operands should throw an exception" {
            shouldThrow<InvalidOperandException> { ArithmeticGroup("+","","") }
            shouldThrow<InvalidOperandException> { ArithmeticGroup("+","1","b") }
            shouldThrow<InvalidOperandException> { ArithmeticGroup("+","a","2") }
        }
        "Int operands should not throw an exception" {
            shouldNotThrow<InvalidOperandException> { ArithmeticGroup("+", 1, 2) }
        }
        "ArithmeticGroup operands should not throw an exception" {
            shouldNotThrow<InvalidOperandException> { ArithmeticGroup("+", intGroup1, intGroup2) }
        }
    }
}